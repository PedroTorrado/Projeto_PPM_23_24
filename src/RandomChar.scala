import scala.util.Random

object RandomChar {
  case class MyRandom(seed: Long) {
    def nextInt: (Int, MyRandom) = {
      val random = new Random(seed)
      val randomNumber = random.nextInt(26)
      val newSeed = random.nextLong()
      val nextRandom = MyRandom(newSeed)
      (randomNumber, nextRandom)
    }
  }

  def randomChar(rand: MyRandom): (Char, MyRandom) = {
    val (randomInt, nextRand) = rand.nextInt
    val randomChar = ('A'.toInt + randomInt).toChar
    (randomChar, nextRand)
  }
}