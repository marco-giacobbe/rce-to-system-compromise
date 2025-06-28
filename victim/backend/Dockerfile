FROM openjdk:8-jdk

WORKDIR /app

# Create backend user
RUN useradd -m -d /home/victim -s /bin/bash victim

# Get Log4J
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*
RUN mkdir -p /app/lib && \
    curl -o /app/lib/log4j-api-2.14.1.jar https://repo1.maven.org/maven2/org/apache/logging/log4j/log4j-api/2.14.1/log4j-api-2.14.1.jar && \
    curl -o /app/lib/log4j-core-2.14.1.jar https://repo1.maven.org/maven2/org/apache/logging/log4j/log4j-core/2.14.1/log4j-core-2.14.1.jar
RUN curl -fLo /app/lib/mysql-connector-java-8.0.11.jar https://repo1.maven.org/maven2/mysql/mysql-connector-java/8.0.11/mysql-connector-java-8.0.11.jar

# Set permission
RUN chmod 644 /app/lib/*.jar && chmod 755 /app/lib

# COPY files
COPY ./src ./src
COPY ./resources ./resources
COPY run.sh .

# Compile backend
RUN mkdir -p bin && javac -d bin -cp "./lib/*" ./src/*.java

# Switch user
USER victim

# Imposta run.sh come comando di default
CMD ["sh", "run.sh"]
