import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene.{Parent, Scene}
import javafx.scene.control.Button
import javafx.stage.Stage

class SecondController {


  @FXML private var playAgain: Button = _
  @FXML private var quit: Button = _
  private var controller: Controller = _

  def setController(controller: Controller): Unit = {
    this.controller = controller
  }

  def onPlayAgainClicked(): Unit = {
    playAgain.getScene.getWindow.hide()
    controller.startButtonPressed()
  }

  def onQuitClicked(): Unit = {
    quit.getScene.getWindow.hide()
    controller.closeWindow()
  }

}
