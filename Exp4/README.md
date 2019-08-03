# Experiência 4

## Descrição

Aplicação dos conhecimentos adquiridos em aproximadamente duas semanas.

## Itens relacionados

- API Rest
- Java
- Maven
- Spring Boot
- Spring Security
- Spring Actuator
- MySQL / H2
- Hibernate
- Springfox (Swagger)
- Java JWT

## Como testar

A partir do diretório `Lab/Exp4/docker`, utilize os comandos:
```
./iniciar.sh dev
```
Após iniciar a aplicação, acesse `http://localhost:8080/swagger-ui.html` para explorar a documentação e realizar testes. Todas as requisições necessitam de credenciais do tipo *Bearer*. A autenticação pode ser realizada através de recurso `POST /autenticacao` com o corpo:
```
{
  "nome": "admin",
  "senha": "123456"
}
```
O usuário `admin` tem acesso a todos os recursos da aplicação, para criar novos usuários, utilize um dos recursos `POST /alunos` ou `POST /professores`, em seguida crie o usuário através do recurso `POST /usuarios`. Detalhes das informações de cada recurso estão na documentação.

Existe a possibilidade de simular um ambiente de produção utilizando um banco `MySQL`, utilize os comandos:
```
./iniciar.sh prod
```
Após iniciar a aplicação, para realizar a carga inicial dos dados no banco, execute:
```
docker exec -it serv-mysql sh
mysql -u root -p$MYSQL_ROOT_PASSWORD unilab < /tmp/data.sql
exit
```

Ao finalizar a aplicação, utilize o comando abaixo para limpar os *containers* e *images*:
```
./limpar.sh dev
```
ou
```
./limpar.sh prod
```

Caso não tenhe o `Docker` instalado, você pode executar o `.jar` direto com o comando (é necessário pelo menos o `Maven`):
```
./iniciar-sem-docker.sh
```

## Anotações

- [referência](https://stackabuse.com/spring-boot-profiles-for-dev-and-prod-environments/) de uso de *profiles* no `Maven`.
- [referência](https://stackoverflow.com/questions/166895) para utilizar dependências por *profile* no `Maven`.
- [referência](https://www.quora.com/How-can-I-run-SQL-file-in-Ubuntu) para importar dados de um arquivo `.sql` para o `MySQL`.
- [referência](https://docs.docker.com/compose/startup-order/) para aguardar o serviço de banco iniciar antes de iniciar o serviço da aplicação, utilizando `docker-compose`.
- cursos: [Java e JPA: Persista seus objetos com a JPA2 e Hibernate](https://cursos.alura.com.br/course/persistencia-de-objetos-com-jpa-hibernate), [Maven: Build do zero a web](https://cursos.alura.com.br/course/maven-build-do-zero-a-web), [Spring Boot Parte 1: Construa uma API Rest](https://cursos.alura.com.br/course/spring-boot-api-rest), [Spring Boot Parte 2: Segurança da API, Cache e Monitoramento](https://cursos.alura.com.br/course/spring-boot-seguranca-cache-monitoramento)
- ao utilizar o `Spring Security`, deixar a regras mas específicas primeiro e as mais genéricas depois.