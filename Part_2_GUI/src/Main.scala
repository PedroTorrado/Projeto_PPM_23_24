import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage

class Main extends Application {
  override def start(primaryStage: Stage): Unit = {
    primaryStage.setTitle("ZigZag")
    val fxmlLoader =
      new FXMLLoader(getClass.getResource("Controller.fxml"))
    val mainViewRoot = fxmlLoader.load()
    val controller = fxmlLoader.getController[Controller]

    val fxmlLoader2 =
      new FXMLLoader(getClass.getResource("SecondController.fxml"))
    val root2 = fxmlLoader2.load()
    val secondController = fxmlLoader2.getController[SecondController]
    val secondStage: Stage = new Stage()

    controller.setController(secondController)
    secondController.setController(controller)

    secondStage.setScene(new Scene(root2))
    primaryStage.setScene(new Scene(mainViewRoot))
    primaryStage.show()
  }
}   

object FxApp {
  def main(args: Array[String]): Unit = {
    Application.launch(classOf[Main], args: _*)
  }
}
