FROM openjdk:17
EXPOSE 8002
COPY src/ src/
COPY pom.xml /app

COPY "target/order.jar" app.jar
CMD [ "java", "-jar", "app.jar" ]