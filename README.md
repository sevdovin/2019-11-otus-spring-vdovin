# 2019-11-otus-spring-vdovin
Домашнее задание №31. Docker, оркестрация, облака, облачные хостинги. <br /><br />

Сборка: mvn clean install <br />
Запуск:<br />
java -jar -Dfile.encoding=UTF8 -DDB_URL=jdbc:postgresql://localhost:5432/library -DPOSTGRES_USER=postgres -DPOSTGRES_PASSWORD=postgres target/homework31-0.0.1-SNAPSHOT.jar<br />
<br />    
Вход в приложение: http://localhost:8080

<br />    
Сборка Docker-образа: 
docker build --build-arg JAR_FILE=target/*.jar -t svdovin/spring-framework-31:0.0.1 .
<br />
Запуск микросервиса в Docker:
docker run -p 8080:8080 -e DB_URL=jdbc:postgresql://192.168.99.1:5432/library -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres svdovin/spring-framework-31:0.0.1
<br />    
Вход в приложение: http://192.168.99.100:8080
<br />
Запуск postgres и сервиса с помощью docker compose: docker-compose up
<br />    
Вход в приложение: http://192.168.99.100:8080
<br />
