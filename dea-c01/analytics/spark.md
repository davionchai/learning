# Intro
1. distributed processing framework for big data
1. in memory caching
1. autoamted query optimization
1. supports java, scala, python and r
1. has following features
    1. batch processing
    1. spark sql
    1. real time analytics
    1. machine learning MLLib
    1. graph processing GraphX
    1. spark streaming with kinesis, kafka, emr
1. not mean for OLTP
1. each spark apps are independent processes
1. `SparkContext` coordinates spark apps through a cluster manager
1. `SparkContext` sends app ode and tasks to executors
1. executors run computations and store data
1. spark structured streaming is a constantly growing dataset in layman term

# Spark structured streaming
1. can integrate with kinesis data stream

# Jupyter
1. can use athena to run jupyter notebooks with spark
1. serverless, firecracker as the backend
1. can adjust dpu to bump up coordinatior or executor
