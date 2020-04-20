package room

import database.DbHandler
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import utils.showAlert
import java.net.URL
import java.sql.SQLIntegrityConstraintViolationException
import java.util.*


class RoomController : Initializable {

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
    private lateinit var tableRoom: TableView<Room>

    @FXML
    private lateinit var columnNumber: TableColumn<Room, Int>

    @FXML
    private lateinit var columnBedsCount: TableColumn<Room, Int>

    @FXML
    private lateinit var columnPrice: TableColumn<Room, Int>

    @FXML
    private lateinit var columnCategory: TableColumn<Room, String>

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        initRoomTable()
        showRoom()

        tableRoom.setOnMouseClicked {
            val room = tableRoom.selectionModel.selectedItem
            if (room != null) {
                tfNumber.text = room.number.toString()
                tfBedsCount.text = room.bedsCount.toString()
                tfPrice.text = room.price.toString()
                tfCategory.text = room.category
            }
        }

        btnAdd.setOnAction { insertRoom() }
        btnDelete.setOnAction { deleteDetail() }
        btnUpdate.setOnAction { updateDetail() }
        btnRefresh.setOnMouseClicked { showRoom() }
    }


    private fun updateDetail() {
        if (validate()) {
            DbHandler.updateRoom(Room(
                    tableRoom.selectionModel.selectedItem.idRoom,
                    tfNumber.text.toInt(),
                    tfBedsCount.text.toInt(),
                    tfPrice.text.toInt(),
                    tfCategory.text
            ))
            showRoom()
        }
    }

    private fun deleteDetail() {
        try {
            tableRoom.selectionModel.selectedItem.idRoom?.let { DbHandler.deleteRoom(it) }
            showRoom()
        } catch (e: SQLIntegrityConstraintViolationException) {
            showAlert("Комната учавствует в связи!")
        }
    }

    private fun insertRoom() {
        if (validate()) {
            DbHandler.addRoom(Room(
                    0,
                    tfNumber.text.toInt(),
                    tfBedsCount.text.toInt(),
                    tfPrice.text.toInt(),
                    tfCategory.text
            ))
            showRoom()
            tableRoom.scrollTo(tableRoom.items.size)
        }
    }

    private fun showRoom() {
        tableRoom.items = DbHandler.getRoomList()
    }

    private fun validate(): Boolean = when {
        tfNumber.text.isBlank() || tfNumber.text.toLongOrNull() == null -> {
            showAlert("Номер заполнен неправильно.")
            false
        }
        tfBedsCount.text.isBlank() || tfBedsCount.text.toLongOrNull() == null -> {
            showAlert("Количество спальных мест заполнено неправильно.")
            false
        }
        tfPrice.text.isBlank() || tfPrice.text.toLongOrNull() == null -> {
            showAlert("Цена заполнена неправильно.")
            false
        }
        tfCategory.text.isBlank() -> {
            showAlert("Категория не должна быть пустой")
            false
        }
        else -> true
    }

    private fun initRoomTable() {
        columnNumber.cellValueFactory = PropertyValueFactory("number")
        columnBedsCount.cellValueFactory = PropertyValueFactory("bedsCount")
        columnPrice.cellValueFactory = PropertyValueFactory("price")
        columnCategory.cellValueFactory = PropertyValueFactory("category")
    }

}