package reservation

import clients.Client
import DataBaseModel
import room.Room
import java.sql.ResultSet

data class Reservation(
        val idReservation: Int,
        val idRoom: Int,
        val idClient: Int,
        val startDate: String,
        val endDate: String,
        val sum: Int,
        val room: Room?,
        val client: Client?
) : DataBaseModel {

    override fun makeInsertString(): String =
            "INSERT INTO Reservation VALUES (default, $idRoom, $idClient, '$startDate', '$endDate', $sum)"
                    .also { println(it) }

    override fun makeUpdateString(): String = "UPDATE Reservation SET " +
            "idRoom = $idRoom, " +
            "idClient = $idClient, " +
            "startDate = '$startDate', " +
            "endDate = '$endDate', " +
            "sum = $sum " +
            "WHERE idReservation = $idReservation"

    companion object {
        fun toObject(resultSet: ResultSet): Reservation = Reservation(
                resultSet.getInt("idReservation"),
                resultSet.getInt("idRoom"),
                resultSet.getInt("idClient"),
                resultSet.getString("startDate"),
                resultSet.getString("endDate"),
                resultSet.getInt("sum"),
                Room(
                        resultSet.getInt("idRoom"),
                        resultSet.getInt("number"),
                        resultSet.getInt("bedsCount"),
                        resultSet.getInt("price"),
                        resultSet.getString("category")
                ),
                Client(
                        resultSet.getInt("idClient"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("patronymic"),
                        resultSet.getString("passportNumber"),
                        resultSet.getString("passportData")
                )
        )
    }
}

