package main

import (
	"errors"
	"fmt"
	"image"
	"image/color"
	"image/jpeg"
	"image/png"
	"math"
	"math/rand"
	"os"
	"path/filepath"
	"strconv"
	"strings"
	"time"
)

func abrirImagem(nome string) (imagem image.Image, extensao string, erro error) {
	arquivo, erro := os.Open(nome)
	if erro != nil {
		return nil, extensao, erro
	}
	defer arquivo.Close()

	extensao = strings.ToLower(filepath.Ext(nome))
	if extensao == "" {
		return nil, extensao, errors.New("Arquivo sem extensão")
	}
	extensao = extensao[1:]

	if extensao == "jpg" || extensao == "jpeg" {
		imagem, erro = jpeg.Decode(arquivo)
	} else if extensao == "png" {
		imagem, erro = png.Decode(arquivo)
	} else {
		return nil, extensao, errors.New("Formato não suportado")
	}

	return imagem, extensao, erro
}

type imagemBase struct {
	altura   int
	largura  int
	extensao string
	pixels   [][]color.Color
}

func (i *imagemBase) NovaImagemBase(imagem image.Image, extensao string) {
	i.altura = imagem.Bounds().Dy()
	i.largura = imagem.Bounds().Dx()
	i.extensao = extensao
	i.pixels = make([][]color.Color, i.largura)

	for x := 0; x < i.largura; x++ {
		i.pixels[x] = make([]color.Color, i.altura)
		for y := 0; y < i.altura; y++ {
			i.pixels[x][y] = imagem.At(x, y)
		}
	}
}

type contadorCor struct {
	r, g, b, a, quantidade uint
}

func (c *contadorCor) Incrementar(r, g, b, a uint) {
	c.r += r
	c.g += g
	c.b += b
	c.a += a
	c.quantidade++
}

func (c *contadorCor) Media() (r, g, b, a uint8) {
	if c.quantidade > 0 {
		r = uint8(math.Round(float64(c.r) / float64(c.quantidade)))
		g = uint8(math.Round(float64(c.g) / float64(c.quantidade)))
		b = uint8(math.Round(float64(c.b) / float64(c.quantidade)))
		a = uint8(math.Round(float64(c.a) / float64(c.quantidade)))
		return r, g, b, a
	}

	return 0, 0, 0, 0
}

func (c *contadorCor) Zerar() {
	c.r = 0
	c.g = 0
	c.b = 0
	c.a = 0
	c.quantidade = 0
}

type cluster struct {
	numero int
	cor    color.Color
}

type kmeans struct {
	imagem        *imagemBase
	clusters      []cluster
	mapaClusters  map[int]int
	classificacao [][]int
}

func (k *kmeans) AdicionarCluster(ct cluster) error {
	_, existe := k.mapaClusters[ct.numero]
	if existe {
		return errors.New("Já existe um cluster com o número " + string(ct.numero))
	} else if ct.numero == 0 {
		return errors.New("O número do cluster não pode ser zero")
	}

	k.clusters = append(k.clusters, ct)
	k.mapaClusters[ct.numero] = len(k.clusters) - 1

	return nil
}

func (k *kmeans) Executar() error {
	if k.imagem.extensao == "png" ||
		k.imagem.extensao == "jpg" ||
		k.imagem.extensao == "jpeg" {
		k.classificar()
	} else {
		return errors.New("Formato não suportado")
	}

	return nil
}

func (k *kmeans) GerarImagemClusterizada(nome string) error {
	pixels := make([][]color.Color, k.imagem.largura)

	for x := 0; x < k.imagem.largura; x++ {
		pixels[x] = make([]color.Color, k.imagem.altura)
		for y := 0; y < k.imagem.altura; y++ {
			indice := k.obterIndiceCluster(k.classificacao[x][y])
			pixels[x][y] = k.clusters[indice].cor
		}
	}

	erro := k.criarImagem(nome, pixels)

	return erro
}

func (k *kmeans) NovaClassificacao(imagem *imagemBase) {
	k.imagem = imagem

	k.clusters = k.clusters[0:0]

	k.classificacao = make([][]int, imagem.largura)
	for x := 0; x < imagem.largura; x++ {
		k.classificacao[x] = make([]int, imagem.altura)
	}

	k.mapaClusters = make(map[int]int)
}

