package imagem

import "image/color"

// Imagem representa a estrutura de uma imagem
type Imagem struct {
	Dimensao Dimensao
	Extensao string
	Pixels   [][]color.Color
}
