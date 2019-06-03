package cosc570.project.pooa.data.interfaces

import android.content.ContentUris
import android.net.Uri
import android.provider.BaseColumns
import cosc570.project.pooa.data.databasehelper.CONTENT_AUTHORITY_URI

object RanksContract {
    internal const val TABLE_NAME = "Ranks"

    val CONTENT_URI: Uri = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME)

    object Columns {
        const val ID = BaseColumns._ID
        const val NAME = "name"
        const val MIN_POINTS = "min_points"
        const val USER_TYPE = "user_type"
        const val DETAIL = "detail"
    }

    fun getId(uri: Uri): Long {
        return ContentUris.parseId(uri)
    }

    fun buildUriFromId(id: Long): Uri {
        return ContentUris.withAppendedId(CONTENT_URI, id)
    }
}