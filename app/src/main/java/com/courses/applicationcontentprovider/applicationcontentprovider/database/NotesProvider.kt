package com.courses.applicationcontentprovider.applicationcontentprovider.database

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.media.UnsupportedSchemeException
import android.net.Uri
import android.os.Build
import android.provider.BaseColumns._ID
import android.provider.ContactsContract.Intents.Insert.NOTES
import android.provider.SettingsSlicesContract.BASE_URI
import androidx.annotation.RequiresApi
import com.courses.applicationcontentprovider.applicationcontentprovider.database.NotesDatabaseHelper.Companion.TABLE_NOTES
import java.net.URI
import java.util.regex.Matcher

class NotesProvider : ContentProvider() {

    private lateinit var mUriMatcher: UriMatcher
    private lateinit var dbHelper: NotesDatabaseHelper

    override fun onCreate(): Boolean {
        mUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        mUriMatcher.addURI(AUTHORITY, "notes", NOTES)
        mUriMatcher.addURI(AUTHORITY, "notes/#", NOTES_BY_ID)
        if (context != null) { dbHelper = NotesDatabaseHelper(context as Context)
        return true
        }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int =
            if (mUriMatcher.match(uri) == NOTES_BY_ID) {
                val db: SQLiteDatabase = dbHelper.writableDatabase
                val linesAffect = db.delete(TABLE_NOTES, "$_ID=?", arrayOf(uri.lastPathSegment))
                db.close()
                context?.contentResolver?.notifyChange(uri, null)
                return linesAffect
            } else {
                throw UnsupportedSchemeException("Uri invalida para exclusão!")
            }
    }

    override fun getType(uri: Uri): String? = throw UnsupportedSchemeException ("Uri não implementado")

    @RequiresApi(Build.VERSION_CODES.P)
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        if (mUriMatcher.match(uri) == NOTES) {
            val db: SQLiteDatabase = dbHelper.writableDatabase
            val id: Long = db.insert(TABLE_NOTES, null, values)
            val insertUri = Uri.withAppendedPath(BASE_URI, id.toString())
            db.close()
            context?.contentResolver?.notifyChange(uri, null)
            return insertUri
        }else {
            throw UnsupportedSchemeException("Uri inválida para inserção!")
        }
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String? ): Cursor? {
        return when {
            mUriMatcher.match()
        }

    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        TODO("Implement this to handle requests to update one or more rows.")
    }

    companion object {
        const val AUTHORITY = "com.courses.applicationcontentprovider.provider"
        val BASE_URI = Uri.parse( "content://$AUTHORITY")
        val URI_NOTES  = Uri.withAppendedPath( BASE_URI,"notes")

        //"content://com.courses.applicationcontentprovider.provider/notes"

        const val NOTES = 1
        const val NOTES_BY_ID = 2
    }
}