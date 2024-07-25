# Intro
1. fully serverless
1. uses docker images
1. dynamic provision in the back using ec2
1. pay for used ec2 only
1. can schedule batch job using cloudwatch events
1. can orchestrate batch job using step functions

# Diff with Glue
1. glue
    1. run spark code, focus on ETL
    1. don't need to configure resources
1. batch
    1. any computing job regardless ETL or not as long as docker img is executable
    1. resources created in account but managed by batch
    1. for any non ETL related work, batch is recommended
