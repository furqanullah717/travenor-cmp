package com.example.data.database

import com.example.data.database.tables.UserTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init() {
        Database.connect(
            url = "jdbc:mysql://localhost:3306/travenor_db",
            driver = "com.mysql.cj.jdbc.Driver",
            user = "root",
            password = "root"
        )
        transaction {
            SchemaUtils.create(
                UserTable
            )
            TransactionManager.manager.defaultIsolationLevel =
                java.sql.Connection.TRANSACTION_SERIALIZABLE
        }
    }
}