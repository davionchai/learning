# Intro
1. serverless application model
1. all config is yaml
1. integrate closely with cloudformation
1. quickly deploy without updating infra with cli command `sam ...`

# Recipe
1. transform header
    1. `Transform: 'AWS::Serverless-2016-10-31'`
1. write code
    1. `AWS::Serverless::Function`
    1. `AWS::Serverless::Api`
    1. `AWS::Serverless::SimpleTable`
1. package & deploy with `sam deploy`

