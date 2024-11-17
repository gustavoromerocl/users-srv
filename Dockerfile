FROM openjdk:17-ea-24-oracle

WORKDIR /app
COPY target/userssrv-0.0.1-SNAPSHOT.jar app.jar
COPY Wallet_DB_TEST /app/oracle_wallet
EXPOSE 8080

CMD [ "java", "-jar", "app.jar" ]