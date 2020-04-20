package clients

import database.DbHandler
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import models.Client
import utils.showAlert
import java.net.URL
import java.sql.SQLIntegrityConstraintViolationException
import java.util.*


class ClientController : Initializable {
    @FXML
    private lateinit var btnRefresh: Label

    @FXML
    private lateinit var tfFirstName: TextField

    @FXML
    private lateinit var tfLastName: TextField

    @FXML
    private lateinit var tfPatronymic: TextField

    @FXML
    private lateinit var tfPassportNumber: TextField

    @FXML
    private lateinit var tfPassportData: TextField

    @FXML
    private lateinit var btnAdd: Button

    @FXML
    private lateinit var btnUpdate: Button

    @FXML
    private lateinit var btnDelete: Button

    @FXML
    private lateinit var tableClients: TableView<Client>

    @FXML
    private lateinit var columnFirstName: TableColumn<Client, String>

    @FXML
    private lateinit var columnLastName: TableColumn<Client, String>

    @FXML
    private lateinit var columnPatronymic: TableColumn<Client, String>

    @FXML
    private lateinit var columnPassportNumber: TableColumn<Client, String>

    @FXML
    private lateinit var columnPassportData: TableColumn<Client, String>


    override fun initialize(location: URL?, resources: ResourceBundle?) {
        initClientTable()
        showClients()

        tableClients.setOnMouseClicked {
            val client = tableClients.selectionModel.selectedItem
            if (client != null) {
                tfFirstName.text = client.firstName
                tfLastName.text = client.lastName
                tfPatronymic.text = client.patronymic
                tfPassportNumber.text = client.passportNumber
                tfPassportData.text = client.passportData
            }
        }

        btnAdd.setOnAction { insertClient() }
        btnDelete.setOnAction { deleteDetail() }
        btnUpdate.setOnAction { updateDetail() }
        btnRefresh.setOnMouseClicked { showClients() }
    }


    private fun updateDetail() {
        if (validate()) {
            DbHandler.updateClient(Client(
                    tableClients.selectionModel.selectedItem.idClient,
                    tfFirstName.text,
                    tfLastName.text,
                    tfPatronymic.text,
                    tfPassportNumber.text,
                    tfPassportData.text
            ))
            showClients()
        }
    }

    private fun deleteDetail() {
        try {
            tableClients.selectionModel.selectedItem.idClient?.let { DbHandler.deleteClient(it) }
            showClients()
        } catch (e: SQLIntegrityConstraintViolationException) {
            showAlert("Клиент учавствует в связи!")
        }
    }

    private fun insertClient() {
        if (validate()) {
            DbHandler.addClient(Client(
                    0,
                    tfFirstName.text,
                    tfLastName.text,
                    tfPatronymic.text,
                    tfPassportNumber.text,
                    tfPassportData.text
            ))
            showClients()
            tableClients.scrollTo(tableClients.items.size)
        }
    }

    private fun showClients() {
        tableClients.items = DbHandler.getClientList()
    }

    private fun validate(): Boolean = when {
        tfFirstName.text.isBlank() -> {
            showAlert("Имя не должно быть пустым")
            false
        }
        tfLastName.text.isBlank() -> {
            showAlert("Фамилия не должно быть пустым")
            false
        }
        tfPatronymic.text.isBlank() -> {
            showAlert("Отчество не должно быть пустым")
            false
        }
        tfPassportNumber.text.isBlank() -> {
            showAlert("Номер паспорта не должен быть пустым")
            false
        }
        tfPassportData.text.isBlank() -> {
            showAlert("Паспортные данные не должны быть пустыми")
            false
        }
        else -> true
    }

    private fun initClientTable() {
        columnFirstName.cellValueFactory = PropertyValueFactory("firstName")
        columnLastName.cellValueFactory = PropertyValueFactory("lastName")
        columnPatronymic.cellValueFactory = PropertyValueFactory("patronymic")
        columnPassportNumber.cellValueFactory = PropertyValueFactory("passportNumber")
        columnPassportData.cellValueFactory = PropertyValueFactory("passportData")
    }
}


