package worker

import database.DbHandler
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import utils.showAlert
import java.net.URL
import java.sql.SQLIntegrityConstraintViolationException
import java.util.*


class WorkerController : Initializable {


    @FXML
    private lateinit var btnRefresh: Label

    @FXML
    private lateinit var tfFirstName: TextField

    @FXML
    private lateinit var tfLastName: TextField

    @FXML
    private lateinit var tfPatronymic: TextField

    @FXML
    private lateinit var tfPosition: TextField

    @FXML
    private lateinit var tfSchedule: TextField

    @FXML
    private lateinit var tfTime: TextField

    @FXML
    private lateinit var btnAdd: Button

    @FXML
    private lateinit var btnUpdate: Button

    @FXML
    private lateinit var btnDelete: Button

    @FXML
    private lateinit var tableWorker: TableView<Worker>

    @FXML
    private lateinit var columnLastName: TableColumn<Worker, String>

    @FXML
    private lateinit var columnFirstName: TableColumn<Worker, String>

    @FXML
    private lateinit var columnPatronymic: TableColumn<Worker, String>

    @FXML
    private lateinit var columnPosition: TableColumn<Worker, String>

    @FXML
    private lateinit var columnSchedule: TableColumn<Worker, String>

    @FXML
    private lateinit var columnTime: TableColumn<Worker, String>

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        initClientTable()
        showWorker()

        tableWorker.setOnMouseClicked {
            val worker = tableWorker.selectionModel.selectedItem
            if (worker != null) {
                tfFirstName.text = worker.firstName
                tfLastName.text = worker.lastName
                tfPatronymic.text = worker.patronymic
                tfSchedule.text = worker.schedule
                tfTime.text = worker.time
                tfPosition.text = worker.position
            }
        }

        btnAdd.setOnAction { insertWorker() }
        btnDelete.setOnAction { deleteWorker() }
        btnUpdate.setOnAction { updateWorker() }
        btnRefresh.setOnMouseClicked { showWorker() }
    }


    private fun updateWorker() {
        if (validate()) {
            DbHandler.updateWorker(Worker(
                    tableWorker.selectionModel.selectedItem.idWorker,
                    tfFirstName.text,
                    tfLastName.text,
                    tfPatronymic.text,
                    tfPosition.text,
                    tfSchedule.text,
                    tfTime.text
            ))
            showWorker()
        }
    }

    private fun deleteWorker() {
        try {
            tableWorker.selectionModel.selectedItem.idWorker?.let { DbHandler.deleteWorker(it) }
            showWorker()
        } catch (e: SQLIntegrityConstraintViolationException) {
            showAlert("Работник учавствует в связи!")
        }
    }

    private fun insertWorker() {
        if (validate()) {
            DbHandler.addWorker(Worker(
                    0,
                    tfFirstName.text,
                    tfLastName.text,
                    tfPatronymic.text,
                    tfPosition.text,
                    tfSchedule.text,
                    tfTime.text
            ))
            showWorker()
            tableWorker.scrollTo(tableWorker.items.size)
        }
    }

    private fun showWorker() {
        tableWorker.items = DbHandler.getWorkerList()
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
        tfPosition.text.isBlank() -> {
            showAlert("Должность не должна быть пустой")
            false
        }
        tfSchedule.text.isBlank() -> {
            showAlert("Расписание не должно быть пустым")
            false
        }
        tfTime.text.isBlank() -> {
            showAlert("Время работыне должно быть пустыми")
            false
        }

        else -> true
    }

    private fun initClientTable() {
        columnFirstName.cellValueFactory = PropertyValueFactory("firstName")
        columnLastName.cellValueFactory = PropertyValueFactory("lastName")
        columnPatronymic.cellValueFactory = PropertyValueFactory("patronymic")
        columnPosition.cellValueFactory = PropertyValueFactory("position")
        columnSchedule.cellValueFactory = PropertyValueFactory("schedule")
        columnTime.cellValueFactory = PropertyValueFactory("time")
    }
}