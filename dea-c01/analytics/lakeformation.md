# Intro
1. built on top of glue, basically managed spark
1. loading & monitor data
1. can define transformation jobs & monitor after
1. strong access control with audit
1. source from s3, rdbms, nosql
1. is crawlers, etl, data catalog, security, ACL, cleaning, transformation
1. land into athena, redshift, emr
1. free, but used resources are charged
1. process
    1. create iam user
    1. create aws glue connection to source
    1. create s3 bucket for lake usage
    1. register s3 to lake, with permission
    1. create database in lake formation for cataloging and permissioning
    1. create blueprint for workflow
    1. run workflow
    1. grant select permissions to whichever service needed

# Governed tables
1. acid compliant
1. new type of s3 table
1. cannot change once created
1. works with Kinesis (stream) and athena
1. automated compaction
1. row and cell level security, governed or not

# Permissions (data filter)
1. can tie to iam, saml, or external aws acc
1. can use policy tags on databases, tables, and cols
1. can have read write permission on tables and cols
1. can have column, row, and cell level security
1. filter can be created through `CreateDataCellsFilter` api or console

# Key points
1. cross account lake formation permission
    1. consumer must be a data lake administrator
    1. can resource access manager to manage external users
    1. can use iam permissions
1. doesn't support manaifests in athena or redshift queries
1. iam permissions on kms encryption key are needed for encrypted data catalogs
1. iam permissions needed tocreate blueprints & workflows
