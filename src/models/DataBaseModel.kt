package models

import java.sql.ResultSet

interface DataBaseModel {
    fun makeInsertString(): String
    fun makeUpdateString(): String
}