func (k *kmeans) calcularDistancia(cor1, cor2 color.Color) float64 {
	corPadrao1 := k.obterCorPadrao(cor1)
	corPadrao2 := k.obterCorPadrao(cor2)

	parteR := math.Pow(float64(corPadrao1.R)-float64(corPadrao2.R), 2.0)
	parteG := math.Pow(float64(corPadrao1.G)-float64(corPadrao2.G), 2.0)
	parteB := math.Pow(float64(corPadrao1.B)-float64(corPadrao2.B), 2.0)
	parteA := math.Pow(float64(corPadrao1.A)-float64(corPadrao2.A), 2.0)

	total := parteR + parteG + parteB + parteA

	return math.Sqrt(total)
}

func (k *kmeans) classificar() {
	var continuar bool
	var menorDistancia float64
	var distanciaCalculada float64
	var ultimaClassificacao int

	contador := make([]contadorCor, len(k.clusters))

	for {
		continuar = false

		// classifica pixels de acordo com a menor distância
		for x := 0; x < k.imagem.largura; x++ {
			for y := 0; y < k.imagem.altura; y++ {
				menorDistancia = float64(9999999)
				ultimaClassificacao = k.classificacao[x][y]

				for c := 0; c < len(k.clusters); c++ {
					cor1 := k.clusters[c].cor
					cor2 := k.imagem.pixels[x][y]
					distanciaCalculada = k.calcularDistancia(cor1, cor2)

					if distanciaCalculada < menorDistancia {
						k.classificacao[x][y] = k.clusters[c].numero
						menorDistancia = distanciaCalculada
					}
				}

				if ultimaClassificacao != k.classificacao[x][y] {
					continuar = true
				}
			}
		}

		// zera resultados anteriores
		for c := 0; c < len(k.clusters); c++ {
			contador[c].Zerar()
		}

		// soma pixels de cada cluster
		for x := 0; x < k.imagem.largura; x++ {
			for y := 0; y < k.imagem.altura; y++ {
				indice := k.obterIndiceCluster(k.classificacao[x][y])
				cor := k.obterCorPadrao(k.imagem.pixels[x][y])
				contador[indice].Incrementar(uint(cor.R), uint(cor.G), uint(cor.B), uint(cor.A))
			}
		}

		// recalcula centro dos clusters
		for c := 0; c < len(k.clusters); c++ {
			corR, corG, corB, corA := contador[c].Media()
			k.clusters[c].cor = color.NRGBA{R: corR, G: corG, B: corB, A: corA}
		}

		if !continuar {
			break
		}
	}
}

func (k *kmeans) criarImagem(nome string, pixles [][]color.Color) error {
	imgBase := image.NewNRGBA(image.Rectangle{image.Point{0, 0},
		image.Point{k.imagem.largura, k.imagem.altura}})

	for x := 0; x < k.imagem.largura; x++ {
		for y := 0; y < k.imagem.altura; y++ {
			imgBase.Set(x, y, pixles[x][y])
		}
	}

	arquivoImagem, erro := os.Create(nome)
	if erro != nil {
		return erro
	}
	defer arquivoImagem.Close()

	erro = png.Encode(arquivoImagem, imgBase)
	if erro != nil {
		return erro
	}

	return nil
}

func (k *kmeans) obterCorPadrao(cor color.Color) color.NRGBA {
	return color.NRGBAModel.Convert(cor).(color.NRGBA)
}

func (k *kmeans) obterIndiceCluster(numero int) int {
	return k.mapaClusters[numero]
}

func main() {
	tempoInicial := time.Now()

	var nomeArquivo string
	var numeroClusters int
	var mudarSempre bool
	var erro error

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

	arquivoImagem, extensao, erro := abrirImagem(nomeArquivo)
	if erro != nil {
		fmt.Println(erro)
		return
	}

	var imagem imagemBase
	imagem.NovaImagemBase(arquivoImagem, extensao)

	var algortimo kmeans
	algortimo.NovaClassificacao(&imagem)
	if mudarSempre {
		rand.Seed(time.Now().UnixNano())
	}
	for i := 1; i <= numeroClusters; i++ {
		ct := cluster{
			numero: i,
			cor: color.NRGBA{
				R: uint8(rand.Intn(256)),
				G: uint8(rand.Intn(256)),
				B: uint8(rand.Intn(256)),
				A: uint8(rand.Intn(256)),
			},
		}

		algortimo.AdicionarCluster(ct)
		fmt.Printf("Cluster inicial número %v: %v\n", i, ct.cor)
	}

	erro = algortimo.Executar()
	if erro != nil {
		fmt.Println(erro)
	}

	erro = algortimo.GerarImagemClusterizada("imagem_segmentada.png")
	if erro != nil {
		fmt.Println(erro)
	}

	tempoFinal := time.Since(tempoInicial)
	fmt.Printf("Tempo total de execução: %v\n", tempoFinal)
}
