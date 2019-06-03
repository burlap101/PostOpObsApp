package cosc570.project.pooa.data.interfaces

import android.content.ContentUris
import android.net.Uri
import android.provider.BaseColumns
import cosc570.project.pooa.data.databasehelper.CONTENT_AUTHORITY_URI

object ObservationsContract {

    internal const val TABLE_NAME = "Observations"

    val CONTENT_URI: Uri = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME)

    object Columns {
        const val ID = BaseColumns._ID
        const val NAME = "name"
        const val SPECIAL_NOTES = "special_notes"
        const val POPULARITY = "popularity"
        const val STATUS = "status"
        const val CREATED_BY = "created_by"
        const val PREVIOUS_REVISION = "previous_revision"
        const val URL = "url"
    }

    fun getId(uri: Uri): Long {
        return ContentUris.parseId(uri)
    }

    fun buildUriFromId(id: Long): Uri {
        return ContentUris.withAppendedId(CONTENT_URI, id)
    }

}