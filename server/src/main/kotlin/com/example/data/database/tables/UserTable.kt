package com.example.data.database.tables

import org.jetbrains.exposed.sql.Table

object UserTable : Table("users") {
    val id = long("id").autoIncrement()
    val email = varchar("email", 255).uniqueIndex()
    val password = varchar("password", 255)
    val name = varchar("name", 255)
    override val primaryKey = PrimaryKey(id)
}