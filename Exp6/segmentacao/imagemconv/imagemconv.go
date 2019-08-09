package imagemconv

import (
	"errors"
	"image"
	"image/color"
	"image/jpeg"
	"image/png"
	"os"
	"path/filepath"
	"segmentacao/imagem"
	"strings"
)

// ArquivoParaImagem converte um arquivo de uma imagem para a estrutura Imagem
func ArquivoParaImagem(nome string) (imagem.Imagem, error) {
	imagemConvertida := imagem.Imagem{}
	arquivo, erro := os.Open(nome)
	if erro != nil {
		return imagemConvertida, erro
	}
	defer arquivo.Close()

	extensao := strings.ToLower(filepath.Ext(nome))
	if extensao == "" {
		return imagemConvertida, errors.New("Arquivo sem extensão")
	}
	extensao = extensao[1:]

	var arquivoImagem image.Image
	if extensao == "jpg" || extensao == "jpeg" {
		arquivoImagem, erro = jpeg.Decode(arquivo)
	} else if extensao == "png" {
		arquivoImagem, erro = png.Decode(arquivo)
	} else {
		return imagemConvertida, errors.New("Formato não suportado")
	}

	imagemConvertida.Dimensao.Altura = arquivoImagem.Bounds().Dy()
	imagemConvertida.Dimensao.Largura = arquivoImagem.Bounds().Dx()
	imagemConvertida.Extensao = extensao
	imagemConvertida.Pixels = make([][]color.Color, imagemConvertida.Dimensao.Largura)

	for x := 0; x < imagemConvertida.Dimensao.Largura; x++ {
		imagemConvertida.Pixels[x] = make([]color.Color, imagemConvertida.Dimensao.Altura)
		for y := 0; y < imagemConvertida.Dimensao.Altura; y++ {
			imagemConvertida.Pixels[x][y] = arquivoImagem.At(x, y)
		}
	}

	return imagemConvertida, erro
}

// ImagemParaPNG salva a estrutura Imagem para um arquivo de imagem no formato PNG
func ImagemParaPNG(imagemOrigem *imagem.Imagem, nome string) error {
	novaImagem := image.NewNRGBA(
		image.Rectangle{
			image.Point{0, 0},
			image.Point{
				imagemOrigem.Dimensao.Largura,
				imagemOrigem.Dimensao.Altura,
			},
		},
	)

	for x := 0; x < imagemOrigem.Dimensao.Largura; x++ {
		for y := 0; y < imagemOrigem.Dimensao.Altura; y++ {
			novaImagem.Set(x, y, imagemOrigem.Pixels[x][y])
		}
	}

	arquivoImagem, erro := os.Create(nome)
	if erro != nil {
		return erro
	}
	defer arquivoImagem.Close()

	erro = png.Encode(arquivoImagem, novaImagem)
	if erro != nil {
		return erro
	}

	return nil
}
