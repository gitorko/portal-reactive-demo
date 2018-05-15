FROM java:8
WORKDIR /var/app
ADD target/reactiveweb-*.jar /var/app/reactiveweb.jar
ENTRYPOINT [ "java", "-jar", "/var/app/reactiveweb.jar" ]
EXPOSE 8080