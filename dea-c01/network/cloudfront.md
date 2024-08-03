# Intro
1. is content delivery network (cdn)
1. improve read performance by caching content at the edge
1. improve user experience
1. 216 point of presence globally (edge location), still adding
1. has ddos protection through shield and waf

# Origins
1. s3 bucket
    1. for distributing files and caching at the edge
    1. enhanced security with cloudfront `origin access control` (oac) which is replacing `origin access identity` (oai)
    1. cloudfront can be used as an ingress to upload files to s3
1. custom origin (http)
    1. alb
    1. ec2
    1. s3 website
    1. any http

# Diff between s3 cross region replication
1. cloudfront
    1. global edge network
    1. files are cached for a ttl configurable
    1. good for static content that must be available globally
1. s3 cross region replication
    1. bucket must be setup for each region
    1. files are updated in near real time
    1. read only
    1. good for dynamic content that requires low latency in specific regions

# ALB as origin
1. allow public ip of edge locations for ec2/alb security groups, both ec2/alb has to be public
1. if alb method, ec2 can be private

# Cache invalidations
1. cloudfront does not auto detect changes from backend, it relies on ttl expiration
1. can force an entire or partial cache refersh by performing ` cloudfront invalidation`
1. can invalidate by all files (`*`) or a special path (`/path/to/invalidate/*`)
