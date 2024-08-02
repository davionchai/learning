# encryption
aws kms encrypt --key-id alias/test --plaintext fileb://kms_secret.txt --output text --query CipherTextBlob --region us-east-1 > kms_secret_encrypted.base64

# base64 decode
cat kms_secret_encrypted.base64 | base64 --decode > kms_secret_encrypted

# base64 decode for windows
certutil -decode .\kms_secret_encrypted.base64 .\kms_secret_encrypted

# decryption
aws kms decrypt --ciphertext-blob fileb://kms_secret_encrypted --output text --query Plaintext > kms_secret_decrypted.base64 --region us-east-1

# base64 decode
cat kms_secret_decrypted.base64 | base64 --decode > kms_secret_decrypted

# base64 decode for windows
certutil -decode .\kms_secret_decrypted.base64 .\kms_secret_decrypted
