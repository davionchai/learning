# Intro
1. managed streaming for kafka
1. alternative to kinesis
1. cluster can be up to 3 az for HA (1 ZK per az)
1. data is stored on ebs volumes
1. automated recovery from common kafka failures
1. can build own producers and consumers of data
1. can customise cluster configurations
    1. changing default 1 mb message size to 10 mb for example

# Kinesis vs MSK
1. kinesis has 1 mb hard limit message size while msk has no limit
1. kinesis uses data shards while msk use topics with partitions
1. shard scaling mechanism can be splitting or merging while msk can only add partitions to topic and not removing
1. kinesis has tls in-flight only while msk can have additional `PLAINTEXT` option
1. both kms at rest encryption
1. kinesis has only IAM policies for AuthN/AuthZ

# Security
1. tls in flight is disable by default between brokers, same between clients and brokers
1. ebs volumes are encrypted with kms
1. has authentication and authorization to
    1. define who can read & write to which topics
    1. mutual tls (AuthN) + kafka acls (AuthZ)
    1. SASL/SCRAM (AuthN) + kafka acls (AuthZ)
    1. iam access control (AuthN + AuthZ)

# Monitoring
1. cloudwatch metrics
    1. basic (cluster + broker metrics)
    1. enhanced (++ enhanced broker metrics)
    1. topic-level (++ enhanced topic-level metrics)
1. prometheus (opensource)
    1. opens a port on the broker to export cluster, broker, and topic-level metrics
    1. setup the jmx exporter (metrics) or node exporter (cpu and sik metrics)
1. broker log delivery
    1. to cloudwatch logs
    1. to s3
    1. to kinesis data streams

# MSK connect
1. managed kafka connect workers on aws
1. auto scaling capabilities for workers
1. any kafka connect connectors to msk connect as plugin

# MSK serverless
1. automatically provision resources and scales compute & storage 
1. just config topics and partitions
1. pricing model
    1. per cluster per hour
    1. per partition per hour
    1. per gb storage per month
    1. per GB in
    1. per GB out

