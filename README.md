# d-auth
autenticador

sudo docker run --name d-postgres -p 5432:5432 -e POSTGRES_PASSWORD=postgres -d postgres:11

sudo docker run -it --rm --link d-postgres:postgres postgres:11 psql -h postgres -U postgres


POST 
http://localhost:8081/oauth/token?username=user&password=1234&grant_type=password

POST /oauth/token HTTP/1.1
Host: localhost:8081
Authorization: Basic YWRtaW46YWRtaW4xMjM0
User-Agent: PostmanRuntime/7.15.2
Accept: */*
Cache-Control: no-cache
Postman-Token: df4b6c53-c330-400e-bb02-f5aeba95f7b5,1e273e74-c953-416d-bf01-bf3c143b1a58
Host: localhost:8081
Content-Type: application/x-www-form-urlencoded
Accept-Encoding: gzip, deflate
Content-Length: 47
Connection: keep-alive
cache-control: no-cache

username=user&password=1234&grant_type=password