import javafx.fxml.FXML
import javafx.scene.control.Button

class SecondController {

  @FXML private var playAgain: Button = _
  @FXML private var quit: Button = _
  private var controller: Controller = _

  def setController(controller: Controller): Unit = {
    this.controller = controller
  }

  def onPlayAgainClicked(): Unit = {
    playAgain.getScene.getWindow.hide()
    controller.unlockEverything()
  }

  def onQuitClicked(): Unit = {
    playAgain.getScene.getWindow.hide()
    controller.closeWindow()
  }

}
