# Intro
1. virtual private cloud
1. is a regional resource
1. inside has subnets
1. subnets are defined at az level
1. public subnet is a subnet accessible from the internet
1. private subnet is a subnet not accessible from the internet
1. the access control between internet and subnets are called `route tables`

# Internet gateway & nat gateway
1. internet gateway helps vpc instances connect to internet
1. public subnet has route to the internet gateway which makes it public
1. nat gateway (aws managed) & nat instance (self manged) helps instances in private subnet connect to internet while staying private

# Network acl
1. is a firewall that controls traffic from and to subnet
1. have allow and deny rules
1. are attached at subnet level
1. rules only include ip addresses
1. is stateless, inbound and outbound must be explicit

# Security groups
1. a firewall that controls traffic to and from eni or ec2
1. can only have allow rules
1. rules can include ip or other security groups
1. vpc has default nacl to allow everything
1. is stateful

# Flow logs
1. capture information about ip traffic going into interfaces
    1. vpc flow logs
    1. subnet flow logs
    1. eni flow logs
1. helps to monitor & debug connectivity issues
    1. subnet to internet
    1. subnet to subnet
    1. internet to subnet
1. data can go to s3, cloudwatch logs, and kinesis data firehose

# Peering
1. to connect two vpc privately in aws network only
1. must not have overalapping CIDR
1. is not transitive, ie if A & B peered and A & C peered, doesnt mean B & C are peered

# Endpoints
1. allow user to connect to aws service using private network without public internet
1. enhanced security and lower latency to access aws services
1. only used within vpc

# Site to ste vpn
1. connect an on prem vpn to aws
1. connection is always encrypted automatically
1. goes over the public internet

# Direct connect (dx)
1. establish a physical connection betwen on prem and aws
1. connection is private, secure, and fast
1. goes over a private network
1. takes at least a month to establish

# Private link (vpc endpoint services)
1. most secure & scalable way to expose a service to 1000s of VPCs
1. does not need vpc peering, gateway, nat, route tables, blah blah blah
1. can connect between any vpcs
1. needs a network load balancer (service vpc) and eni (customer vpc)
