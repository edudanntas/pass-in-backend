FROM azul/zulu-openjdk:17
WORKDIR /app
COPY target/pass-in-1.0.0.jar pass-in-1.0.0.jar
EXPOSE 8080
CMD ["java", "-jar", "pass-in-1.0.0.jar"]
