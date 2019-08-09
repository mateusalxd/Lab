package kmeans

import (
	"math"
	"sync/atomic"
)

// ContadorCor representa a estrutura de um contador de cores
type ContadorCor struct {
	r, g, b, a, quantidade uint64
}

// Incrementar incrementa o contador de acordo com os parâmetros
func (c *ContadorCor) Incrementar(r, g, b, a uint64) {
	atomic.AddUint64(&c.r, r)
	atomic.AddUint64(&c.g, g)
	atomic.AddUint64(&c.b, b)
	atomic.AddUint64(&c.a, a)
	atomic.AddUint64(&c.quantidade, 1)
}

// Media retorna a média das cores contadas
func (c *ContadorCor) Media() (r, g, b, a uint8) {
	if c.quantidade > 0 {
		r = uint8(math.Round(float64(atomic.LoadUint64(&c.r)) / float64(atomic.LoadUint64(&c.quantidade))))
		g = uint8(math.Round(float64(atomic.LoadUint64(&c.g)) / float64(atomic.LoadUint64(&c.quantidade))))
		b = uint8(math.Round(float64(atomic.LoadUint64(&c.b)) / float64(atomic.LoadUint64(&c.quantidade))))
		a = uint8(math.Round(float64(atomic.LoadUint64(&c.a)) / float64(atomic.LoadUint64(&c.quantidade))))
		return r, g, b, a
	}

	return 0, 0, 0, 0
}

// Zerar zera o contador de cores
func (c *ContadorCor) Zerar() {
	c.r = 0
	c.g = 0
	c.b = 0
	c.a = 0
	c.quantidade = 0
}

// Contador representa a estrutura de um contador
type Contador struct {
	contador uint64
}

// Incrementar incrementa em 1 o contador
func (c *Contador) Incrementar() {
	atomic.AddUint64(&c.contador, 1)
}

// Valor retorna o valor do contador
func (c *Contador) Valor() uint64 {
	return atomic.LoadUint64(&c.contador)
}

// Zerar zera o contador
func (c *Contador) Zerar() {
	c.contador = 0
}
