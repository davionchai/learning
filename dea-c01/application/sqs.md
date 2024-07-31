# Intro
1. producer send msgs to sqs queue then consumer poll msgs from sqs queue
1. oldest offering (over 10 years old)
1. scales from 1 msg/s to 15,000 msg/s
1. no limit how many msgs can be in queue
1. < 10 ms latency on publish & receive
1. is horizontal scaled for consumers
1. can duplicate msgs (at least once delivery, sometimes)
1. can have out of order msgs (best effort ordering)
1. can be autoscaled with cloudwatch
1. pay per api request or per network usage

# Producing msgs
1. define body
1. add message attributes (metadata - optional)
1. provide delay delivery (optional)
1. after sending sqs msg can get back msg identifier and md5 hash of the body

# Consuming msgs
1. poll sqs for msg (max 10 msg at a time)
1. process msg within visibility timeout
1. delete msg using msg id and receipt handle
1. hence cannot be consumed by multiple consumers

# FIFO queue
1. first in first out, not available in all regions yet
1. name of the queue must end in .fifo
1. lower throughput (max 3,000/s with batching and 300/s without batching)
1. msgs are processed in order by consumer
1. msg are sent exactly once
1. 5 min interval de-dupe using `Duplication ID`

# Extended client
1. to overcome msg size limit 256kb
1. is java library
1. use s3 as the backend
1. workflow
    1. producer send small metadata msg to sqs and large msg to s3 simultaneously
    1. consumer consume the small medatabase msg and pull large msg from s3

# Limits
1. max 120,000 in-flight msg being processed by consumers
1. batch request has a maximum of 10 msgs (max 256kb)
1. content has to be xml, json, unformatted text
1. standard sqs queue type have unlimited transaction/s (TPS)
1. default retention of msg 4 days, min 1 min and max 14 days
1. limitation of 256kb per msg sent

# Security
1. ssl encryption in flight with https
1. supports SSE with kms
    1. can use customer master key (cmk)
    1. only encrypts body and not metadata 
1. controlled by iam policy
    1. can control over ip
    1. control the time the requests come in

# Dead letter queue (dlq)
1. happens when consumer fails to process msg within the visibility timeout
1. msg goes back to the queue
1. configure a threshold on how many times the msg can go back with `MaximumReceives`
1. once `MaximumReceives` threshold exceeded, msg goes into dlq
1. dlq of fifo queue must be fifo, likewise to standard queue
1. dlq has expiry, once expired they are done, recommended to be 14 days
1. has a feature called `Redrive to Source` to help consume dlq msg
    1. can redrive the msg from dlq back into original source queue in batches without custom code

# Fan out with sns
1. push once in sns and receive by all sqs subscriber, use sqs to retry
1. just make sure sqs queue access policy for sns to write
1. cross region is possible with sqs queues
