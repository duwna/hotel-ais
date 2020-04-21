package clients

import DataBaseModel
import java.sql.ResultSet

data class Client(
        val idClient: Int,
        val firstName: String,
        val lastName: String,
        val patronymic: String,
        val passportNumber: String,
        val passportData: String
) : DataBaseModel {

    override fun makeInsertString(): String =
            "INSERT INTO Client VALUES (default, '$firstName', '$lastName', '$patronymic', '$passportNumber', '$passportData')"

    override fun makeUpdateString(): String = "UPDATE Client SET " +
            "firstName = '$firstName', " +
            "lastName = '$lastName', " +
            "patronymic = '$patronymic', " +
            "passportNumber = '$passportNumber', " +
            "passportData = '$passportData' " +
            "WHERE idClient = $idClient"

    companion object {
        fun toObject(resultSet: ResultSet) = Client(
                resultSet.getInt("idClient"),
                resultSet.getString("firstName"),
                resultSet.getString("lastName"),
                resultSet.getString("patronymic"),
                resultSet.getString("passportNumber"),
                resultSet.getString("passportData")
        )
    }
}