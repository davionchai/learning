# Types
1. Aurora
1. MySQL
1. PostgreSQL
1. MariaDB
1. Oracle
1. SQL Server

# Intro
1. Full ACID compliance
    1. atomicity - if part of transaction fail then entire transaction is discarded
    1. consistency - data written to db always adhere to db constrains and triggers
    1. isolation - each transaction is independent
    1. durability - all changes are permanent once transaction completed

# Aurora
1. mysql and postgresql compatible
1. 5x mysql, 3x postgresql faster
1. 128tb max per database volume
1. up to 15 read replicas
1. continuous backup to s3
1. multi region and multi az
1. automated scaling with aurora serverless

# DB locks
1. rds has lock to prevent multi writing at the same time or prevent reading while writing is happening
1. two locks
    1. shared locks - allow reads but block writes (for share)
    1. exclusive locks - block all reads and writes (for update)

# Operational guidelines
1. use cloudwatch to monitor memory, cpu, storage, and replica lag
1. set automatic backups during low write iops
1. insufficient io will make recovery after failure slow
    1. use db instance with more io
    1. use general purpose or provisioned iops
1. set ttl on dns for db to 30 seconds or less
1. test failover before using (game day)
1. enough ram for entire working set
    1. monitor ReadIOPS, small and stable is good
1. rate limit through api gateway can protect rds
1. use indexes to accelerate select statements (explain can help to surface indexes)
1. avoid full table scans
1. analyze table periodically
1. simplify `where` usage
1. db specific
    1. mysql, mariadb
        1. keep tables well under 16 tb, ideally under 100 gb
        1. enough ram to hold indexes for actively used tables
        1. try to have less than 10,000 tables
        1. use innodb as storage engine 
    1. postgresql
        1. disable db backups and multi az,  synchronous_commit, autovacuum, play with maintenance_work_mem, max_wal_size, checkpoint_timeout during initial massive load
        1. after massive load, enable back whatever is disable, autovacuum is a must
    1. sql server
        1. use rds db events to monitor failovers
        1. do not enable simple recover mode, offline mode, or read only mode because they breaks multi az
        1. dpeloy into all az
    1. oracle
        1. not in the scope because too many, people nowadays use open source
 