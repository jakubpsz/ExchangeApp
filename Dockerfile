# Start with a base image containing Java runtime
FROM openjdk:latest
EXPOSE 8081
WORKDIR /app
COPY target/hw4-1.0-SNAPSHOT.jar target/hw4-1.0-SNAPSHOT.jar
# Run the jar file
COPY src/main/resources/data_files/DAT_MT_EURUSD_M1_202011.csv src/main/resources/data_files/DAT_MT_EURUSD_M1_202011.csv

CMD ["java","-jar","target/hw4-1.0-SNAPSHOT.jar"]