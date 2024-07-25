# Intro
1. database migration service
1. source db has to remain available during migration
1. homogenious migrations
    1. ie oracle to oracle, postgres to postgres
1.  heterogenous migrations
    1. ie oracle to postgres
1. supports continuous data replication using CDC 
1. need an EC2 to perform replication task
1. for multi az, replicated db will be the leader and used to ensure other standby replica are synced

# AWS Schema Conversion Tool (SCT)
1. needed for heterogenous migrations (diff db engine)
1. all udf, ext func, and similar custom stuff are converted and replicated  

# Continuous replication
1. batch first then cdc
