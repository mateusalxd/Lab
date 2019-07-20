# Experiência 2

Itens relacionados
- API Rest
- Java
- Jersey
- Maven
- Docker

## Como testar

### Execute os comandos a partir do diretório base do repositório:

- se tiver o maven instalado (pode ser executado o próximo passo caso não queira gerar o .war novamente)
```
cd Exp2/produto
mvn clean package
cp -u target/produto.war ../docker/extras/exp2.war
cd ../docker
docker-compose build
docker-compose up
```
- se não tiver o maven instalado
```
cd Exp2/docker
docker-compose build
docker-compose up
```

### Para inserir um produto utilize:

```
curl -v -X POST -H "Content-type: application/xml; charset=utf-8" -d "<produto><id>1</id><descricao>Abacaxi</descricao><valor>3.5</valor></produto>" http://localhost/exp2/rest/produtos
```

### Para consultar um produto utilize:

```
curl -v http://localhost/exp2/rest/produtos/1
```
Observação: informar o id do produto no lugar do número 1.