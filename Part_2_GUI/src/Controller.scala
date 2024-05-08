import RandomChar.MyRandom
import com.sun.prism.image.Coords
import javafx.collections.FXCollections
import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene.{Parent, Scene}
import javafx.scene.control.{Button, ChoiceBox, Label, TextField}
import javafx.scene.layout.GridPane
import javafx.scene.paint.Color
import javafx.stage.Stage

import scala.annotation.tailrec

class Controller {

  @FXML private var gridPane: GridPane = _

  @FXML private var newBoardButton: Button = _
  @FXML private var checkWordButton: Button = _
  @FXML private var confirmCoordsButton: Button = _
  @FXML private var northButton: Button = _
  @FXML private var westButton: Button = _
  @FXML private var eastButton: Button = _
  @FXML private var southButton: Button = _
  @FXML private var startNewGameButton: Button = _
  @FXML private var northWestButton: Button = _
  @FXML private var northEastButton: Button = _
  @FXML private var southEastButton: Button = _
  @FXML private var southWestButton: Button = _

  @FXML private var columnChoice: ChoiceBox[Int] = new ChoiceBox[Int]()
  @FXML private var rowChoice: ChoiceBox[Int] = new ChoiceBox[Int]()

  @FXML private var wordTextField: TextField = _

  @FXML private var errorCoords: Label = _
  @FXML private var columnLabel: Label = _
  @FXML private var rowLabel: Label = _
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

  var selectedCoords = (0, 0)
  var selectedWord = ""
  var filledBoard = new BoardData(0, 0, null)

  type Coord2D = (Int, Int)

