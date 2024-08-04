# Intro
1. secure storage for config & secrets
1. optional seamless encryption using kms
1. serverless, scalable, durable, easy sdk
1. version tracking of config / secret
1. security through iam
1. notifications with event bridge
1. integration with cloudformation
1. can have hierarchy, for example
    1. `/my-path/`
        1. `my-app/`
            1. `dev/`
                1. `username`
                1. `password`
            1. `prod/`
                1. `username`
                1. `password`
        1. `another-app`
    1. `/other-path`
1. can be used to access secrets manager
    1. `/aws/reference/secretsmanager/secret_ID_in_Secrets_Manager`
1. can be used to access public aws issued resources
    1. `/aws/service/ami-amazon-linux-latest/amzn2-ami-hvm-x86_64-gp2` (pubic)

# Tiers
1. standard 
    1. 10,000 parameters per region
    1. 4kb max size per paramater value
    1. no parameter policies
    1. no additional charge
    1. free storage pricing
1. advanced
    1. 100,000 parameter per region
    1. 8kb max size per paramater value
    1. has parameter policies
        1. can assign ttl to force updating/deleting sensitive data
        1. can assign multiple policies at a time
    1. charges apply
    1. $0.05 per advanced parameter per month
