# Intro
1. move large amount of data to and from
    1. on prem / other cloud to aws
        1. needs agent
    1. aws to aws, diff storage services are okay
        1. no need agent
1. can sync to 
    1. s3 (any classes)
    1. efs
    1. fsx
1. replication tasks are scheduled
1. one agent task max 10 Gbps, can set limit
1. sync relationship between source and target are reversible
1. if network capacity is a limitation, use `aws snowcone device` instead 

# Key feature
1. file permissions and metadata are preserved
