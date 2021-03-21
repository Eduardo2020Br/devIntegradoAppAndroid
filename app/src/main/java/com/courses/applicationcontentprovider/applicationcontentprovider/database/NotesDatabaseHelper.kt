package com.courses.applicationcontentprovider.applicationcontentprovider.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns._ID
import java.security.AccessControlContext

class NotesDatabaseHelper(
    context: Context
):SQLiteOpenHelper(context, "databaseNotes", null, 1 ) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL( "CREATE TABLE_$TABLE_NOTES ("+
                "$_ID INTEGER NOT NULL PRIMARY KEY)" +
                "$TITLE_NOTES NOT NULL, + " +
                "$DESCRIPTION_NOTES TEXT NOT NULL)")  // Script de criação da tabela
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
    companion object {
        const val TABLE_NOTES: String = "Notes"         // Tabela 1a letra maiuscula
        const val TITLE_NOTES: String = "title"         // coluna td minuscula
        const val DESCRIPTION_NOTES: String = "description" // coluna td minuscula
    }

}