# Intro
1. provide governance, compliance, and audit for aws account
1. is enabled by default
1. provide history of events/api calls made within aws account
1. can put logs from cloudtrail into cloudwatch logs or s3
1. a trail can be applied to all regions (default) or a single region
1. if a resource is deleted, investigate cloudtrail first

# Events
1. management events
    1. operations that are performed on resources in aws
    1. examples
        1. configuring security
        1. configuring rules for routing data
        1. setting up logging
    1. trails are configured to log management events by default
    1. can separat read events (dont modify resource) from write (modify resource) events
1. data events
    1. data events are not logged by default due to its high volume
    1. examples
        1. s3 object level activity (can separate read & write events)
        1. lambda function
1. cloudtrail insights events
    1. detect unusual activity
    1. examples
        1. inaccurate resource provisioning
        1. hitting service limits
        1. bursts of aws iam actions
        1. gaps in periodic maintenance activity
    1. analyzes normal management events to create a baseline then continuously analyzes write events to detect unsual patterns
1. event retention are usually stored for 90 days in cloudtrail, in order to go beyond, need to move them to s3 and use athena

# Lake
1. datalake for cloudtrail events
1. integrates collection, storage, preparation, and optimization for analysis & query (events are in orc format)
1. allow cloudtrail data to be queried with SQL
1. activate service with `Create event data store` menu choice in the console
1. data is retained up to 7 years
1. can specify the event types to track
1. be careful on kms events blowing up costs, its recommended to turn it off
1. basic event selectors can be selected in the i
1. granular control is possible with advanced event selectors
1. can create `channels` to integrate with events outside of aws
1. lake dashboards allow easy visualization of events