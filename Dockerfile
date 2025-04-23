FROM openjdk:21-jdk-slim

RUN apt update && apt install bash wget -y && apt clean \
&& mkdir -p /opt/app \
&& wget -O /usr/local/bin/wait-for-it.sh https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh && \
chmod +x /usr/local/bin/wait-for-it.sh

ENV APP_HOME=/opt/app

COPY target/courses-platform-api-0.0.1-SNAPSHOT.jar $APP_HOME/app.jar

WORKDIR $APP_HOME

ENTRYPOINT ["wait-for-it.sh", "db:3306", "--", "java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]