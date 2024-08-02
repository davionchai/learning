# Intro
1. key management service
1. fully integrated with iam for auth
1. kms key usage is auditable through cloudtrail
1. most aws services are integrated with kms
1. can be used through api calls

# Keys types
1. used to be called kms customer master key (CMK)
1. symmetric (AES 256)
    1. single encryption key for encrypt decrypt
    1. aws services that supports kms use symmetric CMKs
    1. never access to kms key unencrypted, kms api only
1. asymmetric (rsa & ecc key pairs)
    1. public (encrypt) and private (decrypt) pair
    1. use for encrypt/decrypt or sign/verify operations
1. AWS Owned Keys (free)
    1. sse-s3
    1. sse-sqs
    1. sse-ddb
1. AWS Managed Key (free)
    1. aws/service-name
    1. aws/rds
    1. aws/...
1. Customer managed keys in KMS ($1/month)
1. Customer managed keys imported ($1/month)
1. api call to kms ($0.03/10000 calls)
1. aws managed kms key supports automatic key rotation
    1. automatic every 1 year
    1. customer-managed kms key can be automated or on demand
    1. import kms keys only manual rotation using alias

# Cross region
1. for snapshot
1. knms re-encrypt using different region kms key when doing snapshots
