package room

import javafx.fxml.FXML
import javafx.scene.control.*


class RoomController {


    @FXML
    private lateinit var btnRefresh: Label

    @FXML
    private lateinit var tfNumber: TextField

    @FXML
    private lateinit var tfBedsCount: TextField

    @FXML
    private lateinit var tfPrice: TextField

    @FXML
    private lateinit var tfCategory: TextField

    @FXML
    private lateinit var btnAdd: Button

    @FXML
    private lateinit var btnUpdate: Button

    @FXML
    private lateinit var btnDelete: Button

    @FXML
    private lateinit var tableRoom: TableView<*>

    @FXML
    private lateinit var columnNumber: TableColumn<*, *>

    @FXML
    private lateinit var columnBedsCount: TableColumn<*, *>

    @FXML
    private lateinit var columnPrice: TableColumn<*, *>

    @FXML
    private lateinit var columnCategory: TableColumn<*, *>
}