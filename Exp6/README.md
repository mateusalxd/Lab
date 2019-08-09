# Experiência 6

## Descrição

Conhecendo a linguagem Go. Foi realizada uma restruturação da [Exp5](../Exp5), dividindo em diretórios e foi utilizado `Goroutines` no processo de classificação, que era o mais demorado.

## Itens relacionados

- Go
- Goroutines

## Como testar

Copiar o diretório `segmentacao` para a pasta `src` referente ao seu `GOPATH`, em seguida, execute o comando abaixo a partir do diretório `segmentacao` com os seguintes parâmetros:

```sh
go run arquivo_fonte imagem numero_clusters utilizar_seed_no_rand
```

Por exemplo:

```sh
go run segmentacao.go teste.png 4 true
```

Será gerada uma imagem com o nome `imagem_segmentada.png` no diretório do `arquivo_fonte`.

## **IMPORTANTE**

Quanto maior o número de clusters, maior será o tempo gasto.
