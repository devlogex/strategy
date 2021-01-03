FROM maven AS build
COPY settings.xml /usr/share/maven/ref/settings.xml
COPY common /home/root/strategy/common
COPY runner /home/root/strategy/runner
COPY dbservice /home/root/strategy/dbservice
COPY competitor /home/root/strategy/competitor
COPY goal /home/root/strategy/goal
COPY initiative /home/root/strategy/initiative
COPY layout /home/root/strategy/layout
COPY model /home/root/strategy/model
COPY personas /home/root/strategy/personas
COPY position /home/root/strategy/position
COPY report /home/root/strategy/report
COPY vision /home/root/strategy/vision
COPY pom.xml /home/root/strategy/pom.xml
RUN mvn -B -f /home/root/strategy/pom.xml -s /usr/share/maven/ref/settings.xml clean package

FROM openjdk:8
COPY --from=build /home/root/strategy/runner/target/strategy-runner-jar-with-dependencies.jar /var/lib/strategy-runner-jar-with-dependencies.jar
EXPOSE 8002
CMD ["java","-jar","/var/lib/strategy-runner-jar-with-dependencies.jar"]