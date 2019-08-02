mvn -f ../unilab/pom.xml clean package -P$1
cp -v -u ../unilab/target/unilab.jar extras/
docker-compose -f docker-compose-$1.yml build --force-rm
docker-compose -f docker-compose-$1.yml up
