FROM pw_env
WORKDIR /data/strategyservice
COPY . .
RUN mvn -B -f /data/strategyservice/pom.xml -s /usr/share/maven/ref/settings.xml clean package
CMD ["java", "-jar", "runner/target/strategy-runner-jar-with-dependencies.jar"]