  @FXML
  def initialize(): Unit = {
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
    setMidColor(letter20, red1)
    letter21.setText("E")
    letter21.setStyle("-fx-background-color: " + green1); // Pleasant pastel green
    letter22.setText("L")
    letter22.setStyle("-fx-background-color: " + green1); // Pleasant pastel green
    letter23.setText("L")
    letter23.setStyle("-fx-background-color: " + green1); // Pleasant pastel green
    letter24.setText("O")
    letter24.setStyle("-fx-background-color: " + blue1); // Pleasant pastel green
    setMidColor(letter24, green1)
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

  def setColor(label: Label, colorCode:String): Unit = {
    label.setStyle("-fx-background-color: " + colorCode)
  }

  def setMidColor(label: Label, colorCode: String): Unit = {
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
    val styleString = label.getStyle

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
    val game = createNewTestBoard()
    startNewGameButton.setVisible(false)
    newBoardButton.setVisible(true)
    wordTextField.setVisible(true)
    checkWordButton.setVisible(true)
    confirmCoordsButton.setVisible(false)
    columnLabel.setVisible(false)
    columnChoice.setVisible(false)
    rowLabel.setVisible(false)
    rowChoice.setVisible(false)
    errorCoords.setVisible(false)
    northButton.setVisible(false)
    westButton.setVisible(false)
    eastButton.setVisible(false)
    southButton.setVisible(false)
    northEastButton.setVisible(false)
    northWestButton.setVisible(false)
    southEastButton.setVisible(false)
    southWestButton.setVisible(false)
    wordTextField.setText("")
  }

  private def createNewTestBoard(): BoardData = {

    // Create an empty board
    val board = BoardData.empty(5, 5)

    val numbersFile = "seed.txt" // Define the file to store seed and random numbers
    val rand = MyRandom() // Create an instance of MyRandom with the file

    val FinalBoard = board.newWords("listWords.txt")

    val labelMatrix  = {
      Array(Array(letter00, letter01, letter02, letter03, letter04),
        Array(letter10, letter11, letter12, letter13, letter14),
        Array(letter20, letter21, letter22, letter23, letter24),
        Array(letter30, letter31, letter32, letter33, letter34),
        Array(letter40, letter41, letter42, letter43, letter44))
    }

    // Call completeBoardRandomly with the MyRandom instance
    filledBoard = FinalBoard.completeBoardRandomly(rand, MyRandom.randomChar)._1

    filledBoard.display()

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

  def checkWordClicked(): Unit = {
    checkWordButton.setVisible(false)
    confirmCoordsButton.setVisible(true)
    selectedWord = (wordTextField.getText).toUpperCase
    val items = FXCollections.observableArrayList(1, 2, 3, 4, 5)
    columnLabel.setVisible(true)
    columnChoice.setItems(items)
    columnChoice.setVisible(true)
    rowLabel.setVisible(true)
    rowChoice.setItems(items)
    rowChoice.setVisible(true)
  }

  def confirmCoords(): Unit = {
    if(columnChoice.getValue != null && rowChoice.getValue != null) {
      errorCoords.setVisible(false)
      val labelMatrix  = {
        Array(Array(letter00, letter01, letter02, letter03, letter04),
          Array(letter10, letter11, letter12, letter13, letter14),
          Array(letter20, letter21, letter22, letter23, letter24),
          Array(letter30, letter31, letter32, letter33, letter34),
          Array(letter40, letter41, letter42, letter43, letter44))
      }
      selectedCoords = ((rowChoice.getValue)-1, (columnChoice.getValue)-1)
      northButton.setVisible(true)
      westButton.setVisible(true)
      eastButton.setVisible(true)
      southButton.setVisible(true)
      northEastButton.setVisible(true)
      northWestButton.setVisible(true)
      southEastButton.setVisible(true)
      southWestButton.setVisible(true)
    } else {
      errorCoords.setVisible(true)
    }
  }

  def NorthClicked(): Unit = {
    println("North Click")
    val coords = Array[Coord2D]()
    filledBoard.play(selectedWord, selectedCoords, Direction.North, coords)
  }

  def NorthWestClicked(): Unit = {
    println("NorthWest Click")
    val coords = Array[Coord2D]()
    filledBoard.play(selectedWord, selectedCoords, Direction.NorthWest, coords)
  }

  def NorthEastClicked(): Unit = {
    println("NorthEast Click")
    val coords = Array[Coord2D]()
    filledBoard.play(selectedWord, selectedCoords, Direction.NorthEast, coords)
  }

  def SouthClicked(): Unit = {
    println("South Click")
    val coords = Array[Coord2D]()
    val result = filledBoard.play(selectedWord, selectedCoords, Direction.South, coords)
    println("Word found? " + result._1 + "\n Coords: " + result._2(0))
    if(result._1) {
      paintSquares(result._2, green1)
    }
  }

  def SouthEastClicked(): Unit = {
    println("SouthEast Click")
    val coords = Array[Coord2D]()
    filledBoard.play(selectedWord, selectedCoords, Direction.SouthEast, coords)
  }

  def SouthWestClicked(): Unit = {
    println("SouthWest Click")
    val coords = Array[Coord2D]()
    filledBoard.play(selectedWord, selectedCoords, Direction.SouthWest, coords)
  }

  def EastClicked(): Unit = {
    println("East Click")
    val coords = Array[Coord2D]()
    filledBoard.play(selectedWord, selectedCoords, Direction.East, coords)
  }

  def WestClicked(): Unit = {
    println("West Click")
    val coords = Array[Coord2D]()
    filledBoard.play(selectedWord, selectedCoords, Direction.West, coords)
  }

  def paintSquares(coords: Array[Coord2D], colorCode: String): Unit = {
    val labelMatrix  = {
      Array(Array(letter00, letter01, letter02, letter03, letter04),
        Array(letter10, letter11, letter12, letter13, letter14),
        Array(letter20, letter21, letter22, letter23, letter24),
        Array(letter30, letter31, letter32, letter33, letter34),
        Array(letter40, letter41, letter42, letter43, letter44))
    }
    def paint(coords: Array[Coord2D], size: Int): Unit = size match {
      case 0 =>
      case _ => {
        val nextCord = coords.head
        setColor(labelMatrix(nextCord._1)(nextCord._2), colorCode)
        paint(coords.tail, size - 1)
      }
    }
    paint(coords, coords.length)
  }

}