package models

interface DataBaseModel {
    fun makeInsertString(): String
    fun makeUpdateString(): String
}