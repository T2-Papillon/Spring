# Papillone - Spring
**효율적인 업무를 위한 일정 관리 시스템**
- Backend URL : http://3.114.241.233/8888

## Recommended IDE Setup

- [IntelliJ](https://www.jetbrains.com/ko-kr/idea/) 

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

## Applying jekins source code analysis
```bash
$ ./mvnw clean pacakge
$ ./mvnw site

$ cd ..
$ ls -l papplan/target/checkstyle-result.xml
$ ls -l papplan/target/site/spotbugs.xml

$ ./mvnw test -Dspring.profiles.active=test -Dmaven.test.failure.ignore=true
$ ./mvnw package -Dspring.profiles.active=test -Dmaven.test.failure.ignore=true
$ ./mvnw jacoco:report -Dspring.profiles.active=test -Dmaven.test.failure.ignore=true
```

## Ref
- https://www.jacoco.org/jacoco/trunk/doc/maven.html
