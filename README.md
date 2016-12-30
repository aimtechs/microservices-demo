# microservices-demo

## git download

```bash
git clone https://github.com/aimtechs/microservices-demo.git
```

## eureka-service

```bash
cd eureka-service 
mvn -DskipTests clean package
java -Dspring.profiles.active=peer1 -jar target/eureka-service-0.0.1-SNAPSHOT.jar
java -Dspring.profiles.active=peer2 -jar target/eureka-service-0.0.1-SNAPSHOT.jar
```

<http://localhost:8761>

## config-service

```bash
cd confing-service
mvn -DskipTests clean package
java -jar target/config-service-0.0.1-SNAPSHOT.jar
```

## lot-service

```bash
cd lot-service
mvn -DskipTests clean package
java -jar target/lot-service-0.0.1-SNAPSHOT.jar
java -jar target/lot-service-0.0.1-SNAPSHOT.jar
```

## lot-client

```bash
cd lot-client
mvn -DskipTests clean package
java -jar target/lot-client-0.0.1-SNAPSHOT.jar 
```

<http://localhost:9999/names>

<http://localhost:9999/message>


## config refresh test

lot-service.yml  파일내 message 내용 수정 후

```bash
curl -X POST http://{IP}:{PORT}/refresh
```
