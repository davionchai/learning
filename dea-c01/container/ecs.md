# intro
1. elastic container service
1. has 2 types
    1. ec2 launch type
        1. create own ec2 then ecs install agent
        1. need to setup two roles
            1. instance profile role - for infra
            1. task role - for other services
    1. fargate launch type
        1. serverless

# Load balancer
1. 3 types
    1. applicaton load balancer - for most cases
    1. network load balancer - usually for high throughput or to pair with private link
    1. classic load balancer - not applicable for fargate

# Data volumes
1. efs compatible with ecs and fargate
1. fargate + efs = good serverless
1. s3 cannot be mounted as file system
