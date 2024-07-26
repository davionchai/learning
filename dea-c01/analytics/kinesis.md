# Intro
1. scale by # of shard
1. consume data from producers with record made up of
    1. partition eky
    1. data blob (up to 1mb or)
    1. each shard can handle 1 mb or 1000 msg per sec
1. kinesis data stream produces record made up of
    1. partition key
    1. seq no
    1. data blob
    1. 2 mb / sec (shared) per shard when feeding all consumers
    1. 2 mb / sec (enhanced) per shard per consumer
1. retention
    1. 1 - 365 days
    1. replayable
    1. once insert can be deleted, immutable
    1. data sharing same parition goes to same shard with key-based ordering
1. producers are from
    1. aws sdk
    1. kinesis producer library (kpl)
    1. kinesis agent
1. consumers are to
    1. kinesis client library (kcl)
    1. lambda
    1. data firehose
    1. data analytics
1. capacity modes
    1. provisioned mode
        1. choose number of shards, scale manually or api
        1. each shard 1 mb/s in (1000 records per second) and 2 mb/s out (enhanced fan-out or not)
        1. pay per shard per hour
    1. on demand mode
        1. no need to provision
        1. default capacity provisioned, 4mb/s in or 4000 records/s
        1. scales automatically based on prev 30 days throughput peak
        1. pay per stream per hour & data in/out per GB
1. can be monitored using cloudtrail

# Producers
1. has 
    1. kinesis sdk
    1. kpl
    1. kinesis agent
    1. 3rd party
1. `PutRecord`
    1. api based, `PutRecord` (one) and `PutRecords` (many)
    1. uses batching and increases throughput
    1. if throughput exceeded then get error `ProvisionedThroughputExceeded`
        1. prevent having hot shard (too many workload on single shard)
        1. retries with backoff
        1. increase shards
        1. make sure partition key is good

# KPL
1. c++ or java
1. high performance and long running producers
1. automated retry mechanism
1. has synchronous and asynchronous api
1. can send metrics to cloudwatch for monitoring
1. batching is turned on by default
    1. collect reocrds and write to multiple shards in same `PutRecords API`
    1. aggregate collects more records in one record
    1. use `RecordMaxBufferedTime` (default 100ms) to wait and collect
1. compression to be handled by user
1. KCL records must be decoded with KCL or dedicated helper library
1. not to use `RecordMaxBufferedTime` when process is time critical
1. kpl keeps data when device went offline
1. use `aws sdk` instead for highly time critical 

# Agent
1. monitor log files and send to kinesis data streams
1. java based agent built on top of KPL
1. install in linux based server environments
1. can preprocess data before sending
1. handles file rotation, checkpointing, and retry upon failures
1. emits metrics to cloudwatch for monitoring

# Consumers
