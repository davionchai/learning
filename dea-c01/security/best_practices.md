# Principle of least privilege
1. grant only permissions of what is required
1. its okay to have broad for dev env sometimes and then boil down as requirements grow clearer
1. can use iam access analyzer to generate policies based on recent usage

# Data masking and anonymization
1. masking obfuscates data
1. supported in glue databrew and redshift (masking policy)
1. anonymization by replacing with random values or shuffling

# Key salting
1. appending or prepending random value is known as salt
1. blocks precomputed common hash matches `rainbow table` attacks
1. good to rotate salts periodically
1. always use strong and random values for salting
1. each user should have a unique salt
1. salt and hash passwords before storing

# Keeping data where it belongs
1. aws organizations' service control policies is a good solution to prohibit data travelling between regions
1. always restrict iam and s3 policies to allowed regions only
1. be mindful when backing up or replicating for rds, aurora, redshift, any other db, and datastore
1. monitor and alarm with cloudtrail and cloudwatch
