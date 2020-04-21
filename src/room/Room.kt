package room

import DataBaseModel
import java.sql.ResultSet

data class Room(
        val idRoom: Int,
        val number: Int,
        val bedsCount: Int,
        val price: Int,
        val category: String
) : DataBaseModel {

    override fun makeInsertString(): String =
            "INSERT INTO Room VALUES (default, $number, $bedsCount, $price, '$category')"


    override fun makeUpdateString(): String = "UPDATE Room SET " +
            "idRoom = $idRoom, " +
            "number = $number, " +
            "bedsCount = $bedsCount, " +
            "price = $price, " +
            "category = '$category' " +
            "WHERE idRoom = $idRoom"

    companion object {

        fun toObject(resultSet: ResultSet): Room = Room(
                resultSet.getInt("idRoom"),
                resultSet.getInt("number"),
                resultSet.getInt("bedsCount"),
                resultSet.getInt("price"),
                resultSet.getString("category")
        )
    }
}



