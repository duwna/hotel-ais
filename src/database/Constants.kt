package database

const val USER = "root"
const val PASSWORD = "1234"
const val HOST = "localhost"
const val PORT = "3306"

const val DRIVER = "jdbc:mysql"
const val DATABASE = "Hotel?serverTimezone=UTC"

const val URL = "$DRIVER://$HOST:$PORT/$DATABASE"

const val DATE_FORMAT = "yyyy-MM-dd"
const val DATE_TIME_FORMAT = "YYYY-MM-dd HH:mm:ss"

