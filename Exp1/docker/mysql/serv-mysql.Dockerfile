FROM mysql:latest
LABEL Author=mateusalxd
ENV MYSQL_ROOT_PASSWORD=1234
ENV MYSQL_DATABASE=Exp1
ENV MYSQL_PASSWORD=1234
ENV LANG pt_BR.UTF-8
ENV LANGUAGE pt_BR:pt:en
ADD 01_estrutura.sql /docker-entrypoint-initdb.d