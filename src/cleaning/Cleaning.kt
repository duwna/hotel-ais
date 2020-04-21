package cleaning

import models.DataBaseModel
import java.sql.ResultSet

data class Cleaning(
        val idCleaning: Int,
        val number: Int,
        val dateTime: String
) : DataBaseModel {

    override fun makeInsertString(): String =
            "INSERT INTO Cleaning VALUES (default, $number, '$dateTime')"

    override fun makeUpdateString(): String  =  "UPDATE Cleaning SET " +
            "number = $number, " +
            "dateTime = '$dateTime' " +
            "WHERE idCleaning = $idCleaning"

    companion object {

        fun toObject(resultSet: ResultSet) = Cleaning (
                resultSet.getInt("idCleaning"),
                resultSet.getInt("number"),
                resultSet.getString("dateTime")
        )
    }
}

