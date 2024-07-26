# Intro
1. serverless discovery and definition of table definitions and schema
1. can be used serve s3 data lakes, rds, redshift, and many other sql databases 
1. run custom etl jobs
    1. trigger-driven like airflow
    1. fully managed
    1. spark as underlying backend
1. costs
    1. crawler and etl are charged by seconds
    1. first million objects stored and accesses are free for data catalog
    1. development endpoints for developing etl code are charged by minute
 1. glue etl can now support serverless streaming etl by using spark structured streaming

# Glue crawler (data catalog)
1. scans data in s3 and creates schema
1. can run periodically
1. stores only table definition
1. data still stays in s3
1. once catalogued, unstructured data is like structured
1. s3 partition based on how s3 is organized, so please design properly depending on usage

# Hive
1. run sql-like queries from emr
1. data catalog can be used as hive metastore
1. hive metastore can be stored into glue as well

# ETL
1. automatic code generation
    1. can be modified after generated
    1. can use own spark scripts
1. scala or python
1. encryption
    1. service side (at rest)
    1. ssl (in transit)
1. can be event driven
1. can provision extra dpu to increase performance, pay more $$
1. errors are reported to cloudwatch
1. can talk to s3, jdbc, or glue data catalog
1. dynamic frame
    1. like spark dataframe objects
    1. have a schema and self-describing
    1. support scala and python api
    1. transformation
        1. bundled transformations
            1. DropFields, DropNullFields - remove (null) fields
            1. Filter - specify a function to filter records
            1. Join - to enrich data
            1. Map - add fields, delete fields, perform external lookups
        1. machine learning transformations
            1. FindMatches ML - identify duplicate or matching records
        1. Format conversions - csv, json, avro, parquet, orc, xml
        1. Spark transformation like K-means
    1. ResolveChoice
        1. deal with ambiguities in a DynamicFrame and returns a new one
        1. types
            1. make_cols - create new col for each type 
            1. make_struct - create structure that contains each data type
            1. cast - cast all values to specfic type
            1. project - project every type to a given type
1. can update partition using `enableUpdateCatalog` and `partitionKeys` options
1. can update schema using `enableUpdateCatalog` and `updateBehavior` from script
1. can create new table using `enableUpdateCatalog` and `updateBehavior` with `setCatalogInfo`
1. restriction applies 
    1. s3 as data store only
    1. json, csv, avro, parquet
    1. parquet need additional codes
    1. nested schemas are not supported

# Running glue jobs
1. cron style
1. job bookmarks
    1. persists state from job run
    1. prevents reprocessing of old data
    1. processes new data only when re-running on a schedule
    1. works with s3 sources
    1. work with rdb with jdbc only when primary keys are in seq order, new rows only and not updated rows
1. cloudwatch events
    1. can fire off a lambda or sns when etl finish regardless success or fail

# Glue studio
1. visual interface for etl workflows
1. visual job editor
    1. create dags with ui
    1. out of the box connectors to drag and drop
    1. transform / sample / join data
    1. support partitioning
    1. can evaluate data quality
1. visual job dashboard to monitor job statuses

# Databrew
1. visual data prep tool like excel
1. source from s3, warehouse, database
1. output to s3
1. has over 250 prebuilt transformations
1. can use json recipes
1. can create datasets with custom sql from redshift or snowflake
1. ways to handle pii
    1. substitution `REPLACE_WITH_RANDOM`
    1. shuffling `SHUFFLE_ROWS`
    1. deterministic encryption `DETERMINISTIC_ENCRYPT`
    1. probabilistic encryption `ENCRYPT`
    1. decryption `DECRYPT`
    1. delete `DELETE`
    1. masking `MASK_CUSTOM, _DATE, _DELIMITER, _RANGE`
    1. hashing `CRYPTOGRAPHIC_HASH`

# Workflows
1. design multi-job, like combining multiple crawler and etl to run together
1. create from glue blueprint, can be through console or api
1. need to use workflow triggers to trigger either by cron, manual, or linked to eventbridge (eventbridge need to optimize by considering batch size and batch window)
