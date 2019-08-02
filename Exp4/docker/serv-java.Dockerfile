FROM openjdk:8-jdk-alpine
LABEL Author=mateusalxd
ADD extras/unilab.jar /tmp
ADD extras/wait-for.sh /tmp
RUN sh -c 'chmod +x /tmp/wait-for.sh'