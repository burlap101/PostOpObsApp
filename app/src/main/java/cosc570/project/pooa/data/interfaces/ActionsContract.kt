package cosc570.project.pooa.data.interfaces

import android.content.ContentUris
import android.drm.DrmStore.DrmObjectType.CONTENT
import android.net.Uri
import android.provider.BaseColumns
import cosc570.project.pooa.data.databasehelper.CONTENT_AUTHORITY
import cosc570.project.pooa.data.databasehelper.CONTENT_AUTHORITY_URI

object ActionsContract {
    internal const val TABLE_NAME = "Actions"

    val CONTENT_URI: Uri = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME)

    const val CONTENT_TYPE = "vnd.android.cursor.dir/vnd.$CONTENT_AUTHORITY.$TABLE_NAME"
    const val CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.$CONTENT_AUTHORITY.$TABLE_NAME"

    object Columns {
        const val ID = BaseColumns._ID
        const val NAME = "name"
        const val POINTS = "points"
    }

    fun getId(uri: Uri): Long {
        return ContentUris.parseId(uri)
    }

    fun buildUriFromId(id: Long): Uri {
        return ContentUris.withAppendedId(CONTENT_URI, id)
    }
}