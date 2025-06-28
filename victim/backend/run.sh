#!/bin/sh

echo "[*] Running the server as $(whoami)..."
java -Dcom.sun.jndi.ldap.object.trustURLCodebase=true \
     -cp "./bin:./lib/*:./resources" \
     -Dlog4j.configurationFile=./resources/log4j.xml \
     Backend &

wait
