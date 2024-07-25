# Intro
1. to run code snippets
1. is serverless
1. continuous scaling automatically
1. can be triggered by almost all aws services
1. can land operations to almost all aws services
1. default timeout 900 second
1. default payload limit 6 mb

# File system mounting
1. can access efs is all running in a vpc
1. must use efs access point
1. becareful for efs connections limits

# storage
1. 4 types
    1. ephemeral /tmp
        1. max 10240 mb
        1. not persistent
        1. file system storage type
        1. not shared across invocations
    1. lambda layers
        1. 5 layers per function up to 250 mb total
        1. archive storage typed
    1. s3
        1. object storage type
    1. efs
        1. file system storage type
