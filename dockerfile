FROM openjdk:8
MAINTAINER syhc006
LABEL app="auth" version="1.0" by="syhc006"
COPY ./target/auth-web-1.0.jar auth-web-1.0.jar
CMD java -jar auth-web-1.0.jar
