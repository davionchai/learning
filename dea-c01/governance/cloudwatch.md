# Metrics
1. is a variable to monitor
1. belong to namespaces (one namespace/service)
1. dimensions is an attribute of a metric (instnce)
1. up to 30 dimensions per metric
1. metrics have timestamps
1. can use for dashboard
1. can create custom metrics
1. can stream out from aws using kinesis data firehose
1. can filter and send specific metrics only

# Logs
1. has log groups to represent applcations
1. has log stream to represent specific log instances within application
1. configurable log expiration policies
1. can be exported to
    1. s3
    1. kinesis data stream
    1. kinesis data firehose
    1. lambda
    1. opensearch
1. are encrypted by default
1. sources from
    1. sdk
    1. logs agent
    1. unified agent
    1. elastic beanstalk - collection of logs from application
    1. ecs - collection from containers
    1. lambda - collection from function logs
    1. vpc flow logs - vpc specific logs
    1. api gateway
    1. cloudtrail based on filter
    1. route53 - log dns queries
1. can use `log insights` to check & analyze logs
1. log insights can query multiple log groups in different aws accounts
1. log insights is a query engine and not a real-time engine
1. can export to s3 with `CreateExportTask` api but can take up to 12 hours to become available for export 
1. canu use `log subscriptions` to have real-time log events for processing and analysis
1. has a subscription filter to determine which logs for events to be delivered to
1. different log subscriptions can send to same specfic kinesis dataw stream and then aggregated through kinesis data firehose

# Unified agents
1. by default no logs in ec2 will go to cloudwatch, hence need to run cloduwatch agent on ec2 to push the log files
1. iam permissions needed
1. cloudwatch log agent can be setup onprem too
1. has two types
    1. logs agent - older and can only send to cloudwatch logs
    1. unified agent - newer and can collect additional sys level metrics such as ram, processes, etc to cloudwatch logs 
        1. cpu
        1. disk
        1. ram
        1. netstat
        1. processes
        1. swap space
1. ec2 metrics default only has disk, cpi, network at high level

# Alarms
1. used to trigger notifications for metric
1. various options such as sampling, %, max, min, etc
1. states
    1. ok
    1. insufficient_data
    1. alarm
1. period
    1. length of time in seconds to evaluate metric
    1. high resolution custom metrics, 10sec, 30sec, or multiples of 60sec
1. main targets
    1. ec2
    1. asg
    1. sns
1. composite alarms
    1. combination of multiple alarms with AND & OR conditions
1. can test alarm by using cli commands
    1. `aws cloudwatch set-alarm-state --alarm-name "myalarm" --state-value ALARM --state-reason "testing purposes"`
