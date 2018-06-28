FROM openjdk:alpine as builder
WORKDIR /com/bricks/orders/
COPY . .
RUN ./gradlew bootJar

FROM openjdk:alpine
COPY --from=builder /com/bricks/orders/build/libs/brick-orders-api-1.0.jar .
EXPOSE 8080
CMD ["java", "-jar", "-Dspring.data.mongodb.uri=mongodb://springboot-mongo:27017/brick-orders", "./brick-orders-api-1.0.jar"]