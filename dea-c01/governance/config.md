# Intro
1. audit and record compliance of aws resources
1. record config and changes over time
1. common problem
    1. is there unrestricted ssh access to any security groups
    1. is there any s3 having public access
    1. how alb config changed over time
1. can receive alerts (sns) for any changes
1. is a per-region service
1. can be aggregated acorss regions and accounts
1. can store config data into s3 and analyzed by athena

# Rules
1. can use aws managed config rules, theres over 75 to choose
1. can make custom config rules defined in lambda
    1. evaluate if ebs disk is type gp2
    1. evaluate if ec2 is t2.micro
1. rules can be evaluated and triggered
    1. for each config change
    1. and/or at regular time intervals
1. cannot block any activities but only notify
1. pricing (no free tier) 
    1. $0.003 per configuration item recorded per region
    1. $0.001 per config rule evaluation per region

# Remediations
1. automate remediation of non-compliant reosurces using ssm automation documents
1. use aws managed automation documetns or create custom automation documents
    1. can create custom automation documents to trigger lambda function
1. can set remediation retries if resource is still non-compliant after auto remediation

# Notifications
1. use event bridge to trigger notifications when resources are non-compliant
1. can send configuration changes and compliance state notifications to sns
