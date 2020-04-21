package worker

import DataBaseModel
import java.sql.ResultSet

data class Worker(
        val idWorker: Int,
        val firstName: String,
        val lastName: String,
        val patronymic: String,
        val position: String,
        val schedule: String,
        val time: String
) : DataBaseModel {

    override fun makeInsertString(): String =
            "INSERT INTO Worker VALUES (default, '$firstName', '$lastName', '$patronymic', '$position', '$schedule', '$time')"

    override fun makeUpdateString(): String = "UPDATE Worker SET " +
            "firstName = '$firstName', " +
            "lastName = '$lastName', " +
            "patronymic = '$patronymic', " +
            "position = '$position', " +
            "schedule = '$schedule', " +
            "time = '$time' " +
            "WHERE idWorker = $idWorker"

    companion object {
        fun toObject(resultSet: ResultSet) = Worker(
                resultSet.getInt("idWorker"),
                resultSet.getString("firstName"),
                resultSet.getString("lastName"),
                resultSet.getString("patronymic"),
                resultSet.getString("position"),
                resultSet.getString("schedule"),
                resultSet.getString("time")
        )
    }
}