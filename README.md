# D Auth

## Service de autorização de usuários.


## Instalação

```commandline
docker build -f Dockerfile -t celiomatos/d-auth:1.2.0 .
docker run -d --restart always celiomatos/d-auth:1.2.0
```

## Uso
curl
```
curl --location --request POST 'http://127.0.0.1:8089/user/login' --header 'Content-Type: application/x-www-form-urlencoded' --data-urlencode 'username=user' --data-urlencode 'password=1234'
```
response
```json
{
    "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNjI0NTYzODMzLCJleHAiOjE2MjcxNTU4MzN9.PYBa1dbKK-6cwR8H_jnaMf4pFd56QUyTBVUPe3BtjLDjQ3iTS4KDIEqUAGJ1IwsmHknGgbgaQV8b6nbFeYs3hA",
    "name": "user principal",
    "authorities": [
        {
            "id": 1,
            "authority": "USER_CREATE"
        }
    ]
}
```