mvn -f ../unilab/pom.xml clean package -Pdev
JWTS_CHAVE=G0wERGf5hsEkoL60CDOYlmnKLR7M2XFjV3sWhVacYewfhDfUvdTf12lsb15Ro5FO
export JWTS_CHAVE
JWTS_DURACAO=86400000
export JWTS_DURACAO
java -jar -Dspring.profiles.active=dev ../unilab/target/unilab.jar -Denv_var=$JWTS_CHAVE
