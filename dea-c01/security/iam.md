# Intro
1. identity and access management
1. is a global service
1. root account is craeted by default and should be kept as secretive as possible
1. users are people within the organization that could be grouped
1. groups only contain users and not other groups
1. one user can have multiple groups

# Policies
1. policies attached to group level will allow all users within the same group inherit same policies
1. policies can be attached to specific user
1. structures
    1. version always 2012-10-17
    1. id is optional
    1. statement
        1. sid optional
        1. effect allow or deny
        1. principal specifies which account/user/role to which this policy applied to 
        1. action contains list of actions
        1. resource contains list of resources affected
        1. condition for when this policy is in effect

# Password policy
1. can enforce storng passwords by forcing
    1. minimum password length
    1. require specific character types
1. can force password expiration
1. can prevent password re-use

# MFA multi factor authentication
1. highly recommended
1. if password is compromised, it is still safe because mfa blocked direct access
1. mfa device options
    1. virtual mfa device
        1. google authenticator
        1. authy
    1. universal 2nd factor (u2f) security key
        1. yubikey by yubico
    1. hardware key fob mfa device
        1. gemalto
    1. hardware key fob mfa device (aws govcloud us)
        1. surepass id

# Roles
1. some aws service might need to perform actions on behalf
1. assign iam roles to these services with appropriate policies
1. common roles
    1. ec2
    1. lambda
    1. cloudformation
