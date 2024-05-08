import RandomChar.MyRandom

object Main {
  def main(args: Array[String]): Unit = {
    jogoPalavrasCruzadas()
  }

  private def jogoPalavrasCruzadas(randomFilledBoard: Option[BoardData] = None): Unit = {
    println(s"Welcome to Crossword Puzzle Game!\n")

    promptOption() match {
      case "1" =>
        val newBoard = createNewTestBoard()
        println(s"New board created:")
        newBoard.display()
        jogoPalavrasCruzadas(Some(newBoard))
      case "2" =>
        randomFilledBoard match {
          case Some(board) => play(board)
          case None        => println(s"Please start a new board first.")
        }
        jogoPalavrasCruzadas(randomFilledBoard)
      case "4" =>
        changeTextColor()
        jogoPalavrasCruzadas(randomFilledBoard)
      case "3" => // Exit
      case _   =>
        println(s"Invalid option. Please select a valid option.")
        jogoPalavrasCruzadas(randomFilledBoard)
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

    // Create an empty board
    val board = BoardData.empty(5, 5)

    // Define the positions and word for the word "PROGRAMAR"
    val programarPositions = List((3, 3), (2, 3), (1, 2), (1, 1), (2,1), (3, 0), (3, 1), (4, 2), (4,3))

    // Fill the word "PROGRAMAR" on the board

    val numbersFile = "seed.txt" // Define the file to store seed and random numbers
    val rand = MyRandom() // Create an instance of MyRandom with the file

    val FinalBoard = board.newWords("listWords.txt")

    // Call completeBoardRandomly with the MyRandom instance
    val (filledBoard, _) = FinalBoard.completeBoardRandomly(rand, MyRandom.randomChar)

    filledBoard
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
    val wordFound = board.play(word, startCoord, direction)
    println(s"\nThe word '$word' was found: $wordFound")
  }

  private def getCoordinate(board: BoardData): (Int, Int) = {
    def getCoordRecursively(): (Int, Int) = {
      val input = scala.io.StdIn.readLine()
      val Array(rowStr, columnStr) = input.split(" ")

      try {
        val row = rowStr.toInt
        val column = columnStr.toInt

        if (row >= 0 && row < board.rows && column >= 0 && column < board.columns) {
          (row, column)
        } else {
          println(s"Invalid coordinates. Please enter again:")
          getCoordRecursively() // Retry recursively if coordinates are invalid
        }
      } catch {
        case _: NumberFormatException =>
          println(s"Invalid input. Please enter again:")
          getCoordRecursively() // Retry recursively if input is invalid
      }
    }

    getCoordRecursively() // Start the recursive process
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
