import RandomChar.MyRandom
import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene.{Parent, Scene}
import javafx.scene.control.{Button, Label}
import javafx.scene.layout.GridPane
import javafx.scene.paint.Color
import javafx.stage.Stage

import scala.annotation.tailrec

class Controller {

  @FXML private var gridPane: GridPane = _

  @FXML private var newBoardButton: Button = _
  @FXML private var checkWordButton: Button = _

  @FXML private var letter00: Label = _
  @FXML private var letter01: Label = _
  @FXML private var letter02: Label = _
  @FXML private var letter03: Label = _
  @FXML private var letter04: Label = _
  @FXML private var letter10: Label = _
  @FXML private var letter11: Label = _
  @FXML private var letter12: Label = _
  @FXML private var letter13: Label = _
  @FXML private var letter14: Label = _
  @FXML private var letter20: Label = _
  @FXML private var letter21: Label = _
  @FXML private var letter22: Label = _
  @FXML private var letter23: Label = _
  @FXML private var letter24: Label = _
  @FXML private var letter30: Label = _
  @FXML private var letter31: Label = _
  @FXML private var letter32: Label = _
  @FXML private var letter33: Label = _
  @FXML private var letter34: Label = _
  @FXML private var letter40: Label = _
  @FXML private var letter41: Label = _
  @FXML private var letter42: Label = _
  @FXML private var letter43: Label = _
  @FXML private var letter44: Label = _

  final val green1 = "#c1e1c1"
  final val blue1 = "#89cEEE"
  final val red1 = "#F4C1C1"
  final val white1 = "#FFFFFF"

  @FXML
  def initialize(): Unit = {
    checkWordButton.setVisible(false)
    letter00.setText("A")
    letter00.setStyle("-fx-background-color: " + white1); // Pleasant pastel green
    letter01.setText("B")
    letter01.setStyle("-fx-background-color: " + white1); // Pleasant pastel green
    letter02.setText("C")
    letter02.setStyle("-fx-background-color: " + white1); // Pleasant pastel green
    letter03.setText("D")
    letter03.setStyle("-fx-background-color: " + white1); // Pleasant pastel green
    letter04.setText("E")
    letter04.setStyle("-fx-background-color: " + blue1); // Pleasant pastel green
    letter10.setText("F")
    letter10.setStyle("-fx-background-color: " + white1); // Pleasant pastel green
    letter11.setText("G")
    letter11.setStyle("-fx-background-color: " + white1); // Pleasant pastel green
    letter12.setText("H")
    letter12.setStyle("-fx-background-color: " + white1); // Pleasant pastel green
    letter13.setText("I")
    letter13.setStyle("-fx-background-color: " + white1); // Pleasant pastel green
    letter14.setText("M")
    letter14.setStyle("-fx-background-color: " + blue1); // Pleasant pastel green
    letter20.setText("H")
    letter20.setStyle("-fx-background-color: " + green1); // Pleasant pastel green
    setColor(letter20, red1)
    letter21.setText("E")
    letter21.setStyle("-fx-background-color: " + green1); // Pleasant pastel green
    letter22.setText("L")
    letter22.setStyle("-fx-background-color: " + green1); // Pleasant pastel green
    letter23.setText("L")
    letter23.setStyle("-fx-background-color: " + green1); // Pleasant pastel green
    letter24.setText("O")
    letter24.setStyle("-fx-background-color: " + blue1); // Pleasant pastel green
    setColor(letter24, green1)
    letter30.setText("A")
    letter30.setStyle("-fx-background-color: " + red1); // Pleasant pastel green
    letter31.setText("W")
    letter31.setStyle("-fx-background-color: " + blue1); // Pleasant pastel green
    letter32.setText("E")
    letter32.setStyle("-fx-background-color: " + blue1); // Pleasant pastel green
    letter33.setText("L")
    letter33.setStyle("-fx-background-color:" + blue1); // Pleasant pastel green
    letter34.setText("C")
    letter34.setStyle("-fx-background-color: " + blue1); // Pleasant pastel green
    letter40.setText("L")
    letter40.setStyle("-fx-background-color: " + red1); // Pleasant pastel green
    letter41.setText("L")
    letter41.setStyle("-fx-background-color: " + red1); // Pleasant pastel green
    letter42.setText("O")
    letter42.setStyle("-fx-background-color: " + red1); // Pleasant pastel green
    letter43.setText("X")
    letter43.setStyle("-fx-background-color: " + white1); // Pleasant pastel green
    letter44.setText("Y")
    letter44.setStyle("-fx-background-color: " + white1); // Pleasant pastel green
  }


  def setColor(label: Label, colorCode: String): Unit = {
    val weight = 0.5; // Increased weight for more influence of colorCode
    val currentColor = getColorValue(label)
    val newColor = {
      val color1 = Color.web(currentColor)
      val color2 = Color.web(colorCode)
      Color.color(
        color1.getRed * (1 - weight) + color2.getRed * weight,
        color1.getGreen * (1 - weight) + color2.getGreen * weight,
        color1.getBlue * (1 - weight) + color2.getBlue * weight
      )
    }
    label.setStyle("-fx-background-color: #" + newColor.toString.substring(2)) // Set the new color
  }

  def resetColor(label: Label): Unit = {
    label.setStyle("-fx-background-color: " + white1)
  }

  def getColorValue(label: Label): String = {
    val styleString = label.getStyle()

    if (styleString != null && styleString.startsWith("-fx-background-color: ")) {
      val colorStartIndex = styleString.indexOf(": ") + 2 // Skip "-fx-background-color: "
      styleString.substring(colorStartIndex)
    } else {
      println("Label does not have a background color set")
      styleString // Or set a default value
    }
  }

  @FXML private var startButton: Button = _

  def startButtonPressed(): Unit = {
    //println("start button clicked")
    val game = createNewTestBoard()
    checkWordButton.setVisible(true)
  }

  private def createNewTestBoard(): BoardData = {

    // Create an empty board
    val board = BoardData.empty(5, 5)

    // Define the positions and word for the word "PROGRAMAR"
    val programarPositions = List((3, 3), (2, 3), (1, 2), (1, 1), (2, 1), (3, 0), (3, 1), (4, 2), (4, 3))

    // Fill the word "PROGRAMAR" on the board
    val boardWithProgramar = board.setBoardWithWords(List("PROGRAMAR"), List(programarPositions))

    val numbersFile = "seed.txt" // Define the file to store seed and random numbers
    val rand = MyRandom() // Create an instance of MyRandom with the file

    // Call completeBoardRandomly with the MyRandom instance
    val (filledBoard, _) = boardWithProgramar.completeBoardRandomly(rand, MyRandom.randomChar)

    filledBoard

    val labelMatrix  = {
        Array(Array(letter00, letter01, letter02, letter03, letter04),
          Array(letter10, letter11, letter12, letter13, letter14),
          Array(letter20, letter21, letter22, letter23, letter24),
          Array(letter30, letter31, letter32, letter33, letter34),
          Array(letter40, letter41, letter42, letter43, letter44))
    }

    @tailrec
    def changeGUI(x: Int, y: Int): Unit = x match {
      case 5 => return
      case _ => y match {
        case 5 => changeGUI(x+1, 0)
        case _ => {
          labelMatrix(x)(y).setText(filledBoard.getCell(x, y).toString)
          resetColor(labelMatrix(x)(y))
          changeGUI(x, y+1)
        }
        }
    }
    changeGUI(0, 0)
    filledBoard
  }

}