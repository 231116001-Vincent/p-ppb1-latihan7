package com.vharya.latihan.database

import android.content.ContentValues
import android.content.Context

class UsersAdapter(context: Context) {
    private var databaseAdapter: DatabaseAdapter = DatabaseAdapter(context)

    companion object {
        private const val TABLE_NAME = "users"
        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_PASSWORD = "password"
    }

    fun register(username: String, password: String) {
        val database = databaseAdapter.writableDatabase
        val values = ContentValues()

        values.put(COLUMN_USERNAME, username)
        values.put(COLUMN_PASSWORD, password)

        database.insert(TABLE_NAME, null, values)
    }

    fun login(username: String, password: String): Boolean {
        val database = databaseAdapter.readableDatabase

        val cursor = database.rawQuery(
            "SELECT * FROM $TABLE_NAME WHERE $COLUMN_USERNAME = ? AND $COLUMN_PASSWORD = ?",
            arrayOf(username, password)
        )

        return cursor.moveToFirst()
    }
}