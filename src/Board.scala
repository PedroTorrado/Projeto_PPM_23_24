import scala.annotation.tailrec
import scala.util.Random
import scala.collection.mutable.ListBuffer
import RandomChar.{MyRandom, randomChar}

object Direction extends Enumeration {
  type Direction = Value
  val North, South, East, West, NorthEast, NorthWest, SouthEast, SouthWest = Value
}

case class BoardData(rows: Int, columns: Int, grid: List[List[Char]]) {
  type Board = List[List[Char]]
  type Coord2D = (Int, Int)

  // Method to fill one cell on the board
  def fillOneCell(letter: Char, coord: Coord2D): BoardData = {
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

  def completeBoardRandomly(rand: MyRandom, f: MyRandom => (Char, MyRandom)): BoardData = {
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

    fillEntireBoard(this, rand, 0, 0)
  }

  def setBoardWithWords(words: List[String], positions: List[List[Coord2D]]): BoardData = {
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




  def makeMatrix: Board = grid

  def display(): Unit = {
    grid.foreach(row => println(row.mkString("|", " ", "|")))
  }
}

object BoardData {
  // Factory method to create an empty board
  def empty(rows: Int, columns: Int): BoardData = {
    val emptyGrid = List.fill(rows)(List.fill(columns)(' '))
    BoardData(rows, columns, emptyGrid)
  }
}