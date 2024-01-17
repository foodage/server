<div align="center" style="margin-top: 1em; margin-bottom: 3em;">
  <h3>Server</h3>
  <br/>

![Java 17](https://img.shields.io/badge/Java_17-007396?style=for-the-badge&logo=OpenJDK&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot__3.1.3-6DB33F?style=for-the-badge&logo=html5&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger_|_Spring_Doc-85EA2D?style=for-the-badge&logo=html5&logoColor=white) <br/>
![Jasypt](https://img.shields.io/badge/Jasypt__3.0.4-00bfb3?style=for-the-badge&logo=html5&logoColor=white)
![AWS S3](https://img.shields.io/badge/AWS_S3-527FFF?style=for-the-badge&logo=amazons3&logoColor=white) <br/>
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=html5&logoColor=white)

</div> <br/>
<b>Foodage 메인 서버 매뉴얼입니다.</b> <br/>
- 메인 스택: Java 17, Spring Boot 3, Spring Security 6, JPA <br/>
- API 문서: Spring Docs <br/>
- 브랜치 전략: Git-flow <br/>
- 패키지 구조: <s>CQRS 기반</s>, 도메인 지향 패키지 구조

<br/><br/>
<div align="center" style="margin-top: 1em; margin-bottom: 3em;">
 ✦ㅤㅤ-ㅤㅤ✦ㅤㅤ-ㅤㅤ✦<br/>
</div><br/>

## 🕳 목차

1. [🪟 Convention (개발 세팅)](#-convention-개발-세팅)
    * [**코딩 컨벤션**ㅤ``#java``](#코딩-컨벤션%E3%85%A4java)
    * [**커밋 컨벤션**ㅤ``#git_hooks``](#커밋-컨벤션%E3%85%A4git_hooks)
2. [📦 Packaging (jar)](#-packaging-jar)
    + [common (shell)](#common-shell)
    + [mac](#mac)
    + [window](#window)
3. [▶️ Run (jar)](#%EF%B8%8F-run-jar)
    + [mac](#mac-1)
4. [🗄 Database](#-database)
5. [🚪 Docker 배포](#-docker-배포)
5. [📝 Etc](#-etc)
    + [❗️ Versioning 규칙](#%EF%B8%8F-versioning-규칙)
6. [Version History](#version-history)

<br/><br/>

## 🪟 Convention (개발 세팅)

### **코딩 컨벤션**ㅤ``#java``

> **Note**  
> IntelliJ IDEA를 기준으로 작성된 설명입니다.

#### 📗 NAVER IntelliJ formatter 사용

1. Preferences > Editor > Code Style > Schema 우측의 ⋮ 클릭 <br/> >
   Import Schema - IntelliJ IDEA code... > ./share/naver-intellij-formatter.xml 추가
2. Preferences - Tools - Actions On Save -> Reformat Code, Optimize imports 체크 후 적용

<br/>

### **커밋 컨벤션**ㅤ``#git_hooks``

#### 📔 Git Hooks 설정

커밋 컨벤션을 통일하기 위한 세팅입니다. <br/>
커밋 시, 컨벤션에 맞춰 자동으로 메시지가 검사·변환된 후에 커밋이 완료됩니다.

- use: Gitmoji
- ⚠️ header에 포함되는 영문자는 반드시 소문자로 작성해야 합니다.
- ⚠️ 단, 클래스 명은 예외적으로 PascalCase 표기를 허용합니다. (ex. refactor: ExampleClass 메소드 수정)

```
$ cp share/commit-msg.sh .git/hooks/commit-msg
$ chmod +x .git/hooks
```

<br/><br/>

## 📦 Packaging (jar)

#### common (shell)

```
프로젝트 루트 경로에서
./build.sh
실행 후 배포 환경에 맞는 jar 파일 생성
```

#### mac

```
./gradlew clean bootjar [-Pprofile={env}]
ex) ./gradlew clean bootjar -Pprofile=local
```

#### window

```
./gradlew.bat clean bootjar [-Pprofile={env}]
ex) ./gradlew.bat clean bootjar -Pprofile=local
```

<br/><br/>

## ▶️ Run (jar)

#### mac

```
java -jar 경로/foodage-1.0.0.jar [--spring.profiles.active={env}]
ex1) java -jar ./build/libs/foodage-1.0.0.jar --spring.profiles.active=dev
ex2) java -jar ./build/libs/foodage-1.0.0.jar # 생략시 local 환경으로 실행
```

<br/><br/>

## 🗄 Database

``application.yml``의 datasource-url 설정 변경

- local: 본인 local에서 실행중인 mariadb port와 연결하여 개발
    ```
    spring:
      datasource:
        driver-class-name: org.mariadb.jdbc.Driver
        url: jdbc:mariadb://localhost:3306/foodage_test
        username: root
        password: ${USER_MARIA_DB_PASSWORD}
    ```

- develop: 원격 환경 개발 or 테스트 필요할 경우 <br/>
  [서버 배포 정보 공유](https://watebin.notion.site/4f782d484eb8493d87fcb9762ac0b4fd?pvs=4) 내용 참조하여 ec2 인스턴스 <-> 로컬 터널링 후, <br/>
  링크에 기재된 정보로 datasource 세팅

<br/><br/>

## 🚪 Docker 배포

- 원격 서버(ec2) 연결 <br/>
  [서버 배포 정보 공유](https://watebin.notion.site/4f782d484eb8493d87fcb9762ac0b4fd?pvs=4) 내용 참조하여 세팅 진행

#### 0) 원격 서버에 파일 업로드

```
[업로드 파일 리스트]
- ./build/lib/foodage-버전.jar
- ./src/main/resources/application-배포환경.yml

(원격서버에 최초 1회 업로드가 필요한 파일, 이후 수정 필요 X)
- ./Dockerfile
- ./share/docker-start.sh
- ./share/docker-stop.sh
```

#### 1) (실행되고 있다면) 현재 실행중인 서버 중지

```
./docker_stop.sh
```

#### 2) Dockerfile에서 버전 확인

```
vi Dockerfile
...
WORKDIR /builder
COPY . .

COPY foodage-1.0.0.jar /app.jar
# ⬆️ 이 부분의 버전 확인후, 현재 실행하려는 jar 파일의 버전으로 수정
# ex. COPY foodage-1.0.8.jar /app.jar

COPY application-local.yml /application.yml
# ⬆️ 이 부분의 버전 확인후, 현재 실행하려는 원격 서버의 환경으로 수정
# ex. COPY application-dev.yml /application.yml
...
```

#### 3) 최신 버전의 서버 jar 파일을 docker로 실행

```
./docker_start.sh
jasypt key는 서버 담당자(@jjh, @keb)에게 요청
```

<br/><br/>

## 📝 Etc

#### ❗️ Versioning 규칙

- Semantic Versioning 사용 (major.minor.patch)
    - 메이저 버전 (Major): 주요 기능이 변경되거나 큰 변경사항이 있는 경우 등, 대규모 업데이트 시에 증가됩니다.
    - 마이너 버전 (Minor): 새로운 기능이 하위 호환을 유지하면서 추가될 때(=> 기존 API와 호환되면서 새로운 기능이 추가됐을 경우에) 증가됩니다.
    - 패치 버전 (Patch): 버그가 수정되거나 기존 기능의 로직이 수정되는 등, 작은 수정사항이 존재할 때 증가됩니다.

<br/><br/>

<div align="center" style="margin-top: 1em; margin-bottom: 3em;">
 ✦ㅤㅤ-ㅤㅤ✦ㅤㅤ-ㅤㅤ✦<br/>
</div><br/>

## Version History

> _Last Updated: 24-01-16_

#### v.1.0.2 24-01-16 📍

- 카카오 로그인 기능 추가
- Spring Security 설정 추가
- JWT 인증 관련 클래스 추가
- 패키지 구조 리팩토링

#### v.1.0.1 23-10-27

- JPA 추가
- 유저 도메인 클래스 및 CR(U)D API 추가
- Exception 처리 관련 클래스 추가
- CQRS 기반 패키지 구조 정의

#### v.1.0.0 23-08-30

- 히스토리 최초 등록
