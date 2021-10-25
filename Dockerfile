#FROM gradle:7.1.1-jdk16-openj9 AS TEMP_BUILD_IMAGE
#ENV APP_HOME=/usr/app
#WORKDIR $APP_HOME
#COPY build.gradle settings.gradle gradlew gradlew.bat $APP_HOME/
#COPY gradle $APP_HOME/gradle
#COPY . .
#RUN ./gradlew clean build
#
#FROM adoptopenjdk:16-jdk-openj9-focal
#ENV ARTIFACT_NAME=todolist-0.0.1-SNAPSHOT.jar
#ENV APP_HOME=/usr/app
#WORKDIR $APP_HOME
#COPY --from=TEMP_BUILD_IMAGE $APP_HOME/build/libs/todolist-0.0.1-SNAPSHOT.jar .
#EXPOSE 8080
#ENTRYPOINT ["java", "-Xshareclasses:cacheDir=/opt/shareclasses", "-Xquickstart", "-jar", "todolist-0.0.1-SNAPSHOT.jar"]