import RandomChar.{MyRandom, randomChar}

import scala.util.Random

case class Coord2D(x: Int, y: Int)

case class Board(rows: Int, columns: Int) {
  val grid: Array[Array[Char]] = Array.fill(rows, columns)(' ')

  def fillOneCell(letter: Char, coord: Coord2D): Board = {
    if (isValidCoord(coord)) {
      grid(coord.x)(coord.y) = letter
    }
    this // Retorna o próprio objeto Board modificado
  }

  private def isValidCoord(coord: Coord2D): Boolean = {
    coord.x >= 0 && coord.x < rows && coord.y >= 0 && coord.y < columns
  }

  def getCell(x: Int ,y : Int): Char = {
    val char = grid(x)(y)
    char
  }

  // Método recursivo para preencher todo o tabuleiro a partir da posição (x, y)
  def fillEntireBoard(board: Board, seed: Long, rand: MyRandom, x: Int, y: Int): Unit = {
    if (y < board.columns) { // Check if we are still within the columns of the board
      if (x < board.rows) { // Check if we are still within the rows of the board
        if (board.getCell(x, y) == ' ') { // Check if the cell at (x, y) is empty
          val (randomLetter, newRand) = randomChar(rand) // Generate a random letter and update the random generator state
          board.fillOneCell(randomLetter, Coord2D(x, y)) // Fill the current cell with a random letter
        }
        fillEntireBoard(board, seed, rand, x + 1, y) // Recursively call to fill the next cell in the same row
      } else { // If we reach the end of the row, move to the next row and start from the first column
        fillEntireBoard(board, seed, rand, 0, y + 1) // Recursively call to fill the first cell of the next row
      }
    }
  }


  def setBoardWithWords(board: Board, words: List[String], positions: List[List[Coord2D]]): Board = {
    // Create a copy of the original board to preserve it

    // Iterate over each word and its corresponding positions
    for ((word, coords) <- words.zip(positions)) {
      // Iterate over each character of the word and its corresponding position
      for ((char, coord) <- word.zip(coords)) {
        // Check if the coordinates are within the boundaries of the board
        if (coord.x >= 0 && coord.x < board.rows && coord.y >= 0 && coord.y < board.columns) {
          // Fill the cell with the character from the word
          board.fillOneCell(char, coord)
        }
      }
    }

    // Return the modified board
    board
  }

  def display(): Unit = {
    for (row <- grid) {
      println(row.mkString("|", "|", "|"))
    }
  }
}