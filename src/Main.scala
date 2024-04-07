import RandomChar.{MyRandom, randomChar}
object Main {
  def main(args: Array[String]): Unit = {
    val seed = 0// Use current time as seed for randomness
    val rand = MyRandom(seed) // Create a MyRandom object with the seed

    // Create an empty board
    val board = BoardData.empty(5, 5)

    // Define the positions and word for the word "PROGRAMAR"
    val programarPositions = List((3, 3), (2, 3), (1, 2), (1, 1), (2,1), (3, 0), (3, 1), (4, 2), (4,3))

    // Fill the word "PROGRAMAR" on the board
    val boardWithProgramar = board.setBoardWithWords(List("PROGRAMAR"), List(programarPositions))

    val MLPositions = List((0, 0), (0, 1))
    val boardWithProgramarML = boardWithProgramar.setBoardWithWords(List("ML"), List(MLPositions))


    val randomFilledBoard = boardWithProgramarML.completeBoardRandomly(rand, RandomChar.randomChar)

    // Display the final board
    randomFilledBoard.display()

    val wordval = randomFilledBoard.playTradicional("PROGRAMAR", (3,3), Direction.North)
    println(wordval)

    val wordvalInconvencional = randomFilledBoard.playUntraditional("PROGRAMAR", (3,3), Direction.North)
    println(wordvalInconvencional)

    val wordvalInconvencional2 = randomFilledBoard.playUntraditional("ML", (3,3), Direction.North)
    println(wordvalInconvencional2)

    val wordvalInconvencional3 = randomFilledBoard.playUntraditional("MLGS", (0,0), Direction.East)
    println(wordvalInconvencional3)
  }

}