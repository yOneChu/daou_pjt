# daou_pjt
**환경 : intelliJ, PostgreSQL, Springboot, JPA, opencsv **


## 환경설정 ##
1. Docker를 통한 DB 환경구성(PostgreSQL) - (해당 링크는 본인의 블로그 내용임)
- [Docker Postgresql 환경 설정](https://kyhslam.tistory.com/entry/Spring-Boot-17-%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-Postgresql-%EC%A0%91%EC%86%8DDocker-%ED%99%9C%EC%9A%A9?category=892834)
- 명령어
> docker run -p 5432:5432 -e POSTGRES_PASSWORD=pass -e POSTGRES_USER=kyhslam -e POSTGRES_DB=springboot --name postgres_boot -d postgres

2. 소스를 받은 후, org.kyhslam.Application.java 를 Run
