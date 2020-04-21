package reservation

import clients.Client
import database.DbHandler
import javafx.beans.property.SimpleObjectProperty
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import room.Room
import utils.getDayDifference
import utils.showAlert
import java.net.URL
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class ReservationController : Initializable {

    @FXML
    private lateinit var tableReservation: TableView<Reservation>

    @FXML
    private lateinit var columnReservationNumber: TableColumn<Reservation, Int>

    @FXML
    private lateinit var columnReservationClient: TableColumn<Reservation, String>

    @FXML
    private lateinit var columnReservationStart: TableColumn<Reservation, String>

    @FXML
    private lateinit var columnReservationEnd: TableColumn<Reservation, String>

    @FXML
    private lateinit var columnReservationSum: TableColumn<Reservation, Int>

    @FXML
    private lateinit var labelClient: Label

    @FXML
    private lateinit var labelNumber: Label

    @FXML
    private lateinit var tfStart: TextField

    @FXML
    private lateinit var tfEnd: TextField

    @FXML
    private lateinit var btnAdd: Button

    @FXML
    private lateinit var btnEdit: Button

    @FXML
    private lateinit var btnDelete: Button

    @FXML
    private lateinit var tableClient: TableView<Client>

    @FXML
    private lateinit var columnLastName: TableColumn<Client, String>

    @FXML
    private lateinit var columnFirstName: TableColumn<Client, String>

    @FXML
    private lateinit var columnPatronymic: TableColumn<Client, String>

    @FXML
    private lateinit var tableRoom: TableView<Room>

    @FXML
    private lateinit var columnNumber: TableColumn<Room, Int>

    @FXML
    private lateinit var columnNumberOfBeds: TableColumn<Room, Int>

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        initTables()
        showReservation()

        tableRoom.setOnMouseClicked {

            btnEdit.isDisable = true
            btnAdd.isDisable = false

            val room = tableRoom.selectionModel.selectedItem
            if (room != null) labelNumber.text = room.number.toString()

        }

        tableClient.setOnMouseClicked {

            btnEdit.isDisable = true
            btnAdd.isDisable = false

            val client = tableClient.selectionModel.selectedItem
            if (client != null) labelClient.text = "${client.firstName} ${client.lastName} ${client.patronymic}"
        }

        tableReservation.setOnMouseClicked {

            btnEdit.isDisable = false
            btnAdd.isDisable = true

            val reservation = tableReservation.selectionModel.selectedItem
            if (reservation != null) {
                val client = reservation.client
                labelNumber.text = reservation.room?.number.toString()
                labelClient.text = "${client?.firstName} ${client?.lastName} ${client?.patronymic}"
                tfEnd.text = reservation.endDate
                tfStart.text = reservation.startDate
            }
        }

        btnAdd.setOnAction { addReservation() }
        btnDelete.setOnAction { deleteReservation() }
        btnEdit.setOnAction { updateReservation() }
    }

    private fun updateReservation() {
        if (validate(tableReservation.selectionModel.selectedItem.idRoom, true)) {
            val sum = getDayDifference(tfStart.text, tfEnd.text) *
                    (tableReservation.selectionModel.selectedItem.room?.price ?: 1)
            DbHandler.updateReservation(Reservation(
                    tableReservation.selectionModel.selectedItem.idReservation,
                    tableReservation.selectionModel.selectedItem.idRoom,
                    tableReservation.selectionModel.selectedItem.idClient,
                    tfStart.text,
                    tfEnd.text,
                    sum,
                    null,
                    null
            ))
        }
        showReservation()
    }

    private fun deleteReservation() {
        DbHandler.deleteReservation(tableReservation.selectionModel.selectedItem.idReservation)
        showReservation()
    }

    private fun addReservation() {
        if (validate(tableRoom.selectionModel.selectedItem.idRoom, false)) {
            val sum = getDayDifference(tfStart.text, tfEnd.text) *
                    tableRoom.selectionModel.selectedItem.price
            DbHandler.addCReservation(Reservation(
                    0,
                    tableRoom.selectionModel.selectedItem.idRoom,
                    tableClient.selectionModel.selectedItem.idClient,
                    tfStart.text,
                    tfEnd.text,
                    sum,
                    null,
                    null
            ))
        }
        showReservation()
    }

    private fun validate(idRoom: Int, isUpdate: Boolean): Boolean {
        try {
            SimpleDateFormat("YYYY-MM-DD").parse(tfStart.text)
            SimpleDateFormat("YYYY-MM-DD").parse(tfEnd.text)
        } catch (e: ParseException) {
            showAlert("Дата введена некорректно")
            return false
        }
        if (!isUpdate && !DbHandler.checkReservation(idRoom, tfStart.text to tfEnd.text)) {
            showAlert("Комната уже забронирована на эти дни")
            return false
        }
        return true
    }

    private fun initTables() {
        columnFirstName.cellValueFactory = PropertyValueFactory("firstName")
        columnLastName.cellValueFactory = PropertyValueFactory("lastName")
        columnPatronymic.cellValueFactory = PropertyValueFactory("patronymic")

        columnNumber.cellValueFactory = PropertyValueFactory("number")
        columnNumberOfBeds.cellValueFactory = PropertyValueFactory("bedsCount")

        columnReservationNumber.setCellValueFactory { cellData ->
            return@setCellValueFactory SimpleObjectProperty(
                    cellData.value.room?.number)
        }
        columnReservationClient.setCellValueFactory { cellData ->
            val client = cellData.value.client
            return@setCellValueFactory SimpleObjectProperty(
                    "${client?.firstName} ${client?.lastName} ${client?.patronymic}")
        }
        columnReservationStart.setCellValueFactory { cellData ->
            return@setCellValueFactory SimpleObjectProperty(
                    cellData.value.startDate)
        }
        columnReservationEnd.setCellValueFactory { cellData ->
            return@setCellValueFactory SimpleObjectProperty(
                    cellData.value.endDate)
        }
        columnReservationSum.setCellValueFactory { cellData ->
            return@setCellValueFactory SimpleObjectProperty(
                    cellData.value.sum)
        }

        tableRoom.items = DbHandler.getRoomList()
        tableClient.items = DbHandler.getClientList()
    }

    private fun showReservation() {
        tableReservation.items = DbHandler.getReservationList()
    }

}