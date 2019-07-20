FROM jetty:9.4.18-alpine
ENV LANG pt_BR.UTF-8
ENV LANGUAGE pt_BR:pt:en
ADD extras/exp2.war webapps