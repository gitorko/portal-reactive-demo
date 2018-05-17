```
To Run the reactiveweb-service
$mvn clean install
$spring-boot:run
Rest API
http://localhost:8080/api/customers

To Run the reactiveweb-ui
$npm start

Clarity UI
https://localhost:4200/

To build clarity web
ng build --prod

To Run Mongo on Docker:
docker run -p 27017:27017 -d mongo

Ensure this property is set in application.properties
spring.data.mongodb.host=localhost

Docker File Sharing if volumes are to be persisted:
Set-NetConnectionProfile -interfacealias "vEthernet (DockerNAT)" -NetworkCategory Private

Proxy for angular
ng serve --proxy-config proxy.config.json

https://github.com/spring-projects/spring-boot/issues/9785

If you want to pass properties during command line
spring-boot:run '-Dspring-boot.run.arguments=--spring.data.mongodb.host=172.29.160.33'

Demo:
1. Using spring init 
2. Webflux
3. JPA
4. Clarity Seed project
5. CORS (Cross-Origin Resource Sharing)
6. vscode themes & plugins

Future topics:
1. back pressure
2. hot cold stream.
3. server sent event.

```
