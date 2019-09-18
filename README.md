# jboss-https-ssl-configuration

This repository contains example how to configure JBoss and Docker
for application to work using https instead of http. 

## Generate ssl encryption and certificate

Go to ${JBOSS_HOME}/standalone/configuration/ directory and run from terminal:

```bash
keytool -genkeypair -alias mychat -keyalg RSA -keystore mychat-test.keystore -storepass secret
```
This command will generate self-signed certificate, where

-keystore defines name of certificate 

-storepass set password of this certificate 

## Configure https connector in standalone.xml

To setup redirect from http to https you need:
 
1. to set redirect in http connector

2. to set values of -keystore and -storepass and path to certificate key file location  
 
Edit ${JBOSS_HOME}/standalone/configuration/standalone.xml

```xml
<subsystem xmlns="urn:jboss:domain:web:2.2" default-virtual-server="default-host" native="false">
    <connector name="http" protocol="HTTP/1.1" scheme="http" socket-binding="http" redirect-port="8443"/>
    <connector name="https" protocol="HTTP/1.1" scheme="https" socket-binding="https" enable-lookups="false" secure="true">
        <ssl name="mychat-ssl" key-alias="mychat" password="secret" certificate-key-file="/home/jboss/jboss-eap-6.4/standalone/configuration/mychat-test.keystore" protocol="TLSv1"/>
    </connector>
    <virtual-server name="default-host" enable-welcome-root="true">
        <alias name="localhost"/>
        <alias name="example.com"/>
    </virtual-server>
</subsystem>
```
## Configure Docker

To configure Docker you need:

1. Change http to https and localhost port 8080 to 8443, 
add certificate key file to Docker container in Dockerfile

2. Change port 8080 to 8443 in docker-compose.yaml

Edit Dockerfile

```Dockerfile
FROM daggerok/jboss-eap-6.4:6.4.21-alpine
RUN echo "JAVA_OPTS=\"\$JAVA_OPTS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005\"" >> ${JBOSS_HOME}/bin/standalone.conf
EXPOSE 5005
HEALTHCHECK --timeout=1s --retries=66 \
        CMD wget -q --spider https://localhost:8443/my-chat/health \
         || exit 1
ADD --chown=jboss ./jboss-eap-6.4/modules/org/postgresql ${JBOSS_HOME}/modules/org/postgresql
ADD --chown=jboss ./jboss-eap-6.4/standalone/configuration/standalone.xml ${JBOSS_HOME}/standalone/configuration/standalone.xml
ADD --chown=jboss ./jboss-eap-6.4/standalone/configuration/mychat-test.keystore ${JBOSS_HOME}/standalone/configuration/mychat-test.keystore
ADD --chown=jboss ./target/*.war ${JBOSS_HOME}/standalone/deployments/my-chat.war
```
## Run application

Build and run using docker-compose-maven-plugin (see pom.xml) and open it in browser.

```bash
./mvnw ; ./mvnw -Pdocker docker-compose:up

open https://localhost:8443/my-chat
```
As our generated certificate is self-signed you will see warning. 

Simply ignore it, hit Advanced and proceed to localhost.