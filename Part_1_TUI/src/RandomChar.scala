import scala.util.{Try, Success, Failure}
import java.io.{File, PrintWriter}
import scala.io.Source

object RandomChar {
  case class MyRandom() {
    val numbersFile = "seed.txt"
    private val file = new File(numbersFile)

    // Initialize the file with the seed
    if (!file.exists()) {
      val writer = new PrintWriter(file)
      writer.println("34546567686853423424345576879") // Write initial seed value (0) to the file
      writer.close()
    }

    def nextInt: (Int, MyRandom) = {
      Try {
        // Open the file and read its first line
        val source = Source.fromFile(file)
        val firstLine = source.getLines().next()
        source.close()

        // Extract the first 10 characters from the first line (change 10 to your desired number)
        val numCharsToRead = 3
        val currentSeed = firstLine.take(numCharsToRead).toLong
        //println(currentSeed)

        // Generate the next random number using the seed
        val random = new scala.util.Random(currentSeed)
        val nextRandom = random.nextInt

        // Compute the next seed and create the next MyRandom instance
        val nextSeed = currentSeed * 119
        val nextRand = MyRandom()

        // Update the seed in the file
        val writer = new PrintWriter(file)
        writer.println(nextSeed)
        writer.close()

        (nextRandom, nextRand)
      } match {
        case Success(value) => value
        case Failure(exception) => throw new RuntimeException("Failed to read random numbers file", exception)
      }
    }
  }

  object MyRandom {
    def randomChar(rand: MyRandom): (Char, MyRandom) = {
      val (randomInt, nextRand) = rand.nextInt
      val randomChar = ('A'.toInt + normalize(randomInt)).toChar
      (randomChar, nextRand)
    }
  }

  def normalize(number: Int): Int = {
    val result = number % 26 // 26 is the number of letters in the English alphabet
    if (result >= 0) result else result + 26
  }
}
