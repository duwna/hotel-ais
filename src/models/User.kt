package models

import java.sql.ResultSet

data class User(
        val idUser: Int,
        val position: Int,
        val email: String,
        val phone: String,
        val firstName: String,
        val lastName: String,
        val patronymic: String,
        val password: String
) : DataBaseModel {

    override fun makeInsertString(): String =
            "INSERT INTO User VALUES (default, $position, '$email', '$phone', '$firstName', " +
                    "'$lastName', '$patronymic', '$password')"

    override fun makeUpdateString(): String = "UPDATE User SET " +
            "position = '$position', " +
            "email = '$email', " +
            "phone = '$phone', " +
            "firstName = '$firstName', " +
            "lastName = '$lastName', " +
            "patronymic = '$patronymic', " +
            "password = '$password', " +
            "WHERE $idUser = $idUser"

    val positionName: String
        get() = when (position) {
            ADMIN -> "Администратор"
            DISP -> "Диспетчер"
            else -> throw IllegalArgumentException()
        }

    companion object {
        const val ADMIN = 0
        const val DISP = 1

        fun toObject(resultSet: ResultSet): User = User(
                resultSet.getInt("idUser"),
                resultSet.getInt("position"),
                resultSet.getString("email"),
                resultSet.getString("phone"),
                resultSet.getString("firstName"),
                resultSet.getString("lastName"),
                resultSet.getString("patronymic"),
                resultSet.getString("password")
        )
    }
}


