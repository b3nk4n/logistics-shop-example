# Logistics Shop Backend Application

## Getting started

Before the application can be started, a few setup steps need to be taken.

### Database

To set up the database, please run the following commands to start the PostgreSQL container, and run the respective
migrations to create the database and its tables.

```
$ docker run -p 5432:5432 -e POSTGRES_PASSWORD=justAn0th3rS3cr3t -d postgres
$ cd /path/to/repo/backend
$ java -jar target/logistics-shop-1.0.0.jar db migrate config.yml
```

### Starting the LogisticsShopApp application

1. Run `mvn clean install` to build your application
2. Start application with `java -jar target/logistics-shop-1.0.0.jar server config.yml`
3. To check that your application is running enter url `http://localhost:8080`

### Alternatively deploy via Docker Compose

To run application with _Docker Compose_, run the following command in the root directory where the docker-compose.yml file is located:

`$ docker-compose up`

To finally stop and remove the deployed containers, please run following command:

`$ docker-compose down`

## Swagger API

The Swagger API can be found under http://localhost:8080/swagger.

## Health Checks

To see your applications health enter url `http://localhost:8081/healthcheck`
