package com.example.repository

import com.example.data.database.tables.UserTable
import com.example.model.User
import com.example.model.UserRequest
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepository {
    suspend fun findUserByEmail(email: String): User? = transaction {
        //select * from users where email = email
        UserTable.select { UserTable.email eq email }.map {
            User(
                it[UserTable.name],
                it[UserTable.email],
                it[UserTable.id]
            )
        }.singleOrNull()
    }

    suspend fun createUser(user: UserRequest): User = transaction {
        val id = UserTable.insert {
            it[name] = user.name
            it[email] = user.email
            it[password] = user.password
        } get UserTable.id
        User(user.name, user.email, id)
    }
}