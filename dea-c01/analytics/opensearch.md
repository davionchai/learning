# Intro
1. previously ElasticSearch
1. is for petabyte scale analysis and reporting
1. is a fork of elasticsearch and kibana, in sep 2021, elastic changed license to block aws offering elasticsearch as a service 
1. is a search engine
1. built on top of `Lucene`, basically scalable version of it
1. has visualization tool (dashboards, kibana alternative)
1. can be part of data pipeline with kinesis 
1. good usage for
    1. full text search
    1. log analytics
    1. app monitoring
    1. security analytics
    1. clickstream analytics
1. has fully managed and serverless version

# Concepts
1. documents, can be more than text, any structured json data works
1. types, defines the schema and mapping shared by documents that represent the same sort of thing, however is gonig to be removed soon
1. indices, support search into all documents within a collection of types

# Shards
1. documents are hashed to a particular shard
1. each shard may be on different node in a cluster
1. every shard is a self-contained lucene index of its own

# Fully Managed
1. is not serverless
1. scale up/down without downtime
1. pay for what is used (instance hours, storage, data transfer)
1. network isolation
1. has aws integration
    1. s3 buckets (via lambda to kinesis)
    1. kinesis data streams
    1. dynamodb stream
    1. cloudwatch/trail
    1. zone awareness
1. can specific cohice of count and instance types of master node(s)
1. snapshots to s3

# Security
1. resource based plicies
1. identity based policies
1. ip based policies
1. request signing
1. hide behind vpc (has to configure when deploying)
1. cognito (auth framework to dashboards)

# Note
1. oltp, no transactions
1. ad hoc data query, use athena instead
1. for search and analytics only

# Storage
1. Standard data nodes use hot storage, just like how ec2 works (most expensive)
1. UltraWarm (warm) storage uses s3 + caching, good for indices with few writes (lower cost) but must have a dedicated master node
1. cold storage uses s3 (cheapest), good for periodic research on old data, must have dedicated master and have UltraWarm enabled prior
    1. not compatible with t2 or t3 instance types on data nodes
    1. if using fine-grained access control, must map users to `cold_manager` role in OpenSearch Dashboards
1. data can move between diffrent storage types

# Index state management (ism)
1. automated index management policies
1. ism policies run every 30-48 minutes to ensure random jitter that doesnt cause multiple policies running at once
1. can perform index rollups
    1. periodically roll up old data into summarized indices
    1. save storage costs
    1. new index may have fewer fields and coarser time buckets
1. can perform index transforms
    1. like rollups, but to create a different view to analyze same data differently
    1. mostly use for groupings and aggregations

# Cross cluster replication
1. replicate indices / mappings / metadata across domains
1. designed for HA
1. follower index pulls data from leader index
1. need fine-grained access control and node-to-node encryption to securely replicate
1. `remote reindex` allows copying indices from one cluster to another on demand

# Stability
1. best practice is to have 3 dedicated master nodes to avoid split brain mechanism
1. always monitor disk space (minimum storage requirements is roughly `source data * [1 + Number of replicas] * 1.45`)
1. choosing number of shards
    1. `(source data = room to grow) * (1 + indexing overhead)/desired shard size`
    1. may need to limit number of shards per node but usually disk space run out first
1. instance types usually to be more storage orientated

# Performance
1. memory pressure in jvm can happen if
    1. unbalanced shard allocations across nodes
    1. too many shards in a cluster
1. delete old or unused indices to release `JVMMemoryPressure` if needed

# Serverless
1. introduced in jan 2023
1. on demand autoscaling
1. work against `collections` instead of `domains`
1. always encrypted with kms key
1. capacity is measured in Opensearch Compute Units (ocu)
    1. can set an upper limit, but lower limit is always 2 for indexing and 2 for search  
