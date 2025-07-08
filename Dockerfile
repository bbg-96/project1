# 베이스 이미지 지정
FROM openjdk:21-jdk

# 외부에서 JAR 파일의 위치를 인수로 받을 수 있음 (기본값은 target 폴더의 jar파일)
ARG JAR_FILE=target/*.jar

# 빌드된 JAR 파일을 컨테이너 내부로 복사하여 app.jar로 명명
COPY ${JAR_FILE} app.jar

# 컨테이너 시작 시 실행할 명령어 지정 (Spring Boot 앱 실행)
ENTRYPOINT ["java", "-jar", "/app.jar"]