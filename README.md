# Papillone - Spring
**효율적인 업무를 위한 일정 관리 시스템**
- Backend URL : http://3.114.241.233/8888

## Build
```sh
$ cd papplan
$ ./mvnw clean pacakge
$ java -jar target/paplan-0.0.5-SNAPSHOT.jar
```

## Proxy Server Build
```sh
$ cd papplan
$ sudo docker compose -f docker-compose.yml up -d --build --force-recreate
```

## CI/CD
### DEV
![image](https://github.com/T2-Papillon/Spring/assets/66417882/71266733-a824-4598-ae23-279c961f6e4b)
-  github action, AWS CodeDeploy 활용
- [URL](http://3.114.241.233/8888)
