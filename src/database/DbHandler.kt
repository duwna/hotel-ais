package database

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import clients.Client
import room.Room
import models.User
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

}