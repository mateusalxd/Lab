# Parâmetros:
# 1 - arquivo dockerfile
# 2 - nome do container
# 3 - se informado -e executa o container após criação

DOCKERFILE=$1
CONTAINER_NAME="$2"
AUTOEXEC="$3"
REPOSITORY="mateusalxd/$CONTAINER_NAME"

echo -e "--> Parando containers\n"
CONTAINER_ID=$(docker ps -q -f "name=$CONTAINER_NAME")
[ ! -z $CONTAINER_ID ] && docker stop -t 0 $CONTAINER_ID || echo "Nenhum container em execução"

echo -e "\n--> Removendo containers\n"
CONTAINER_ID=$(docker ps -a -q -f "name=$CONTAINER_NAME")
[ ! -z $CONTAINER_ID ] && docker rm $CONTAINER_ID || echo "Nenhum container para ser removido"

echo -e "\n--> Removendo imagens\n"
IMAGE_ID=$(docker images -q $REPOSITORY)
[ ! -z $IMAGE_ID ] && docker rmi $IMAGE_ID || echo "Nenhuma imagem para ser removida"

echo -e "\n--> Recriando imagens\n"
docker build -f $DOCKERFILE -t $REPOSITORY $(dirname $DOCKERFILE)

[ $? -eq 0 -a $AUTOEXEC == "-e" ] && docker run -d -P --name $CONTAINER_NAME $REPOSITORY