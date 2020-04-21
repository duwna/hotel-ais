package cleaning

import database.DbHandler
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import utils.showAlert
import java.net.URL
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class CleaningController : Initializable {

    @FXML
    private lateinit var btnRefresh: Label

    @FXML
    private lateinit var tfNumber: TextField

    @FXML
    private lateinit var tfDateTime: TextField

    @FXML
    private lateinit var btnAdd: Button

    @FXML
    private lateinit var btnUpdate: Button

    @FXML
    private lateinit var btnDelete: Button

    @FXML
    private lateinit var tableCleaning: TableView<Cleaning>

    @FXML
    private lateinit var columnNumber: TableColumn<Cleaning, Int>

    @FXML
    private lateinit var columnDateTime: TableColumn<Cleaning, String>


    override fun initialize(location: URL?, resources: ResourceBundle?) {
        initCleaningTable()
        showCleaning()

        tableCleaning.setOnMouseClicked {
            val cleaning = tableCleaning.selectionModel.selectedItem
            if (cleaning != null) {
                tfDateTime.text = cleaning.dateTime
                tfNumber.text = cleaning.number.toString()
            }
        }

        btnAdd.setOnAction { insertCleaning() }
        btnDelete.setOnAction { deleteCleaning() }
        btnUpdate.setOnAction { updateCleaning() }
        btnRefresh.setOnMouseClicked { showCleaning() }
    }


    private fun updateCleaning() {
        if (validate()) {
            DbHandler.updateCleaning(Cleaning(
                    tableCleaning.selectionModel.selectedItem.idCleaning,
                    tfNumber.text.toInt(),
                    tfDateTime.text
            ))
            showCleaning()
        }
    }

    private fun deleteCleaning() {
        tableCleaning.selectionModel.selectedItem.idCleaning?.let { DbHandler.deleteCleaning(it) }
        showCleaning()
    }

    private fun insertCleaning() {
        if (validate()) {
            DbHandler.addCleaning(Cleaning(
                    0,
                    tfNumber.text.toInt(),
                    tfDateTime.text
            ))
            showCleaning()
            tableCleaning.scrollTo(tableCleaning.items.size)
        }
    }

    private fun showCleaning() {
        tableCleaning.items = DbHandler.getCleaningList()
    }

    private fun validate(): Boolean = when {
        tfDateTime.text.isBlank() -> {
            showAlert("Дата не должна быть пустой.")
            false
        }
        tfNumber.text.isBlank() || tfNumber.text.toIntOrNull() == null -> {
            showAlert("Номер введен некорректно.")
            false
        }
        !DbHandler.checkRoomExists(tfNumber.text.toInt()) -> {
            showAlert("Комнаты с таким номером не существует.")
            false
        }
        else -> try {
            SimpleDateFormat("YYYY-MM-DD hh:mm:ss").parse(tfDateTime.text)
            true
        } catch (e: ParseException) {
            showAlert("Дата введена некоректно.")
            false
        }
    }

    private fun initCleaningTable() {
        columnDateTime.cellValueFactory = PropertyValueFactory("dateTime")
        columnNumber.cellValueFactory = PropertyValueFactory("number")
    }
}