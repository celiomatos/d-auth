# d-auth
autenticador

sudo docker run --name d-postgres -p 5432:5432 -e POSTGRES_PASSWORD=postgres -d postgres:11

sudo docker run -it --rm --link d-postgres:postgres postgres:11 psql -h postgres -U postgres