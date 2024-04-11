import RandomChar.MyRandom

object Main {
  def main(args: Array[String]): Unit = {
    jogoPalavrasCruzadas()
  }

  private def jogoPalavrasCruzadas(): Unit = {
    println(s"Welcome to Crossword Puzzle Game!\n")

    var randomFilledBoard: Option[BoardData] = None

    Iterator.continually(promptOption())
      .takeWhile(_ != "3")
      .foreach {
        case "1" =>
          randomFilledBoard = Some(createNewTestBoard())
          println(s"New board created:")
          randomFilledBoard.foreach(_.display())
        case "2" =>
          randomFilledBoard match {
            case Some(board) =>
              play(board)
            case None => println(s"Please start a new board first.")
          }
        case "4" =>
          changeTextColor()
        case _ =>
          println(s"Invalid option. Please select a valid option.")
      }
  }

  private def promptOption(): String = {
    println(s"Options:")
    println(s"1.Create new board")
    println(s"2.Play")
    println(s"3.Exit")
    println(s"4.Change Text Color")
    print(s"Select an option: ")
    scala.io.StdIn.readLine()
  }

  private def createNewTestBoard(): BoardData = {
    val seed = System.currentTimeMillis() // Use current time as seed for randomness
    val rand = MyRandom(seed) // Create a MyRandom object with the seed

    // Create an empty board
    val board = BoardData.empty(5, 5)

    // Define the positions and word for the word "PROGRAMAR"
    val programarPositions = List((3, 3), (2, 3), (1, 2), (1, 1), (2,1), (3, 0), (3, 1), (4, 2), (4,3))

    // Fill the word "PROGRAMAR" on the board
    val boardWithProgramar = board.setBoardWithWords(List("PROGRAMAR"), List(programarPositions))

    val MLPositions = List((0, 0), (0, 1))
    val boardWithProgramarML = boardWithProgramar.setBoardWithWords(List("ML"), List(MLPositions))

    boardWithProgramarML.completeBoardRandomly(rand, RandomChar.randomChar)._1
  }

  private def play(board: BoardData): Unit = {
    println(s" Current Board:")
    board.display()

    // Ask the user for the word, starting coordinate, and direction
    println(s"\nEnter the word you want to find:")
    val word = scala.io.StdIn.readLine().toUpperCase()

    println(s"\nEnter the starting coordinate (row column), e.g., '3 3':")
    val startCoord = getCoordinate(board)

    println(s"\nEnter the direction (North, South, East, West, NorthEast, NorthWest, SouthEast, SouthWest):")
    val direction = getDirection()

    // Check if the word is found
    val wordFound = board.playUntraditional(word, startCoord, direction)
    println(s"\nThe word '$word' was found: $wordFound")
  }

  private def getCoordinate(board: BoardData): (Int, Int) = {
    var validCoord = false
    var startCoord: (Int, Int) = (0, 0)

    while (!validCoord) {
      val input = scala.io.StdIn.readLine()
      val Array(rowStr, columnStr) = input.split(" ")

      try {
        val row = rowStr.toInt
        val column = columnStr.toInt

        if (row >= 0 && row < board.rows && column >= 0 && column < board.columns) {
          startCoord = (row, column)
          validCoord = true
        } else {
          println(s"Invalid coordinates. Please enter again:")
        }
      } catch {
        case _: NumberFormatException =>
          println(s"Invalid input. Please enter again:")
      }
    }
    startCoord
  }

  private def getDirection(): Direction.Value = {
    val directionStr = scala.io.StdIn.readLine()
    Direction.withName(directionStr)
  }

  private def changeTextColor(): Unit = {
    println(s"Select a color:")
    println(s"0.White")
    println(s"1.Red")
    println(s"2.Green")
    println(s"3.Yellow")
    println(s"4.Blue")

    scala.io.StdIn.readLine() match {
      case "0" =>
        print(Console.WHITE)
      case "1" =>
        print(Console.RED)
      case "2" =>
        print(Console.GREEN)
      case "3" =>
        print(Console.YELLOW)
      case "4" =>
        print(Console.BLUE)
      case _ =>
        println(s"Invalid color option.")
    }
  }
}
