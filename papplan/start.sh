#!/usr/bin/env bash

cd /home/ubuntu/app/papplan

# Maven 프로젝트에서 사용할 겨우
./mvnw -X clean package

# ./gradlew bootJar

sudo docker compose -f docker-compose.yml up -d
