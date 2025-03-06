FROM eclipse-temurin:17-jre-alpine

# Add Zscaler intermediate root certificate
COPY zscaler.crt /usr/local/share/ca-certificates/zscaler.crt
RUN update-ca-certificates

# Install curl
RUN apk update && apk --no-cache add curl

VOLUME /tmp
EXPOSE 8080

ARG JAR_FILE=target/web-portal-business-service-1.0.jar
COPY ${JAR_FILE} /app.jar

CMD ["java", "-XX:MaxRAMPercentage=75.0", "-XX:+UseContainerSupport", "-jar", "/app.jar"]
