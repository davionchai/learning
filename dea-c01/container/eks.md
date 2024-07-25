# Intro
1. elastic kubernetes service
1. 2 launch type
    1. ec2
    1. fargate

# Node types
1. managed node groups (ec2)
    1. nodes are managed by eks
    1. asg managed by eks
    1. supports on-demand and spot instances
1. self-managed nodes (ec2)
    1. need to manually create first then register to eks
    1. asg managed by eks
    1. can use prebuilt ami
    1. support on-demand and spot instances
1. fargate
    1. no maintenance and no nodes management

# Data volumes
1. need to create storage class manifest
1. need to install and use container storage interface (CSI) driver
1. 4 types
    1. ebs
    1. efs, works with fargate as well
    1. Fsx for Lustre
    1. Fsx for NetApp ONTAP
