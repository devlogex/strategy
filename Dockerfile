FROM pw_env
WORKDIR /data/strategyservice
COPY runner/target/strategy-runner-jar-with-dependencies.jar .
# RUN mvn -B -f /data/strategyservice/pom.xml -s /usr/share/maven/ref/settings.xml clean package
CMD ["java", "-jar", "strategy-runner-jar-with-dependencies.jar"]