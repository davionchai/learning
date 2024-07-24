# Intro
1. designed for OLAP
1. has sql, odbc, jdbc interfaces
1. scale up/down on demand
1. built in replication & backups

# Spectrum
1. query data from s3 without loading
1. limitless concurrency
1. separate storage & compute resources
1. support gzip and snappy
1. 1mb block size

# Durability
1. replication within cluster
1. backup to s3 and replicated to another region
1. automated snapshots
1. automated replacement of failed nodes
1. limited to single az, multi az only allowed for ra3 clusters

# Resizing/Scaling
1. elastic resize
    1. new cluster is craeted while old cluster is kept for read only
    1. cname is flipped to new cluster, so expect some downtime during the switch
    1. data moved in parallel to new compute nodes
1. classic resize
    1. new cluster is read only for hours to days

# Table workload distribution styles
1. auto - based on size of data
1. even - rows distributed across slics in round-robin
1. key - rows distributed based on one column
1. all - entire table is copied to every node
1. use pg class info or sv table info to check

# Import & Export Data
1. COPY command
    1. from s3, emr, dynamodb, remote hosts
    1. s3 requires a manifest file and IAM role
    1. use single copy transaction for narrow tables (many rows and few columns)
1. UNLOAD
    1. unload table into files in s3
1. use enhanced vpc routing properly to avoid exposing data to public internet 
1. auto copy from s3
1. aurora zero etl, auto replication from aurora to redshift
1. redshift streaming ingestion, from kinensis data streams or msk

# Dblink
1. connect redshift to postgresql

# Workload management (WLM)
1. prioritize short, fast queries vs long, slow queries
1. query queues
1. interface through console, cli, api
1. concurrency scaling cluster
    1. automatically scaled up cluster
    1. support unlimited concurrent users & queries as long as $$ enough
    1. wlm queues to manage which queries are sent to concurrency scaling cluster
1. automated wlm
    1. up to 8 queues
    1. default 5 queues with even memory allocation
    1. large queries has concurrency lowered
    1. small queries has concurrency raised
    1. configurable via
        1. priority
        1. concurrency scaling mode
        1. user groups
        1. query groups
        1. query monitoring rules
1. manual wlm
    1. one default queue with concurrency level of 5 (5 queries at once)
    1. superuser queue with concurrency level 1
    1. up to 8 queues, up to concurrency level 50
        1. each queues can be customized
    1. query queue hopping, query can hop to another queue 
1. short query acceleration (SQA)
    1. prioritize short running queries
    1. ran in separate space, won't wait in queue behind long queries
    1. can use to replace wlm queues for short queries
    1. works with ctas and read only queries
    1. use ML to predict query execution time
    1. can configure how many seconds is considered short
1. vacuum types
    1. vacuum full - default, resort all rows and reclaim space from deleted rows 
    1. vacuum delete only - reclaim space from delete rows only
    1. vacuum sort only - resort table only
    1. vacuum reindex - re-initializing interleaved indexes (cleanup sort key columms)

# New catchy stuff
1. ra3 nodes with managed storage
    1. compute and storage are decoupled
    1. ssd based
1. data lake export
    1. unload to s3 in parquet
    1. automatically partitioned
1. spatial data types
    1. geometry, geography
1. cross region data sharing
    1. share live data across redshift cluster without copy
    1. ra3 node type only

# Security
1. hardware security module (hsm)
    1. need to create new encrypted cluster
    1. must use a client server cert pair
1. use grant or revoke to manage access privileges for user or group

# Serverless
1. automated scaling
1. pay only when in use
1. serverless endpoint with jdbc & odbc connection
1. security maintain through IAM
1. required params to setup
    1. database name
    1. admin user credentials
    1. vpc
    1. encryption settings (default aws kms)
    1. audit logging
1. can manage snapshots and recovery points after creation
1. capacity measured in redshift processing units (rpu)
1. pay for rpu / second + storage
1. base rpu
    1. default to auto
    1. can adjust from 32 to 512 rpu
1. max rpu
    1. can set usage limit
    1. can increase to improve performance
1. does not support
    1. parameter groups
    1. workload management
    1. aws partner integration
    1. maintenance windows / version tracks
    1. no public endpoints, vpc only
1. monitoring views are available to help troubleshooting
1. cloudwatch logs are available under /aws/redshift/serverless/
1. cloudwatch metrics are available to monitor query performance and warehouse dimensional stats

# Materialized views
1. precomputed results from executed queries
1. queries are much faster since it uses precomputed results
1. useful for predictable and repeating queries
1. can built on top of another materialized views

# Data sharing
1. share live data across redshift cluster
1. sell data through aws data exchange
1. access can be granular
1. producer and consumer must both be encrypted and must use ra3 nodes
1. cross region involves transfer chargers
1. types of data shares
    1. standard
    1. aws data exchange
    1. aws lake formation - managed

# Lambda UDF
1. custom functions in lambda to be used in redshift sql
1. must `grant usage on language exfunc` for permissions
1. use AWSLambdaRole IAM policy, basically need `lambda:InvokeFunction` for redshift cluster IAM
1. redshift uses json to talk with lambda

# Federated queries
1. query and analyze across databases, warehouses, and lakes
1. ties redshift with aurora postgresql or mysql
1. incorporate live data in RDS into redshift queries
1. skipping the needs for ETL pipelines
1. must be in same vpc subnet or vpc peering
1. credentials must be in secret managers
1. secrets access policy must be in iam role
1. connect using `create external schema` (similarly to s3 and spectrum)
1. can use `svv_external_schemas` to check
1. read only access to external data sources and its only one-way from redshift

# Out of the box tables & views
1. SYS view - monitor query & workload usage
1. STV tables - transient data containing snapshots of current system data
1. SVV views - metadata about db objects that reference STV tables
1. STL views - generated from logs persisted to disk
1. SVCS views - details about queries on main & concurrency scaling clusters
1. SVL views - details about queries on main clusters


