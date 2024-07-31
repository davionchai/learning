# Intro
1. send one msg to many receivers
1. publish/subscribe model
1. each subscriber to the topic will get all msg (unless filtered)
1. up to 12,500,000 subscriptions per topic
1. 100,000 topics limit per account, can request to incraese
1. publishables
    1. emails
    1. sms & mobile notification
    1. http(s) endpoints
    1. sqs
    1. lambda
    1. kinesis data firehose
1. aws services that utilizes sns
    1. cloudwatch alarms
    1. auto scaling group notifications
    1. cloudformation state changes
    1. budgets
    1. s3 events
    1. dms new replica
    1. rds events
    1. lambda
    1. dynamodb
    1. many more

# Usage
1. topic publish with sdk
    1. craete a topic
    1. create a subscription
    1. publish
1. direct publish with mobile apps sdk
    1. create a platform app
    1. create a platform endpoint
    1. publish to platform endpoint

# Security
1. in flight using https
1. at rest using kms
1. client side for custom encryption
1. regulated by iam policies for access

# FIFO
1. ordering by msg group id and all msg in same group are ordered 
1. dedupe using a duplication id or content based dedupe
1. can have sqs standard and sqs fifo as subscribers
1. limited throughput 

# Message filtering
1. json policy to filter msg sent to sns topic's subscription
1. a subscriber without filter policy will receive every msg
