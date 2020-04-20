package database

import javafx.collections.FXCollections
import javafx.collections.ObservableList
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

    fun getUserByEmail(email: String): User? {
        val resultSet = statement.executeQuery("SELECT * FROM User WHERE email = '$email'")
        return if (resultSet.next()) User.toObject(resultSet)
        else null
    }

    fun updateUser(user: User) = statement.executeUpdate(user.makeUpdateString())

}