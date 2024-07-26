# Intro
1. presto as backend
1. serverless
1. use s3 data with sql
1. support many formats
    1. csv, tsv
    1. json
    1. orc
    1. parquet
    1. avro
    1. snappy, zlib, lzo, gzip
1. integration
    1. jupyter, zeppelin, rstudio notebooks
    1. quicksight
    1. odbc & jdbc supports visualization tools

# Using with glue
1. glue crawlers extract data from s3 and populate catalogs with table definitions
1. athena sees catalogs and start allowing users to query

# Workgroups
1. can organize users/teams/apps workloads into workgroups
1. control query access
1. track costs by workgroups
1. integrates with iam, cloudwatch, and sns
1. each workgroup has query history, data limits, iam policies, and encryption settings

# Cost model
1. $5 per tb scanned
1. failed queries are free
1. no charge for ddl (create/alter/drop)
1. columnar formats are cheapest (orc, parquet)
1. glue and s3 are charged separately

# Security
1. access control with iam, acl, and s3 policies, `AmazonAthenaFullAccess` and `AWSQuicksightAthenaAccess`
1. can encrypt at rest like s3
1. cross account s3 access is possible
1. TLS is always on between athena and s3
1. fine-grained access (iam) is broader than data filters but cannot restrict to specific table versions
1. to allow access, lowest security has to grant iam access to `database/default` and `catalog` in each region

# Performance
1. use columnar format
1. small number of large files > large number of small files
1. use paritions, `MSCK REPAIR TABLE`

# ACID transactions
1. powered by iceberg
1. add `'table_type' = 'ICEBERG' during create table command
1. alternatively use governed tables
