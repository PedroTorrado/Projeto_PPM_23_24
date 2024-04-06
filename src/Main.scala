import scala.util.Random

object Main {
  def main(args: Array[String]): Unit = {
    val (randomLetter) = Tabuleiro.randomChar(Random) // Chamar randomchar com a instância de MyRandom
    println(s"Letra aleatória: $randomLetter")
  }
}
