FROM openjdk:8u272-jdk
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["/sbin/tini", "--", "java","-jar","/app.jar"]