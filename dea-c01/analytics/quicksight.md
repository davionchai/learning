# Intro
1. aws cloud analytics service
    1. build visualizations
    1. build paginated reports
    1. perform ad hoc analysis
    1. get alerts on detected anomolies
    1. quickly get business insights from data
1. sources (aws preferred)
    1. redshift
    1. aurora / rds
    1. athena
    1. opensearch
    1. iot analytics
    1. ec2-hosted database
    1. files (s3 or local)
        1. excel
        1. csv, tsv
        1. clf

# SPICE
1. Super-fast Parallel In Nemory Calculation Engine
1. is a columnar storage, in memory , machine code generation service
1. mean to accelerate interactive queries on large datasets
1. each user gets 10 gb of SPICE
1. can scales to hundreds of thousands of users
1. can query directly from athena however will timeout at 30 minutes

# Security
1. support MFA
1. has vpc, eni or direct connect
1. row level security
1. column level security (cls) new in 2021 but enterprise edition only

# Redshift specific
1. by default quicksight can only access data stored in the same region, vpc won't work
1. instead, use a sg with an inbound rule in redshift to authorize access from the ip range of quicksight servers
1. for enterprise only
    1. create a private subnet in vpc
    1. use eni to put quicksight in the subnet (enterprise)
    1. enable cross account access by peering connection between quicksight private subnet and redshift private subnet (or other aws resource)
    1. alternatively, transit gateway, privatelink, vpc sharing are all available

# User management
1. users are defined via iam or email signup
1. active directory keys are managed by aws and is enterprise edition only
1. tweak with iam for custom AD

# Pricing
1. annual subscription
    1. standard - $9 / user / month
    1. enterprise - $18 / user / month
    1. quicksight q - $28 / user / month
1. Extra SPICE capacity (beyond 10 gb)
    1. standard - $0.25 / gb / month
    1. enterprise - $0.38 / gb / month
1. monthly subscription
    1. standard - $12 / user / month
    1. enterprise - $24 / user / month
    1. quicksight q - $34 / user / month
1. enterprise benefits
    1. encryption at rest
    1. microsoft active directory

# Dashboards
1. read only snapshots of analysis
1. share with quicksight access
1. share with embedded dashboards
    1. embed within app
    1. authenticate with AD / Cognito / SSO
    1. quicksight javascript sdk / api
    1. whitelist domains where embedding is allowed

# Machine learning insights
1. ml powered anomaly detection
    1. on top of random cut forest
    1. can help to identify culprits on significant changes in metrics 
1. ml powered forecasting
    1. on top of random cut forest
    1. help to identify seasonality and trends
    1. can exclude outliers and imputes missing value
1. auto narratives
    1. help to produce story for dashboards
1. suggested insights
