import RandomChar.randomChar

object Main {
  def main(args: Array[String]): Unit = {
    val seed = System.currentTimeMillis() // Use o tempo atual como semente para garantir aleatoriedade
    val rand = new RandomChar.MyRandom(seed) // Criar uma instância de Random
    val (randomLetter, newRand) = randomChar(rand) // Gerar uma letra aleatória e atualizar o estado do gerador de números aleatórios
    val (randomLetter1, newRand1) = randomChar(newRand) // Gerar uma letra aleatória e atualizar o estado do gerador de números aleatórios
    val (randomLetter2, newRand2) = randomChar(newRand1) // Gerar uma letra aleatória e atualizar o estado do gerador de números aleatórios

    val board = Board(3, 3) // Criar um tabuleiro 3x3
    board.fillOneCell(randomLetter, Coord2D(0, 0)) // Preencher a célula (0, 0) com uma letra aleatória
    board.fillOneCell(randomLetter1, Coord2D(0, 1)) // Preencher a célula (0, 0) com uma letra aleatória
    board.fillOneCell(randomLetter2, Coord2D(0, 2)) // Preencher a célula (0, 0) com uma letra aleatória
    board.display() // Exibir o tabuleiro preenchido
  }
}