import RandomChar.randomChar

object Main {
  def main(args: Array[String]): Unit = {
    val seed = 1050 // Use o tempo atual como semente para garantir aleatoriedade
    val rand = RandomChar.MyRandom(seed) // Criar uma instância de Random

    val board = Board(10,10) // Criar um tabuleiro vazio

    val words = List("STACK", "OVERFLOW", "IS", "AWESOME")
    val positions = List(
      List(Coord2D(0, 0), Coord2D(1, 0), Coord2D(2, 0), Coord2D(3, 0), Coord2D(4, 0)), // For "STACK"
      List(Coord2D(0, 1), Coord2D(1, 1), Coord2D(2, 1), Coord2D(3, 1), Coord2D(4, 1), Coord2D(5, 1), Coord2D(6, 1), Coord2D(7, 1)), // For "OVERFLOW"
      List(Coord2D(0, 2), Coord2D(1, 2)), // For "IS"
      List(Coord2D(0, 3), Coord2D(1, 3), Coord2D(2, 3), Coord2D(3, 3), Coord2D(4, 3), Coord2D(5, 3), Coord2D(6,3)) // For "AWESOME"
    )

    board.setBoardWithWords(board, words, positions)

    board.display()

    println("")

    board.completeBoardRandomly(board, rand, randomChar) // Preencher o tabuleiro com letras aleatórias
    board.display()


  }
}