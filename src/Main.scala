import scala.util.Random

object Main {
  def main(args: Array[String]): Unit = {
    val seed = 0// Use o tempo atual como semente para garantir aleatoriedade

    val rand = Tabuleiro.MyRandom(seed) // Criar uma instância de MyRandom

    val (randomLetter, newRand) = Tabuleiro.randomChar(rand) // Chamar randomChar com a instância de MyRandom
    println(s"Letra aleatória: $randomLetter")

    val (randomLetter2, _) = Tabuleiro.randomChar(newRand)
    println(s"Outra letra aleatória: $randomLetter2")
  }
}
