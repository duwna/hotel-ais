package start

import database.DbHandler
import enter.EnterController
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.Node
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import javafx.scene.layout.VBox
import models.User
import models.User.Companion.ADMIN
import models.User.Companion.DISP
import utils.openNextWindow
import utils.showAlert
import java.net.URL
import java.util.*

class StartController : Initializable {

    @FXML
    private lateinit var labelFullName: Label

    @FXML
    private lateinit var labelPosition: Label

    @FXML
    private lateinit var labelClients: Label

    @FXML
    private lateinit var labelRooms: Label

    @FXML
    private lateinit var labelReserv: Label

    @FXML
    private lateinit var labelUsers: Label

    @FXML
    private lateinit var labelWorkers: Label

    @FXML
    private lateinit var labelCleaning: Label

    @FXML
    private lateinit var labelEdit: Label

    @FXML
    private lateinit var vboxEdit: VBox

    @FXML
    private lateinit var tfFirstName: TextField

    @FXML
    private lateinit var tfLastName: TextField

    @FXML
    private lateinit var tfPatronymic: TextField

    @FXML
    private lateinit var tfPhone: TextField

    @FXML
    private lateinit var tfEmail: TextField

    @FXML
    private lateinit var tfPassword: PasswordField

    @FXML
    private lateinit var tfRePassword: PasswordField

    @FXML
    private lateinit var btnSave: Button

    @FXML
    private lateinit var labelExit: Label

    private var user = DbHandler.currentUser

    private var isEdit = false

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        initViews()

        labelClients.setOnMouseClicked {
            openNextWindow(javaClass.getResource("/com/duwna/fxml/detail_provider.fxml"),
                    "Детали и поставщики")
        }
        labelRooms.setOnMouseClicked {
            openNextWindow(javaClass.getResource("/com/duwna/fxml/detail_provider.fxml"),
                    "Детали и поставщики")
        }
        labelReserv.setOnMouseClicked {
            openNextWindow(javaClass.getResource("/com/duwna/fxml/detail_provider.fxml"),
                    "Детали и поставщики")
        }
        labelUsers.setOnMouseClicked {
            openNextWindow(javaClass.getResource("/com/duwna/fxml/detail_provider.fxml"),
                    "Детали и поставщики")
        }
        labelWorkers.setOnMouseClicked {
            openNextWindow(javaClass.getResource("/com/duwna/fxml/detail_provider.fxml"),
                    "Детали и поставщики")
        }
        labelCleaning.setOnMouseClicked {
            openNextWindow(javaClass.getResource("/com/duwna/fxml/detail_provider.fxml"),
                    "Детали и поставщики")
        }

        labelEdit.setOnMouseClicked { changeEdit() }
        labelExit.setOnMouseClicked { event ->
            openNextWindow(javaClass.getResource("/com/duwna/fxml/enter.fxml"), "Вход")
            (event.source as Node).scene.window.hide()
        }
        btnSave.setOnAction {
            if (validateUpdate()) {
                val newUser = User(
                        user.idUser,
                        user.position,
                        tfEmail.text,
                        tfPhone.text,
                        tfFirstName.text,
                        tfLastName.text,
                        tfPatronymic.text,
                        tfPassword.text
                )

                DbHandler.updateUser(newUser)
                user = newUser
                showAlert("Сохранено.")
                initViews()
            }
        }

    }

    private fun initViews() {

        labelFullName.text = "${user.firstName} ${user.lastName} ${user.patronymic}"
        labelPosition.text = user.positionName

        labelClients.isVisible = user.position == ADMIN
        labelRooms.isVisible = user.position == ADMIN
        labelReserv.isVisible = user.position == ADMIN

        labelUsers.isVisible = user.position == DISP
        labelWorkers.isVisible = user.position == DISP
        labelCleaning.isVisible = user.position == DISP

        tfFirstName.text = user.firstName
        tfLastName.text = user.lastName
        tfPatronymic.text = user.patronymic
        tfEmail.text = user.email
        tfPhone.text = user.phone
        tfPassword.text = user.password
        tfRePassword.text = user.password
    }

    private fun changeEdit() {
        isEdit = !isEdit
        vboxEdit.isVisible = isEdit
    }

    private fun validateUpdate(): Boolean {

        var isValid = true

        if (tfFirstName.text.isBlank()) {
            tfFirstName.style = EnterController.styleHintRed
            isValid = false
        } else tfFirstName.style = EnterController.styleHintGray

        if (tfLastName.text.isBlank()) {
            tfLastName.style = EnterController.styleHintRed
            isValid = false
        } else tfLastName.style = EnterController.styleHintGray

        if (tfPatronymic.text.isBlank()) {
            tfPatronymic.style = EnterController.styleHintRed
            isValid = false
        } else tfPatronymic.style = EnterController.styleHintGray

        if (tfEmail.text.isBlank()) {
            tfEmail.style = EnterController.styleHintRed
            isValid = false
        } else tfEmail.style = EnterController.styleHintGray

        when {
            tfPhone.text.isBlank() -> {
                tfPhone.style = EnterController.styleHintRed
                isValid = false
            }
            tfPhone.text.toLongOrNull() == null -> {
                tfPhone.style = EnterController.styleTextRed
                isValid = false
            }
            else -> {
                tfPhone.style = EnterController.styleHintGray
                tfPhone.style = EnterController.styleTextBlack
            }
        }

        if (tfPassword.text.isBlank()) {
            tfPassword.style = EnterController.styleHintRed
            isValid = false
        } else tfPassword.style = EnterController.styleHintGray

        when {
            tfRePassword.text.isBlank() -> {
                tfRePassword.style = EnterController.styleHintRed
                isValid = false
            }
            tfRePassword.text != tfPassword.text -> {
                tfRePassword.style = EnterController.styleTextRed
                isValid = false
            }
            else -> tfRePassword.style = EnterController.styleHintGray
        }
        return isValid
    }

}