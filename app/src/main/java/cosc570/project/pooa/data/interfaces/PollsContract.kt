package cosc570.project.pooa.data.interfaces

import android.content.ContentUris
import android.net.Uri
import android.provider.BaseColumns
import cosc570.project.pooa.data.databasehelper.CONTENT_AUTHORITY_URI

object PollsContract {
    internal const val TABLE_NAME = "Polls"

    val CONTENT_URI: Uri = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME)

    object Columns {
        const val ID = BaseColumns._ID
        const val OBSERVATION = "observation"
        const val APPEND = "append"
        const val SPECIAL_NOTES = "special_notes"
        const val TEXT_ADD_CHANGE = "text_add_change"
        const val DELETE_OB = "delete_ob"
        const val VOTES = "votes"
        const val VOTES_FOR = "votes_for"
        const val GLOBAL = "global"
        const val PROCEDURE = "procedure"
    }

    fun getId(uri: Uri): Long {
        return ContentUris.parseId(uri)
    }

    fun buildUriFromId(id: Long): Uri {
        return ContentUris.withAppendedId(CONTENT_URI, id)
    }
}