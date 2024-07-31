# Intro
1. formerly cloudwatch events
1. can be cron based, eda, and on demand
1. almost everything can send to event bridge
1. events can be filtered
1. can trigger myriads of aws services
1. each event flow are called event bus 
1. event bus type
    1. default event bus - aws services
    1. partner event bus - external saas partners such as datadog and zendesk
    1. custom event bus - anything else
1. event buses can be crossed account using resource based policy
1. can archive events and sent to an event bus for unlimited time to allow replaying events
1. has a schema registry for event bridge to analyze events in the bus so that it could infer schema
1. schema versioning is possible

# Resource based policy
1. manage permission for a specific event bus
