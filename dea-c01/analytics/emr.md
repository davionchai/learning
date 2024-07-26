# Intro
1. elastic map reduce
1. managed hadoop on ec2
1. has emr notebooks
1. integration with many aws services
    1. ec2
    1. vpc
    1. s3
    1. cloudwatch
    1. iam
    1. cloudtrail
    1. data pipeline

# Cluster
1. has master node to manage the cluster
    1. track and monitor task
    1. its a single ec2
    1. alternatively named as leader node
1. has core node to host hdfs data and run tasks
    1. can be scaled
    1. multi node cluster need at least 1
1. has task node to run tasks but do not host data
    1. is optional
    1. spot instances are recommended

# Cluster types
1. transient cluster
    1. cluster shutdown after all steps done
1. long-running cluster
    1. can spin up task nodes for temp workload
    1. can use reserved instances to save costs
    1. termination protection on by default

# Usage
1. frameworks and apps are configured during cluster launch
1. connect to master to run jobs directly
1. connect to s3 for ordered steps

# Storage
1. use hdfs
    1. 128mb bloc storage
    1. ephemeral, cluster gone then all gone
1. use emrfs (s3 as hdfs)
    1. persistent storage after cluster terminates
    1. alternative for s3
    1. dynamodb is utilized to ensure consistent view when multiple writers are working
1. use local file system
1. use ebs for hdfs
    1. only M4 and C4 typed ec2
    1. not persistent
    1. emr raise failure if ebs got detached

# Scaling
1. automatic scaling
    1. old way of doing
    1. custom scaling rules based on cloudwatch metrics
    1. instance groups only
1. managed scaling
    1. introduced in 2020
    1. support instance groups or fleets
    1. scales spot, on demand, and instances in a savings plan within same cluster
    1. only for spark, hive and yarn workloads
1. scale-up strategy
    1. first add core nodes then task nodes up until max desired 
1. scale-down strategy
    1. first remove task nodes then core nodes
    1. spot nodes always get removed first

# Serverless
1. emr manages underlying capacity
    1. can specify default initial worker sizes
    1. same region only 
1. spark submit via job run requests
1. only cli is supported now
1. still need manually terminate cluster after job done

# Emr on eks
1. spark submit on eks
1. share resources between spark and other kubernetes pod

