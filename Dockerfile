FROM maven:3.6.2-jdk-8-slim AS build
COPY . /home/app
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml -P install-drivers initialize
RUN mvn -f /home/app/pom.xml clean test