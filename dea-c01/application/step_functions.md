# Intro
1. use to design workflows
1. easy visualization
1. managed error handling and retry mechanism
1. provides audit of workflow history
1. can "wait" for very long time
1. max execution time of a state machine is 1 year
1. usage example
    1. train ml model
    1. tune ml model
    1. batch job

# State machine
1. basically is a defined workflow in step functions
1. each step in a workflow is a step
1. type of states
    1. task: perform operations like lmabda
    1. choice: conditional rules
    1. wait
    1. parallel
    1. map: run a set of steps for each item in a dataset, like pandas apply lambda fn
    1. pass: usually used for debugging
    1. succeed
    1. fail
