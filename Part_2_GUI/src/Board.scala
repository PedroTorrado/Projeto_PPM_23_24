import RandomChar.MyRandom

import scala.annotation.{tailrec, unused}
import scala.util.Random
import scala.collection.mutable.ListBuffer
import scala.io.Source

object Direction extends Enumeration {
  type Direction = Value
  val North, South, East, West, NorthEast, NorthWest, SouthEast, SouthWest = Value
}

case class BoardData(rows: Int, columns: Int, grid: List[List[Char]]) {
  private type Board = List[List[Char]]
  type Coord2D = (Int, Int)

  // Method to fill one cell on the board
  private def fillOneCell(letter: Char, coord: Coord2D): BoardData = {
    if (isValidCoord(coord)) {
      val (x, y) = coord
      val updatedGrid = grid.updated(x, grid(x).updated(y, letter))
      BoardData(rows, columns, updatedGrid) // Corrected this line
    } else {
      this
    }
  }

  // Method to check if a coordinate is valid
  private def isValidCoord(coord: Coord2D): Boolean = {
    val (x, y) = coord
    x >= 0 && x < rows && y >= 0 && y < columns
  }

  def getCell(x: Int, y: Int): Char = {
    grid(x)(y)
  }

  def completeBoardRandomly(rand: MyRandom, f: MyRandom => (Char, MyRandom)): (BoardData, MyRandom) = {
    @tailrec
    def fillEntireBoard(newBoard: BoardData, rand: MyRandom, x: Int, y: Int): BoardData = {
      if (y < rows) {
        if (x < columns) {
          if (newBoard.getCell(x, y) == ' ') {
            val (randomLetter, newRand) = f(rand)
            val updatedBoard = newBoard.fillOneCell(randomLetter, (x, y))
            fillEntireBoard(updatedBoard, newRand, x + 1, y)
          } else {
            fillEntireBoard(newBoard, rand, x + 1, y)
          }
        } else {
          fillEntireBoard(newBoard, rand, 0, y + 1)
        }
      } else {
        newBoard
      }
    }

    (fillEntireBoard(this, rand, 0, 0), rand)
  }

  private def setBoardWithWords(words: List[String], positions: List[List[Coord2D]]): BoardData = {
    def fillBoard(board: BoardData, word: String, coords: List[Coord2D]): BoardData = {
      coords.zip(word).foldLeft(board) { case (accBoard, ((x, y), char)) =>
        if (isValidCoord(x, y)) {
          accBoard.fillOneCell(char, (x, y))
        } else {
          accBoard
        }
      }
    }

    val filledBoard = words.zip(positions).foldLeft(this) { case (accBoard, (word, coords)) =>
      fillBoard(accBoard, word, coords)
    }

    filledBoard
  }

  @unused
  def makeMatrix: Board = grid

  private def computeNextCoord(coord: Coord2D, direction: Direction.Value): Coord2D = {
    val (x, y) = coord
    direction match {
      case Direction.North => (x - 1, y)
      case Direction.South => (x + 1, y)
      case Direction.East => (x, y + 1)
      case Direction.West => (x, y - 1)
      case Direction.NorthEast => (x - 1, y + 1)
      case Direction.NorthWest => (x - 1, y - 1)
      case Direction.SouthEast => (x + 1, y + 1)
      case Direction.SouthWest => (x + 1, y - 1)
    }
  }

  @unused
  def playTradicional(word: String, startCoord: Coord2D, direction: Direction.Value): Boolean = {
    @tailrec
    def checkWord(coord: Coord2D, remainingWord: String): Boolean = {
      if (remainingWord.isEmpty) {
        true // All characters of the word matched
      } else if (!isValidCoord(coord)) {
        false // Coordinate is out of bounds
      } else {
        val (x, y) = coord
        val currentLetter = getCell(x, y)
        if (currentLetter != remainingWord.head) {
          false // Current cell doesn't match the next character of the word
        } else {
          // Move to the next cell in the specified direction
          val nextCoord = computeNextCoord(coord, direction)
          checkWord(nextCoord, remainingWord.tail) // Recur with the next cell and remaining word
        }
      }
    }
    checkWord(startCoord, word)
  }

  def play(word: String, startCoord: Coord2D, initialDirection: Direction.Value, coords: Array[Coord2D]): (Boolean, Array[Coord2D]) = word.isEmpty match {
    case true => (true, coords)
    case false => {
      val iCoords = coords :+ startCoord
      val currentLetter = getCell(startCoord._1, startCoord._2)
      // Check if the current letter matches the first letter of the word
      if (word.head == currentLetter) {
        // Check all directions for the remaining word
        val remainingWord = word.tail
        val directions = Direction.values
        var savedCoords = Array[Coord2D]()
        // Check each direction
        val foundInAnyDirection = directions.exists { direction =>
          // Compute the next coordinate based on the current direction
          val nextCoord = computeNextCoord(startCoord, direction)
          // Check if the next coordinate is valid
          if(isValidCoord(nextCoord)) {
            // Recur with the remaining word and the next coordinate in the current direction
            val playRec = play(remainingWord, nextCoord, direction, iCoords)
            savedCoords = playRec._2
            playRec._1
          } else {
            false
          }
        }
        (foundInAnyDirection, savedCoords)
      } else {
        // If the current letter doesn't match, the word cannot be found from this starting point
        (false, coords)
      }
    }
  }

  def display(): Unit = {
    // Converter a lista de listas para uma matriz bidimensional
    val gridArray: Array[Array[Char]] = grid.map(_.toArray).toArray

    // Função interna para imprimir cada linha da grade
    def displayRow(row: Array[Char]): Unit = {
      if (row.nonEmpty) {
        //print(row.head + " ") // Imprimir o primeiro elemento da linha seguido de um espaço
        displayRow(row.tail) // Chamar recursivamente para o restante dos elementos da linha
      } else {
        //println() // Imprimir uma nova linha quando todos os elementos da linha foram impressos
      }
    }

    // Função interna para imprimir cada linha da grade usando recursão
    def displayGrid(grid: Array[Array[Char]]): Unit = {
      if (grid.nonEmpty) {
        displayRow(grid.head) // Imprimir a primeira linha da grade
        displayGrid(grid.tail) // Chamar recursivamente para o restante das linhas da grade
      }
    }

    // Chamar a função displayGrid inicialmente com a matriz bidimensional
    displayGrid(gridArray)
  }

  def newWords(fileName: String): BoardData = {
    // Read the file and store lines in a list
    val fileSource = Source.fromFile("listWords.txt")
    val lines = fileSource.getLines().toList
    fileSource.close()

    // Calculate the number of lines
    val numLines = lines.size

    // Choose a random line index
    val rand = MyRandom()
    val (randomInt, nextRand) = rand.nextInt

    val lineNumber = (randomInt % numLines).abs
    //println(lineNumber)
    // Get the random line
    val fileContent = lines(lineNumber)

    // Define a regular expression pattern to match coordinate pairs
    val coordPattern = """\((\d+),\s*(\d+)\)""".r

    // Extract words from the content excluding numbers
    val words = fileContent.split("[^a-zA-Z]+").filter(_.nonEmpty).toList

    // Extract coordinates from the content
    // Extract coordinates from the content
    val coords = fileContent.split("/").filter(_.nonEmpty).tail.map { segment =>
      coordPattern.findAllMatchIn(segment).map { m =>
        (m.group(1).toInt, m.group(2).toInt): Coord2D
      }.toList
    }.toList

    setBoardWithWords(words, coords)
  }

}

object BoardData {
  // Factory method to create an empty board
  def empty(rows: Int, columns: Int): BoardData = {
    val emptyGrid = List.fill(rows)(List.fill(columns)(' '))
    BoardData(rows, columns, emptyGrid)
  }
}