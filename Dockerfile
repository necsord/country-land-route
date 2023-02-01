FROM openjdk:17-slim

WORKDIR /app
COPY . .
COPY $HOME/.m2 /root/.m2
RUN ./mvnw clean install

CMD ./mvnw spring-boot:run