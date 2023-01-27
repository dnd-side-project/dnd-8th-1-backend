FROM openjdk:11

# NOT Plain jar file
ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} ./danverse.jar

ENV TZ=Asia/Seoul

EXPOSE 8080

ENTRYPOINT ["java","-jar","./danverse.jar"]