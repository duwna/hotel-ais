package user

import database.DbHandler
import javafx.beans.property.SimpleObjectProperty
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import utils.showAlert
import java.net.URL
import java.util.*


class UserController : Initializable {

    @FXML
    private lateinit var btnRefresh: Label

    @FXML
    private lateinit var tfFirstName: TextField

    @FXML
    private lateinit var tfLastName: TextField

    @FXML
    private lateinit var tfPatronymic: TextField

    @FXML
    private lateinit var tfEmail: TextField

    @FXML
    private lateinit var tfPassword: TextField

    @FXML
    private lateinit var tfPhone: TextField

    @FXML
    private lateinit var rbAdmin: RadioButton

    @FXML
    private lateinit var group: ToggleGroup

    @FXML
    private lateinit var rbDisp: RadioButton

    @FXML
    private lateinit var btnAdd: Button

    @FXML
    private lateinit var btnUpdate: Button

    @FXML
    private lateinit var btnDelete: Button

    @FXML
    private lateinit var tableUser: TableView<User>

    @FXML
    private lateinit var columnPosition: TableColumn<User, String>

    @FXML
    private lateinit var columnEmail: TableColumn<User, String>

    @FXML
    private lateinit var columnFirstName: TableColumn<User, String>

    @FXML
    private lateinit var columnLastName: TableColumn<User, String>

    @FXML
    private lateinit var columnPatronymic: TableColumn<User, String>

    @FXML
    private lateinit var columnPhone: TableColumn<User, String>

    @FXML
    private lateinit var columnPassword: TableColumn<User, String>

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        initUserTable()
        showUser()

        tableUser.setOnMouseClicked {
            val user = tableUser.selectionModel.selectedItem
            if (user != null) {
                tfEmail.text = user.email
                tfFirstName.text = user.firstName
                tfLastName.text = user.lastName
                tfPassword.text = user.password
                tfPatronymic.text = user.patronymic
                tfPhone.text = user.phone
            }
        }

        btnAdd.setOnAction { insertUser() }
        btnDelete.setOnAction { deleteUser() }
        btnUpdate.setOnAction { updateUser() }
        btnRefresh.setOnMouseClicked { showUser() }
    }


    private fun updateUser() {
        if (validate()) {
            DbHandler.updateUser(User(
                    tableUser.selectionModel.selectedItem.idUser,
                    if (rbAdmin.isSelected) User.ADMIN else User.DISP,
                    tfEmail.text,
                    tfPhone.text,
                    tfFirstName.text,
                    tfLastName.text,
                    tfPatronymic.text,
                    tfPassword.text
            ))
            showUser()
        }
    }

    private fun deleteUser() {
        tableUser.selectionModel.selectedItem.idUser?.let { DbHandler.deleteUser(it) }
        showUser()
    }

    private fun insertUser() {
        if (validate()) {
            DbHandler.addUser(User(
                    0,
                    if (rbAdmin.isSelected) 0 else 1,
                    tfEmail.text,
                    tfPhone.text,
                    tfFirstName.text,
                    tfLastName.text,
                    tfPatronymic.text,
                    tfPassword.text
            ))
            showUser()
            tableUser.scrollTo(tableUser.items.size)
        }
    }

    private fun showUser() {
        tableUser.items = DbHandler.getUserList()
    }

    private fun validate(): Boolean = when {
        tfEmail.text.isBlank() -> {
            showAlert("Email не должен быть пустым.")
            false
        }
        tfFirstName.text.isBlank() -> {
            showAlert("Имя не должно быть пустым.")
            false
        }
        tfLastName.text.isBlank() -> {
            showAlert("Фамилия не должна быть пустой.")
            false
        }
        tfPassword.text.isBlank() -> {
            showAlert("Пароль не должен быть пустым.")
            false
        }
        tfPatronymic.text.isBlank() -> {
            showAlert("Отчество не должно быть пустым.")
            false
        }
        tfPhone.text.isBlank() -> {
            showAlert("Телефон не должен быть пустым.")
            false
        }
        tfPhone.text.toLongOrNull() == null -> {
            showAlert("Телефон указан неверно")
            false
        }
        else -> true
    }

    private fun initUserTable() {
        columnEmail.cellValueFactory = PropertyValueFactory("email")
        columnFirstName.cellValueFactory = PropertyValueFactory("firstName")
        columnLastName.cellValueFactory = PropertyValueFactory("lastName")
        columnPassword.cellValueFactory = PropertyValueFactory("password")
        columnPatronymic.cellValueFactory = PropertyValueFactory("patronymic")
        columnPhone.cellValueFactory = PropertyValueFactory("phone")

        columnPosition.setCellValueFactory { cellData ->
            return@setCellValueFactory SimpleObjectProperty(
                    if (cellData.value.position == User.ADMIN) "Администратор"
                    else "Диспетчер"
            )
        }
    }
}