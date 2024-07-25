# Intro
1. is physical device
1. designed to be highly secure and portable to collect and porcess data at the edge
1. device for data migration
    1. snowcone
    1. snowball edge
    1. snow mobile
1. device for edge computing
    1. snowcone
    1. snowball edge
1. use it when it takes more than a week to transfer data into aws

# Snowball edge
1. is a huge box
1. pay per data transfer job
1. probide block storage and s3 compatible object storage
1. 2 types
    1. storage optimized
        1. 80 tb hdd, up to 40 vCPU, 80 GB ram 
        1. 210 tb nvme, up to 104 vCPU, 416 GB ram
    1. compute optimized - 42 tb hdd or 28 tb nvme, 104 vCPU, 416 GB ram, optional gpu

# Snowcone
1. is a small device
1. 2 CPUs, 4 GB ram, wire/wireless, usb c powered or battery
1. 2 types
    1. snowcone - 8 tb hdd
    1. snowcone ssd - 14 tb ssd
1. use snowcone is snowball too big to handle
1. no battery / cables provided
1. 2 options to return
    1. use physical post to send back
    1. use datasync

# Snow mobile
1. is a real bigass truck
1. transfer exabytes of data (1 EB = 1000 PB = 1,000,000 TB)
1. 1 snow mobile has 100 PB capacity
1. has gps and 24/7 surveilence

# Edge location
1. place without internet

# OpsHub
1. replace old way of using cli only
1. install in laptop/pc locally and start usin with ui
1. unlocking and configuring single/clustered devices
1. transfer files
1. launch instances
1. monitor device metrics
1. launch aws services (ie ec2, datasync, nfs)
