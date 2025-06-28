FROM openjdk:8-jdk-alpine

WORKDIR /app

# Install depedencies
RUN apk add bash python3 maven curl gcc musl-dev git
RUN git clone https://github.com/mbechler/marshalsec.git \
    && cd marshalsec \
    && mvn package -DskipTests

# Copy the files
COPY malware ./malware
COPY run.sh .

# Compile files
RUN cd malware \
    &&  javac Exploit.java \
    && gcc -static -o rshell reverse_shell.c \
    && gcc -static -o dirtypipe dirtypipe.c

# Imposta run.sh come comando di default
CMD ["sh", "run.sh"]
