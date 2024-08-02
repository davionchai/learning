# Intro
1. web application firewall
1. protects web application from common web exploits (layer 7)
1. http is layer 7
1. tcp/udp is layer 4
1. can deploy on
    1. application load balancer (alb)
    1. api gateway
    1. cloudfront
    1. appsync graphsql api
    1. cognito user pool
1. define web acl rules
    1. ip set up to 10,000 ip address, can use multiple rules to bypass max ip count
    1. filter based http headers, body, or url strings to stay protected from common attack such as sql injection and cross site scripting (xss)
    1. can have size constraints
    1. can have geo match (block countries)
    1. can set rate based rules to count occurances of events to block ddos attack
1. web acl are regional except for cloudfront
1. a rule group is a reusable set of rules that can be added to a web acl
1. does not support network load balancer which is layer 4
    1. to overcome this, use global accelerator to create a fixed ip
    1. set up alb with waf webacl attached (note that webacl must same region with alb)
