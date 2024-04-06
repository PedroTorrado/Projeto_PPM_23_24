import scala.util.Random

object Tabuleiro {

  def randomChar(r: Random): Char = {
    val i = r.nextInt(26)
    val randomChar = ('A'.toInt + i).toChar
    (randomChar)
  }


}