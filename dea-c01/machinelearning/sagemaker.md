# Intro
1. designed to help building ml models

# Feature store
1. a feature is just a property used to train ml model
1. ml model training needs fast & secure access to feature data for training but its hard to be kept organized and share features between diff models
1. is categorized as feature groups that contains the record identifier, feature name, and event time
1. has online and offline store (s3) where offline store automatically creates a glue data catalog for usage
1. is encrypted at rest and in transit

# Lineage tracking
1. create & store ml workflow, usually known as MLOps
1. keep running history of ml models
1. tracking for auditing and compliance
1. automatically or manually created tracking entities
1. integrates with aws resource access manager for cross account lineage
1. entities types
    1. trial component - computation jobs
    1. trial - trial components
    1. experiment - a group of tirals for a given use case
    1. context - logical grouping of entities
    1. action - workflow step, model deployment
    1. artifact - object or data
    1. association - connecting dots betwen the entities
        1. ContributedTo
        1. AssociatedWith
        1. DerivedFrom
        1. Produced
        1. SameAs
1. Can be queried using LineageQueryApi with the help of Amazon SageMaker SDK for Python

# Data wrangler
1. basically an etl pipeline in sagemaker
1. has gui for users to prepare, import, and visualize data
1. has 300+ built in transformations function to use
1. can use custom xforms with pandas, pyspark, or pyspark ql
1. has `Quick Model` that contain subsets of given dataset to train model with data and measure its results
1. source can be from
    1. s3
    1. athena
    1. redshift
    1. lake formation
    1. sagemaker feature store
    1. any jdbc connectors
1. can write into
    1. sagemaker processing
    1. sagemaker pipelines
    1. sagemaker feature store
