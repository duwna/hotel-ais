package database

import cleaning.Cleaning
import clients.Client
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import user.User
import reservation.Reservation
import room.Room
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement

object DbHandler {

    private lateinit var connection: Connection
    private lateinit var statement: Statement
    lateinit var currentUser: User

    var isConnected = false
    fun makeConnection(url: String, user: String, pass: String) {
        connection = DriverManager.getConnection(url, user, pass)
        statement = connection.createStatement()
    }

    fun getUserList(): ObservableList<User> {
        val userList = FXCollections.observableArrayList<User>()
        val resultSet: ResultSet = statement.executeQuery("SELECT * FROM User")
        while (resultSet.next()) userList.add(User.toObject(resultSet))
        return userList
    }

    fun addUser(user: User) = statement.executeUpdate(user.makeInsertString())
    fun updateUser(user: User) = statement.executeUpdate(user.makeUpdateString())
    fun deleteUser(id: Int) = statement.executeUpdate("DELETE FROM User WHERE idUser = $id")


    fun getUserByEmail(email: String): User? {
        val resultSet = statement.executeQuery("SELECT * FROM User WHERE email = '$email'")
        return if (resultSet.next()) User.toObject(resultSet)
        else null
    }

    fun getClientList(): ObservableList<Client> {
        val clientList = FXCollections.observableArrayList<Client>()
        val resultSet: ResultSet = statement.executeQuery("SELECT * FROM Client")
        while (resultSet.next()) clientList.add(Client.toObject(resultSet))
        return clientList
    }

    fun addClient(client: Client) = statement.executeUpdate(client.makeInsertString())
    fun updateClient(client: Client) = statement.executeUpdate(client.makeUpdateString())
    fun deleteClient(id: Int) = statement.executeUpdate("DELETE FROM Client WHERE idClient = $id")


    fun getRoomList(): ObservableList<Room> {
        val roomList = FXCollections.observableArrayList<Room>()
        val resultSet: ResultSet = statement.executeQuery("SELECT * FROM Room")
        while (resultSet.next()) roomList.add(Room.toObject(resultSet))
        return roomList
    }

    fun addRoom(Room: Room) = statement.executeUpdate(Room.makeInsertString())
    fun updateRoom(Room: Room) = statement.executeUpdate(Room.makeUpdateString())
    fun deleteRoom(id: Int) = statement.executeUpdate("DELETE FROM Room WHERE idRoom = $id")


    fun getCleaningList(): ObservableList<Cleaning> {
        val roomList = FXCollections.observableArrayList<Cleaning>()
        val resultSet: ResultSet = statement.executeQuery("SELECT * FROM Cleaning")
        while (resultSet.next()) roomList.add(Cleaning.toObject(resultSet))
        return roomList
    }

    fun addCleaning(Cleaning: Cleaning) = statement.executeUpdate(Cleaning.makeInsertString())
    fun updateCleaning(Cleaning: Cleaning) = statement.executeUpdate(Cleaning.makeUpdateString())
    fun deleteCleaning(id: Int) = statement.executeUpdate("DELETE FROM Cleaning WHERE idCleaning = $id")

    fun checkRoomExists(number: Int): Boolean =
            statement.executeQuery("SELECT * FROM Room WHERE number = $number").next()


    fun getReservationList(): ObservableList<Reservation> {
        val reservationList = FXCollections.observableArrayList<Reservation>()
        val resultSet: ResultSet = statement.executeQuery("SELECT * FROM Reservation " +
                "JOIN Client on Reservation.idClient = Client.idClient " +
                "JOIN Room on Reservation.idRoom = Room.idRoom ")
        while (resultSet.next()) reservationList.add(Reservation.toObject(resultSet))
        return reservationList
    }

    fun addCReservation(reservation: Reservation) = statement.executeUpdate(reservation.makeInsertString())
    fun deleteReservation(id: Int) = statement.executeUpdate("DELETE FROM Reservation WHERE idReservation = $id")
    fun updateReservation(reservation: Reservation) = statement.executeUpdate(reservation.makeUpdateString())

    fun checkReservation(idRoom: Int, newDates: Pair<String, String>): Boolean {
        val list = mutableListOf<Pair<String, String>>()
        val resultSet: ResultSet = statement.executeQuery("SELECT * FROM Reservation WHERE idRoom = $idRoom")
        while (resultSet.next())
            list.add(resultSet.getString("startDate") to resultSet.getString("endDate"))

        list.forEach { oldDates ->
            when {
                oldDates.second >= newDates.first && oldDates.first <= newDates.first -> return false
                oldDates.first <= newDates.second && oldDates.second >= newDates.second -> return false
                oldDates.first >= newDates.first && oldDates.second <= newDates.second -> return false
                oldDates.first <= newDates.first && oldDates.second >= newDates.second -> return false
            }
        }

        return true
    }
}