package main

import (
	"fmt"
	"image/color"
	"math/rand"
	"os"
	"segmentacao/algoritmo/kmeans"
	"segmentacao/imagemconv"
	"strconv"
	"time"
)

func main() {
	tempoInicial := time.Now()

	var (
		nomeArquivo    string
		numeroClusters int
		mudarSempre    bool
		erro           error
	)

	switch len(os.Args) {
	case 4:
		nomeArquivo = os.Args[1]
		if _, erro = os.Stat(nomeArquivo); erro != nil {
			fmt.Printf("Verifique se o arquivo %v existe\n", nomeArquivo)
			return
		}

		numeroClusters, _ = strconv.Atoi(os.Args[2])
		if numeroClusters <= 0 {
			fmt.Println("Número de clusters inválido, serão considerados 3 clusters")
			numeroClusters = 3
		}

		mudarSempre, erro = strconv.ParseBool(os.Args[3])
		if erro != nil {
			mudarSempre = false
			fmt.Println("A geração dos clusters sempre iniciará com os mesmos valores")
		}
	default:
		fmt.Println("Parâmetros inválidos")
		return
	}

	imagemOriginal, erro := imagemconv.ArquivoParaImagem(nomeArquivo)
	if erro != nil {
		fmt.Println(erro)
		return
	}

	if mudarSempre {
		rand.Seed(time.Now().UnixNano())
	}
	clusters := make([]kmeans.Cluster, numeroClusters)
	for i := 0; i < numeroClusters; i++ {
		cluster := kmeans.Cluster{
			Numero: i + 1,
			Cor: color.NRGBA{
				R: uint8(rand.Intn(256)),
				G: uint8(rand.Intn(256)),
				B: uint8(rand.Intn(256)),
				A: uint8(rand.Intn(256)),
			},
		}

		clusters[i] = cluster
		fmt.Printf("Cluster inicial número %v: %v\n", i, cluster.Cor)
	}

	imagemFinal, erro := kmeans.Classificar(&imagemOriginal, &clusters)
	if erro != nil {
		fmt.Println(erro)
		return
	}
	imagemconv.ImagemParaPNG(&imagemFinal, "imagem_segmentada.png")

	tempoFinal := time.Since(tempoInicial)
	fmt.Printf("Tempo total de execução: %v\n", tempoFinal)
}
