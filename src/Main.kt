import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import utils.openNextWindow

class Main : Application() {
    @Throws(Exception::class)
    override fun start(primaryStage: Stage) {
        val root = FXMLLoader.load<Parent>(javaClass.getResource("/enter/enter.fxml"))
        primaryStage.apply {
            title = "Вход"
            minWidth = 350.0
            minHeight = 600.0
            scene = Scene(root, 1024.0, 768.0)
            show()
        }

        openNextWindow(javaClass.getResource("/clients/clients.fxml"), "")
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(Main::class.java)
        }
    }
}