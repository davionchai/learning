# Elastic Load Balancer (ELB)
1. Is a managed load balancer 
1. Has 4 kinds of load balancers offered by AWS
    1. alb - application load balancer
    1. nlb - network load balancer
    1. gwlb - gateway load balancer
    1. clb - classic load balancer (retired in 2023)

# ALB
1. is mainly http/https/grpc protocols (layer 7)
1. has routing features
1. support static dns

# NLB
1. is mainly tcp/udp protocols (layer 4)
1. has static ip through elastic ip

# GWLB
1. is mainly geneve protocol on ip packets (layer 3)
1. route traffic to firewalls that are managed on ec2
1. usually use for intrusion detection
1. help to route traffic to virtual appliances
