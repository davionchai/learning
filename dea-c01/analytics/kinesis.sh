# get cli version
aws --version

# Producer

aws kinesis put-record --stream-name test --partition-key user1 --data "user signup" --cli-binary-format raw-in-base64-out

# Consumer
# check stream
aws kinesis describe-stream --stream-name test

# consume some data
aws kinesis get-shard-iterator --stream-name test --shard-id shareId-000000000 --shard-iterator-type TRIM_HORIZON

aws kinesis get-records --shard-iterator <>
