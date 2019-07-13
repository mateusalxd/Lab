FROM tomcat:latest
LABEL Author=mateusalxd
ENV LANG pt_BR.UTF-8
ENV LANGUAGE pt_BR:pt:en
ADD exp1.war webapps