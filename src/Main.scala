import RandomChar.MyRandom
object Main {
  def main(args: Array[String]): Unit = {
    val seed = System.currentTimeMillis() // Use current time as seed for randomness
    val rand = RandomChar.MyRandom(seed) // Create a MyRandom object with the seed

    val board = BoardData.empty(10, 10)

    val words = List("STACK", "OVERFLOW", "IS", "AWESOME")
    val positions = List(
      List((0, 0), (1, 0), (2, 0), (3, 0), (4, 0)), // For "STACK"
      List((0, 1), (1, 1), (2, 1), (3, 1), (4, 1), (5, 1), (6, 1), (7, 1)), // For "OVERFLOW"
      List((0, 2), (1, 2)), // For "IS"
      List((0, 3), (1, 3), (2, 3), (3, 3), (4, 3), (5, 3), (6, 3)) // For "AWESOME"
    )

    val filledBoard = board.setBoardWithWords(words, positions)

    filledBoard.display()

    println()

    val randomFilledBoard = filledBoard.completeBoardRandomly(rand, RandomChar.randomChar)
    randomFilledBoard.display()
  }
}
