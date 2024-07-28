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
1. methods
    1. kinesis sdk
        1. `GetRecords` each shard maximum 2mb total aggregate throughput
        1. `GetRecords` returns up to 10mb (then throttle for 5 sec) or up to 10,000 records
        1. maximum 5 `GetRecords` api calls per shard per second meaning 200ms latency per call
    1. kinesis client library (kcl)
        1. java is first class
        1. read from KPL (de-aggregation)
        1. share multiple shards with multiple consumers in one group, shard discovery
        1. has checkpoint feature to resume progress
        1. leverage dynamodb for coordination and checkpointing (one row/shard)
        1. need to manage dynamodb properly to prevent throttled performance
        1. record processors will process thedata
        1. `ExpiredIteratorException` means to increase WCU in dynamodb
    1. kinesis connector library
        1. older java library, uses kcl on top
        1. write data to
            1. s3
            1. dynamodb
            1. redshift
            1. opensearch
        1. might as well use knesis firehose or lambda
    1. 3rd party libraries
    1. kinesis firehose
    1. aws lambda
        1. can de-aggregate record from kpl
        1. can run lightweight etl almost anywhere
        1. can be used to trigger notifications / send emails
        1. has configurable batch size
    1. kinesis consumer enhanced fan out
        1. intro-ed in aug 2018
        1. works with kcl 2.0 and lambda
        1. each consumer get 2mb/s of provisioned throughput per shard
        1. no more 2mb/s limit from kinesis data streams
        1. pushes data to consumers over http/2
        1. reduced latency to ~70ms
        1. default limit of 20 consumers per data stream

# Scaling
1. add shards = shard splitting
1. reduce shards = merging shards 
1. how to handle out of order records due to resharding
    1. after resharding, process continues reading from child shards
    1. data that is not read from child shards will still be in parent
    1. if process start reading from child before complete reading from parent, process could read data for a partuclar hash key out of order
    1. recommendation, after reshard, read entirely from parent until no new records
    1. kcl has the logic built-in even after resharding operations
1. auto scaling
    1. no native
    1. manually wirte own code with api call `UpdateShardCount`
1. limitations
    1. plan capacity in advance
    1. one resharding operation (one shard event) at a time

# Handling duplicates
1. producers can create duplicates due to retries when network timeouts happen (ack never reaches back to producer)
    1. recommended to embed unique record id to dedupe through consumer side
1. consumers can read records for multiple times when
    1. a worker terminates unexpectedly
    1. a worker instance are added or removed
    1. shards are merged or split
    1. the application is deployed
    1. recommended to make consumer application idempotent or have final workflow destination to handle duplicates

# Security
1. control access by iam
1. encryption in flight using https
1. encryption at rest using kms
1. client side encryption can be implemented manually
1. vpc endpoints are available

# Firehose
1. read from upstream and do simple transformation as batch (near real time), buffer can be disabled though
1. can write into s3, redshift (start from s3), opensearch, 3rd-party partner destinations, custom destinations with proper http endpoint
1. failed data can be archived to a s3 backup bucket (source data as well)
1. main use case is to load data into redshift, s3, opensearch, splunk
1. scaled automatically
1. data transformations are done through lambda
1. supports compression when target is s3 (gzip, zip, and snappy)
1. gzip is the data further loaded into redshift
1. only pay for amount of data going through firehose
1. spark / kcl do not read from firehose!
1. buffer size is flushed based on time and size rules
    1. 32mb or 2 minutes, configurable
    1. can autommatically control buffer size
    1. high throughput cause buffer size to be hit
    1. low throughput cause buffer time to be hit
1. no storage layer

# Kinesis data analytics
1. is acutally managed flink for java to support sql applications
1. can pair with lambda for transformation works
1. recently support python and scala
1. can develop flink application from scratch and load it into managed flink via s3
1. additonally can use table api for sql access
1. commonly used for streaming etl, continuous metric generation, responsive analytics
1. charged by kinesis processing units (kpu) consumed per hour
1. 1 kpu = 1 vcpu + 4 gb
1. has automated schema discovery
1. has `RANDOM_CUT_FOREST` to run anomaly detecton on numeric columns in a stream
