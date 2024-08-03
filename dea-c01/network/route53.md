# Intro
1. fully managed authoritative dns
1. authoritative means domain owner can update the dns record
1. is a domain registrar
1. only aws service that promises 100% availability sla
1. why route 53? 53 is a reference to the traditional dns port

# Records
1. how to route traffic for a domain
1. each record contains:
    1. domain/subdomain name - ie example.com
    1. record type - ie A or AAAA
    1. value - ie 1.2.3.4
    1. routing policy - how route53 responds to queries 
    1. ttl - time to live for the recrd cached at dns resolvers
1. supports the following dns record types:
    1. must know for exam
        1. A - maps hotstname to ipv4
        1. AAAA - maps hostname to ipv6
        1. CNAME - maps a hostname to another hostname
            1. target is a domain name which must have an A or AAAA record
            1. can't be created for the top node of a dns snamespace (zone apex), ie cannot for example.com but can for www.exmaple.com
        1. NS - name servers for the hosted zone
            1. public hosted zone - traffic allowed from internet
            1. private hosted zone - olny accessible within vpc
            1. $0.50 per month per hosted zone
    1. advanced
        1. CAA
        1. DS
        1. MX
        1. NAPTR
        1. PTR
        1. SOA
        1. TXT
        1. SPF
        1. SRV
