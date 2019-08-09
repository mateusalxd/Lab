package kmeans

import (
	"errors"
	"image/color"
	"math"
	"runtime"
	"segmentacao/imagem"
	"sync"
)

// Classificar classifica uma Imagem por cores
func Classificar(imagemOriginal *imagem.Imagem, clusters *[]Cluster) (imagem.Imagem, error) {
	mapaClusters := make(map[int]int)
	erro := inicializarMapaClusters(&mapaClusters, clusters)
	if erro != nil {
		return imagem.Imagem{}, erro
	}

	var classificacoes [][]int
	inicializarClassificacoes(
		&classificacoes,
		imagemOriginal.Dimensao.Altura,
		imagemOriginal.Dimensao.Largura,
	)

	numeroClusters := len((*clusters))
	contadorContinuidade := Contador{}
	contadorCor := make([]ContadorCor, numeroClusters)
	grupo := sync.WaitGroup{}
	intervalos2D := gerarIntervalos2D(
		imagemOriginal.Dimensao.Altura,
		imagemOriginal.Dimensao.Largura,
	)

	for {
		// zera resultados anteriores
		contadorContinuidade.Zerar()
		for c := 0; c < numeroClusters; c++ {
			contadorCor[c].Zerar()
		}

		// classifica pixels de acordo com a menor distância
		for _, intervalo2D := range intervalos2D {
			grupo.Add(1)
			go classificarIntervalo(
				intervalo2D.X1,
				intervalo2D.X2,
				intervalo2D.Y1,
				intervalo2D.Y2,
				imagemOriginal,
				clusters,
				&mapaClusters,
				&classificacoes,
				&grupo,
				&contadorContinuidade,
				&contadorCor,
			)
		}
		grupo.Wait()

		// recalcula centro dos clusters
		for c := 0; c < numeroClusters; c++ {
			corR, corG, corB, corA := contadorCor[c].Media()
			(*clusters)[c].Cor = color.NRGBA{R: corR, G: corG, B: corB, A: corA}
		}

		if contadorContinuidade.Valor() == 0 {
			break
		}
	}

	return gerarImagemClassificada(
		&classificacoes,
		clusters,
		&mapaClusters,
		imagemOriginal.Dimensao.Altura,
		imagemOriginal.Dimensao.Largura,
	), nil
}

func calcularDistancia(cor1, cor2 color.Color) float64 {
	corPadrao1 := obterCorPadrao(cor1)
	corPadrao2 := obterCorPadrao(cor2)

	parteR := math.Pow(float64(corPadrao1.R)-float64(corPadrao2.R), 2.0)
	parteG := math.Pow(float64(corPadrao1.G)-float64(corPadrao2.G), 2.0)
	parteB := math.Pow(float64(corPadrao1.B)-float64(corPadrao2.B), 2.0)
	parteA := math.Pow(float64(corPadrao1.A)-float64(corPadrao2.A), 2.0)

	total := parteR + parteG + parteB + parteA

	return math.Sqrt(total)
}

func classificarIntervalo(
	xInicial, xFinal, yInicial, yFinal int,
	imagemOriginal *imagem.Imagem,
	clusters *[]Cluster,
	mapaClusters *map[int]int,
	classificacoes *[][]int,
	grupo *sync.WaitGroup,
	contadorContinuidade *Contador,
	contadorCor *[]ContadorCor) {
	defer grupo.Done()

	var (
		menorDistancia      float64
		distanciaCalculada  float64
		ultimaClassificacao int
		indice              int
		cor                 color.NRGBA
	)

	for x := xInicial; x < xFinal; x++ {
		for y := yInicial; y < yFinal; y++ {
			menorDistancia = float64(9999999)
			ultimaClassificacao = (*classificacoes)[x][y]

			for c := 0; c < len((*clusters)); c++ {
				cor1 := (*clusters)[c].Cor
				cor2 := imagemOriginal.Pixels[x][y]
				distanciaCalculada = calcularDistancia(cor1, cor2)

				if distanciaCalculada < menorDistancia {
					(*classificacoes)[x][y] = (*clusters)[c].Numero
					menorDistancia = distanciaCalculada
				}
			}

			// verifica se a última classificação é diferente da atual
			if ultimaClassificacao != (*classificacoes)[x][y] {
				contadorContinuidade.Incrementar()
			}

			// soma pixels de cada cluster
			indice = (*mapaClusters)[(*classificacoes)[x][y]]
			cor = obterCorPadrao(imagemOriginal.Pixels[x][y])
			(*contadorCor)[indice].Incrementar(
				uint64(cor.R),
				uint64(cor.G),
				uint64(cor.B),
				uint64(cor.A),
			)
		}
	}
}

func gerarImagemClassificada(classificacoes *[][]int, clusters *[]Cluster, mapaClusters *map[int]int, altura, largura int) imagem.Imagem {
	novaImagem := imagem.Imagem{}
	novaImagem.Dimensao.Altura = altura
	novaImagem.Dimensao.Largura = largura
	novaImagem.Pixels = make([][]color.Color, largura)

	for x := 0; x < largura; x++ {
		novaImagem.Pixels[x] = make([]color.Color, altura)
		for y := 0; y < altura; y++ {
			indice := (*mapaClusters)[(*classificacoes)[x][y]]
			novaImagem.Pixels[x][y] = (*clusters)[indice].Cor
		}
	}

	return novaImagem
}

func gerarIntervalos2D(altura, largura int) []Intervalo2D {
	numeroNucleos := runtime.NumCPU()
	if numeroNucleos > largura {
		numeroNucleos = 1
	}

	intervalos := make([]Intervalo2D, numeroNucleos)
	larguraBase := (largura / numeroNucleos)

	for n := 0; n < numeroNucleos; n++ {
		intervalo := Intervalo2D{}
		intervalo.X1 = n * larguraBase
		intervalo.Y1 = 0
		intervalo.Y2 = altura
		if n != numeroNucleos-1 {
			intervalo.X2 = intervalo.X1 + larguraBase
		} else {
			intervalo.X2 = largura
		}
		intervalos[n] = intervalo
	}

	return intervalos
}

func inicializarClassificacoes(classificacoes *[][]int, altura, largura int) {
	(*classificacoes) = make([][]int, largura)
	for x := 0; x < largura; x++ {
		(*classificacoes)[x] = make([]int, altura)
	}
}

func inicializarMapaClusters(mapaClusters *map[int]int, clusters *[]Cluster) error {
	for i, cluster := range *clusters {
		_, ok := (*mapaClusters)[cluster.Numero]
		if ok {
			return errors.New("Já existe um cluster com o número " + string(cluster.Numero))
		} else if cluster.Numero == 0 {
			return errors.New("O número do cluster não pode ser zero")
		}

		(*mapaClusters)[cluster.Numero] = i
	}

	if len((*clusters)) == 0 {
		return errors.New("Deve existir pelo menos um cluster")
	}

	return nil
}

func obterCorPadrao(cor color.Color) color.NRGBA {
	return color.NRGBAModel.Convert(cor).(color.NRGBA)
}
