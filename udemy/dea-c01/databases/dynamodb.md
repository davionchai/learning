# Intro
1. Dynamodb is NoSQL (non-relational databases) and are distributed 
1. HA with replication across multi AZs
1. Scales to massive workloads, distributed database
1. Fast and consistent (low latency)
1. IAM integrated
1. EDA with dynamodb streams
1. Has auto scaling
1. Has standard & IA table class

# Basics
1. Table based
1. Table must be created with primary key
1. Table can have unlimited rows of data (items)
1. Each item has attributes, can be added over time
1. Maximum size of an item is 400 KB
1. Supported Data Types:
    1. Scalar - String, Number, Binary, Boolean, Null
    1. Doc - List, Map
    1. Set - String Set, Number Set, Binary Set

# Primary Keys Options
1. Partition Key (Hash)
1. Partition Key + Sort Key (Hash + Range)
 
# Modes
1. Provisioned Mode (default)
    1. manual config for read/write speed
1. On-demand Mode
    1. automaticaly scale up/down depending on workload
    1. pay what is used, 2.5x more $$$
1. Can switch between modes after 24H

# Throughput
1. Read Capacity Units (RCU)
    1. Strongly consistent read
        1. data is always retrieved after replicated
        1. RCU * 2
        1. 1 rcu = 1 strongly consistent read / second / 4 KB (nearest upper bound, 4.1 KB ~= 8 KB)
    1. Eventually consistent read (default)
        1. data is not replciated immediately
        1. 1 rcu = 2 eventually consistent read / second / 4 KB
    1. example 10 SCR / second / 4 KB = 10 * (4KB/4) = 10 RCU
    1. example 16 ECR / second / 12 KB = 16/2 * (12KB/4) =  24 RCU
    1. example 10 SCR / second / 6 KB = 10 * (8KB/4) = 20 RCU
1. Write Capacity Units (WCU)
    1. 1 wcu = 1 item / second / 1 KB
    1. example 10 items / second / 2 KB = 20 WCU
    1. example 6 items / second / 4.5 KB = 6 * 5 = 30 WCU
        decimal always rounded to upper bound
    1. example 120 items / minute / 2 KB = 120 * (2 / 60) = 4 WCU
1. Burst Capacity can be used temporarily
1. WCU and RCU are spread evenly across partitions

# Partitions
1. Partition key -> Internal Hash -> Partition Assignment
1. Formula # of partitions by capacity = (RCU total / 3000) + (WCU total / 1000)
1. Formula # of partitions by size = total size / 10 GB
1. Formula # of partitions = ceiling(max(# of partitions by capabity, # of partitions by size))

# Throttling
1. exceeding RCU and WCU will get ProvisionedThroughputExceededException
1. For RCU, we can use DynamoDB Accelerator (DAX) $$$

# Writing Data
1. PutItem
1. UpdateItem
1. Conditional Writes (Multiple Items)

# Reading Data
1. GetItem (1 item)
1. Query (SQL like)
    1. max 1 mb
    1. pagination
1. Scan (entire table)
    1. max 1 mb
    1. pagination
    1. RCU heavy (control through LIMIT)
    1. parallel scan with multiple workers

# Deleting Data
1. DeleteItem
1. DeleteTable
    1. faster than DeleteItem if deleting whole table

# Batch Operations
1. BatchWriteItem
    1. up to 25 PutItem and/or DeleteItem in one call
    1. up to 16 mb of data written, up to 400 kb of data per item
    1. can't update items
    1. if failure happened, will receive UnprocessedItems 
1. BatchGetItem
    1. return items from one or more tables
    1. up to 100 items
    1. up to 16 mb of data
    1. if failure happened, will receive UnprocessedItems 

# PartiQL
1. SQL for dynamodb
    1. only select, insert, update, and delete

# Indexing
1. Local Secondary Index (LSI)
    1. alternative (additional) sort key
    1. is scalar - string, number, or binary
    1. up to 5 LSI per table
    1. can be created only during table creation time
    1. use wcu / rcu of main table only
1. Global Secondary Index (GSI)
    1. alternative (additional) primary key
    1. is scaler - string, number, or binary
    1. must provision rcu / wcu for each GSI
    1. can be added/modified after table creation
    1. if GSI's wcu got throttled, main table will be affected