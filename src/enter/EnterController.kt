package enter


import database.*
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.Node
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import javafx.scene.paint.Color
import utils.openNextWindow
import utils.showAlert
import java.net.URL
import java.sql.SQLException
import java.util.*


class EnterController : Initializable {

    @FXML
    private lateinit var tfLogin: TextField

    @FXML
    private lateinit var tfPassword: PasswordField

    @FXML
    private lateinit var btnReg: Button

    @FXML
    private lateinit var labelConnection: Label

    @FXML
    private lateinit var tfUser: TextField

    @FXML
    private lateinit var tfPass: TextField

    @FXML
    private lateinit var tfHost: TextField

    @FXML
    private lateinit var tfPort: TextField

    @FXML
    private lateinit var labelConnect: Label

    private var isConnectionVisible = false

    override fun initialize(location: URL?, resources: ResourceBundle?) {

        initConnectionFields()
        tryConnection()

        labelConnect.setOnMouseClicked { tryConnection() }
        labelConnection.setOnMouseClicked { changeConnectionState() }


        btnReg.setOnAction {
            if (DbHandler.isConnected && validateEnter()) enter(it)
        }
    }

    private fun enter(event: ActionEvent) {
        val user = DbHandler.getUserByEmail(tfLogin.text)
        if (user != null && user.password == tfPassword.text) {
            DbHandler.currentUser = user
            openNextWindow(javaClass.getResource("/start/start.fxml"), "Добро пожаловать")
            (event.source as Node).scene.window.hide()
        } else showAlert("Неверный логин или пароль!")
    }

    private fun tryConnection() {
        try {
            val url = "$DRIVER://${tfHost.text}:${tfPort.text}/$DATABASE"
            DbHandler.makeConnection(url, tfUser.text, tfPass.text)
            DbHandler.isConnected = true
            labelConnection.textFill = Color.web("#116811")
        } catch (e: SQLException) {
            DbHandler.isConnected = false
            labelConnection.textFill = Color.web("#ff0000")
        }
    }

    private fun changeConnectionState() {
        if (isConnectionVisible) {
            labelConnect.isVisible = false
            tfPort.isVisible = false
            tfHost.isVisible = false
            tfUser.isVisible = false
            tfPass.isVisible = false
        } else {
            labelConnect.isVisible = true
            tfPort.isVisible = true
            tfHost.isVisible = true
            tfUser.isVisible = true
            tfPass.isVisible = true
        }
        isConnectionVisible = !isConnectionVisible
    }

    private fun initConnectionFields() {
        tfUser.text = USER
        tfHost.text = HOST
        tfPass.text = PASSWORD
        tfPort.text = PORT
    }

    private fun validateEnter(): Boolean {

        var isValid = true

        if (tfLogin.text.isBlank()) {
            tfLogin.style = styleHintRed
            isValid = false
        } else tfLogin.style = styleHintGray

        if (tfPassword.text.isBlank()) {
            tfPassword.style = styleHintRed
            isValid = false
        } else tfPassword.style = styleHintGray

        return isValid
    }

    companion object {
        const val styleHintRed = "-fx-prompt-text-fill: red;"
        const val styleHintGray = "-fx-prompt-text-fill: gray;"
        const val styleTextRed = "-fx-text-inner-color: red;"
        const val styleTextBlack = "-fx-text-inner-color: black;"
    }
}
