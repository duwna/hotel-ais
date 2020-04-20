package clients

import javafx.fxml.FXML
import javafx.scene.control.*


class ClientController {
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
    private lateinit var tableClients: TableView<*>

    @FXML
    private lateinit var columnFirstName: TableColumn<*, *>

    @FXML
    private lateinit var columnLastName: TableColumn<*, *>

    @FXML
    private lateinit var columnPatronymic: TableColumn<*, *>

    @FXML
    private lateinit var columnPassportNumber: TableColumn<*, *>

    @FXML
    private lateinit var columnPassportData: TableColumn<*, *>
}