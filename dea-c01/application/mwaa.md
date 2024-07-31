# Intro
1. managed workflows for apache airflow
1. airflow is a batch-oriented workflow tool
1. uses Directed Acyclic Graph (DAG) in python to write pipelines
1. DAGs are uploaded to s3 and can be zipped with plugins and requirements
1. runs within a vpc in at least 2 AZ
1. has private or public webserver endpoints with iam managed
1. airflow workers autoscale up to the limits defined by users
1. can work together with basically all aws services
1. schedulers and workers are implemented using fargate
