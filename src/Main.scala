import RandomChar.MyRandom

object Main {
  var currentTextColor: String = "\u001b[0m" // Default text color (reset)

  def main(args: Array[String]): Unit = {
    jogoPalavrasCruzadas()
  }

  def jogoPalavrasCruzadas(): Unit = {
    println(s"${currentTextColor}Welcome to Crossword Puzzle Game!\n${currentTextColor}")

    var randomFilledBoard: Option[BoardData] = None

    Iterator.continually(promptOption())
      .takeWhile(_ != "3")
      .foreach {
        case "1" =>
          randomFilledBoard = Some(createNewTestBoard())
          println(s"${currentTextColor}New board created:${currentTextColor}")
          randomFilledBoard.foreach(_.display())
        case "2" =>
          randomFilledBoard match {
            case Some(board) =>
                play(board)
            case None => println(s"${currentTextColor}Please start a new board first.${currentTextColor}")
          }
        case "4" =>
          changeTextColor()
        case _ =>
          println(s"${currentTextColor}Invalid option. Please select a valid option.${currentTextColor}")
      }
  }

  def promptOption(): String = {
    println(s"${currentTextColor}Options:${currentTextColor}")
    println(s"${currentTextColor}1. Create new board${currentTextColor}")
    println(s"${currentTextColor}2. Play${currentTextColor}")
    println(s"${currentTextColor}3. Exit${currentTextColor}")
    println(s"${currentTextColor}4. Change Text Color${currentTextColor}")
    print(s"${currentTextColor}Select an option: ${currentTextColor}")
    scala.io.StdIn.readLine()
  }

  def createNewTestBoard(): BoardData = {
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

    boardWithProgramarML.completeBoardRandomly(rand, RandomChar.randomChar)
  }

  def play(board: BoardData): Unit = {
    println(s"${currentTextColor}Current Board:${currentTextColor}")
    board.display()

    // Ask the user for the word, starting coordinate, and direction
    println(s"${currentTextColor}\nEnter the word you want to find:${currentTextColor}")
    val word = scala.io.StdIn.readLine().toUpperCase()

    println(s"${currentTextColor}\nEnter the starting coordinate (row column), e.g., '3 3':${currentTextColor}")
    val startCoord = getCoordinate(board)

    println(s"${currentTextColor}\nEnter the direction (North, South, East, West, NorthEast, NorthWest, SouthEast, SouthWest):${currentTextColor}")
    val direction = getDirection()

    // Check if the word is found
    val wordFound = board.playUntraditional(word, startCoord, direction)
    println(s"${currentTextColor}\nThe word '$word' was found: $wordFound${currentTextColor}")
  }

  def getCoordinate(board: BoardData): (Int, Int) = {
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
          println(s"${currentTextColor}Invalid coordinates. Please enter again:${currentTextColor}")
        }
      } catch {
        case _: NumberFormatException =>
          println(s"${currentTextColor}Invalid input. Please enter again:${currentTextColor}")
      }
    }
    startCoord
  }

  def getDirection(): Direction.Value = {
    val directionStr = scala.io.StdIn.readLine()
    Direction.withName(directionStr)
  }

  def changeTextColor(): Unit = {
    println(s"${currentTextColor}Select a color:${currentTextColor}")
    println(s"${currentTextColor}0. White${currentTextColor}")
    println(s"${currentTextColor}1. Red${currentTextColor}")
    println(s"${currentTextColor}2. Green${currentTextColor}")
    println(s"${currentTextColor}3. Yellow${currentTextColor}")
    println(s"${currentTextColor}4. Blue${currentTextColor}")

    scala.io.StdIn.readLine() match {
      case "0" =>
        currentTextColor = "\u001b[0m" // White
      case "1" =>
        currentTextColor = "\u001b[31m" // Red
      case "2" =>
        currentTextColor = "\u001b[32m" // Green
      case "3" =>
        currentTextColor = "\u001b[33m" // Yellow
      case "4" =>
        currentTextColor = "\u001b[34m" // Blue
      case _ =>
        println(s"${currentTextColor}Invalid color option.${currentTextColor}")
    }
  }
}
