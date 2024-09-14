FROM openjdk:17
EXPOSE 8001
COPY src/ src/
COPY pom.xml /app

COPY "target/user.jar" app.jar
CMD [ "java", "-jar", "app.jar" ]