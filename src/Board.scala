import RandomChar.{MyRandom, randomChar}

import scala.annotation.tailrec
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
  def completeBoardRandomly(board: Board, r: MyRandom, f: MyRandom => (Char, MyRandom)): Board = {
    @tailrec
    def fillEntireBoard(newBoard: Board, rand: MyRandom, x: Int, y: Int): Board = {
      if (y < newBoard.rows) {
        if (x < newBoard.columns) {
          if (newBoard.getCell(x, y) == ' ') {
            val (randomLetter, newRand) = f(rand)
            val updatedBoard = newBoard.fillOneCell(randomLetter, Coord2D(x, y))
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

    fillEntireBoard(board, r, 0, 0)
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