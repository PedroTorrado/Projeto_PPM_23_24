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

  def display(): Unit = {
    for (row <- grid) {
      println(row.mkString("|", "|", "|"))
    }
  }
}