# Encryption in flight
1. data is encrypted before sending and decrypted after receiving
1. tls certs (https) is used
1. lower chances of MITM significantly

# Service side encryption at rest
1. data is encrypted after arriving into the server
1. data is decrypted before being sent
1. stored in encrypted form using a specific key
1. the encryption and decryption keys must be managed somewhere and server must be able to access 

# Client side encryption
1. data is encrypted by the client and never decrypted by the server
1. data is to be decrypted by a receiving client
1. envelope encryption is one of the many method
