FROM maven:3.5.4-jdk-8-alpine as builder
RUN mkdir logistics-shop
COPY ./ /logistics-shop
WORKDIR logistics-shop
RUN mvn clean install

FROM openjdk:8-jdk-alpine
COPY --from=builder /logistics-shop/target/logistics-shop-1.0.0.jar ./
COPY --from=builder /logistics-shop/config-docker.yml ./

CMD java -jar logistics-shop-1.0.0.jar db migrate config-docker.yml && java -jar logistics-shop-1.0.0.jar server config-docker.yml
EXPOSE 8080
