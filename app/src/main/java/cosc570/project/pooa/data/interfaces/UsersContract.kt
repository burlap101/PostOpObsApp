package cosc570.project.pooa.data.interfaces

import android.content.ContentUris
import android.net.Uri
import android.provider.BaseColumns
import cosc570.project.pooa.data.databasehelper.CONTENT_AUTHORITY_URI

object UsersContract {
    internal const val TABLE_NAME = "Users"

    val CONTENT_URI: Uri = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME)

    object Columns {
        const val ID = BaseColumns._ID
        const val USERNAME = "username"
        const val USER_TYPE = "user_type"
        const val FIRST_NAME = "first_name"
        const val LAST_NAME = "last_name"
        const val EMAIL = "email"
        const val POINTS = "points"
        const val PASSWORD = "password"
    }

    fun getId(uri: Uri): Long {
        return ContentUris.parseId(uri)
    }

    fun buildUriFromId(id: Long): Uri {
        return ContentUris.withAppendedId(CONTENT_URI, id)
    }
}