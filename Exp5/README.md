# Experiência 5

## Descrição

Conhecendo a linguagem Go. Foi criado um programa que segmenta imagens por cores utilizando o algortmo k-means.

## Itens relacionados

- Go

## Como testar

Execute o comando abaixo com os seguintes parâmetros:

```sh
go run arquivo_fonte imagem numero_clusters utilizar_seed_no_rand
```

Por exemplo:

```sh
go run segmentacao.go teste.png 4 true
```

Será gerada uma imagem com o nome `imagem_segmentada.png` no diretório do `arquivo_fonte`.

## Anotações

- por ser o primeiro contato, pode existir alguns erros na utilização da linguagem.
- lembra as linguagens C e Python.
