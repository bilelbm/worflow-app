# Getting Started

### Setup Application
The application needs docker and docker-compose to run the test database

[Official Docker website](https://www.docker.com/get-started)

After installing docker and docker compose cd into the project directory then use the following command:
```
docker-compose -f docker/postgres.yml up -d
```

Now your postgres db should be up and running

You can now start the application (port: 8080):
```
mvn clean install
```

to manage the database you can download the postgres webclient *PgAdmin*:
[PgAdmin website](https://www.pgadmin.org/download/)

The application uses swagger 2 for Api documentation found in the following url:
```
http://localhost:8080/swagger-ui.html
```



