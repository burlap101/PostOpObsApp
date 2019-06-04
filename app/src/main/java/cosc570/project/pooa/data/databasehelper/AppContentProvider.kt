package cosc570.project.pooa.data.databasehelper

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteQueryBuilder
import android.drm.DrmStore
import android.net.Uri
import android.util.Log
import cosc570.project.pooa.data.interfaces.*


/**
 * Provider for the app. This is the only class that knows about [AppDatabase]
 */

private const val TAG = "AppContentProvider"

const val CONTENT_AUTHORITY = "cosc570.project.pooa.provider"

private const val ACTIONS = 100
private const val ACTIONS_ID = 101
private const val BLOCKS = 200
private const val BLOCKS_ID = 201
private const val CHAPTERS = 300
private const val CHAPTERS_ID = 301
private const val OBSERVATIONAPPROVER = 400
private const val OBSERVATIONAPPROVER_ID = 401
private const val OBSERVATIONPROCEDURE = 500
private const val OBSERVATIONPROCEDURE_ID = 501
private const val OBSERVATIONS = 600
private const val OBSERVATIONS_ID = 601
private const val OBSERVATIONSTATUS = 700
private const val OBSERVATIONSTATUS_ID = 701
private const val POLLS = 800
private const val POLLS_ID = 801
private const val POLLUSER = 900
private const val POLLUSER_ID = 901
private const val PROCEDURES = 1000
private const val PROCEDURES_ID = 1001
private const val RANKS = 1100
private const val RANKS_ID = 1101
private const val SUBCHAPTERS = 1200
private const val SUBCHAPTERS_ID = 1201
private const val USERS = 1300
private const val USERS_ID = 1301
private const val USERTYPES = 1400
private const val USERTYPES_ID = 1401


val CONTENT_AUTHORITY_URI: Uri = Uri.parse("content://$CONTENT_AUTHORITY")

class AppProvider: ContentProvider() {

    private val uriMatcher by lazy { buildUriMatcher() }

    private fun buildUriMatcher() : UriMatcher {
        Log.d(TAG, "builderUriMatcher: starts")
        val matcher = UriMatcher(UriMatcher.NO_MATCH)

        matcher.addURI(CONTENT_AUTHORITY, ActionsContract.TABLE_NAME, ACTIONS)
        matcher.addURI(CONTENT_AUTHORITY, BlocksContract.TABLE_NAME, BLOCKS)
        matcher.addURI(CONTENT_AUTHORITY, ChaptersContract.TABLE_NAME, CHAPTERS)
        matcher.addURI(CONTENT_AUTHORITY, ObservationApproverContract.TABLE_NAME, OBSERVATIONAPPROVER)
        matcher.addURI(CONTENT_AUTHORITY, ObservationProcedureContract.TABLE_NAME, OBSERVATIONPROCEDURE)
        matcher.addURI(CONTENT_AUTHORITY, ObservationsContract.TABLE_NAME, OBSERVATIONS)
        matcher.addURI(CONTENT_AUTHORITY, ObservationStatusContract.TABLE_NAME, OBSERVATIONSTATUS)
        matcher.addURI(CONTENT_AUTHORITY, PollsContract.TABLE_NAME, POLLS)
        matcher.addURI(CONTENT_AUTHORITY, PollUserContract.TABLE_NAME, POLLUSER)
        matcher.addURI(CONTENT_AUTHORITY, ProceduresContract.TABLE_NAME, PROCEDURES)
        matcher.addURI(CONTENT_AUTHORITY, RanksContract.TABLE_NAME, RANKS)
        matcher.addURI(CONTENT_AUTHORITY, SubChaptersContract.TABLE_NAME, SUBCHAPTERS)
        matcher.addURI(CONTENT_AUTHORITY, UsersContract.TABLE_NAME, USERS)
        matcher.addURI(CONTENT_AUTHORITY, UserTypesContract.TABLE_NAME, USERTYPES)

        matcher.addURI(CONTENT_AUTHORITY, ActionsContract.TABLE_NAME + "/#", ACTIONS_ID)
        matcher.addURI(CONTENT_AUTHORITY, BlocksContract.TABLE_NAME + "/#", BLOCKS_ID)
        matcher.addURI(CONTENT_AUTHORITY, ChaptersContract.TABLE_NAME + "/#", CHAPTERS_ID)
        matcher.addURI(CONTENT_AUTHORITY, ObservationApproverContract.TABLE_NAME + "/#", OBSERVATIONAPPROVER_ID)
        matcher.addURI(CONTENT_AUTHORITY, ObservationProcedureContract.TABLE_NAME + "/#", OBSERVATIONPROCEDURE_ID)
        matcher.addURI(CONTENT_AUTHORITY, ObservationsContract.TABLE_NAME + "/#", OBSERVATIONS_ID)
        matcher.addURI(CONTENT_AUTHORITY, ObservationStatusContract.TABLE_NAME + "/#", OBSERVATIONSTATUS_ID)
        matcher.addURI(CONTENT_AUTHORITY, PollsContract.TABLE_NAME + "/#", POLLS_ID)
        matcher.addURI(CONTENT_AUTHORITY, PollUserContract.TABLE_NAME + "/#", POLLUSER_ID)
        matcher.addURI(CONTENT_AUTHORITY, ProceduresContract.TABLE_NAME + "/#", PROCEDURES_ID)
        matcher.addURI(CONTENT_AUTHORITY, RanksContract.TABLE_NAME + "/#", RANKS_ID)
        matcher.addURI(CONTENT_AUTHORITY, SubChaptersContract.TABLE_NAME + "/#", SUBCHAPTERS_ID)
        matcher.addURI(CONTENT_AUTHORITY, UsersContract.TABLE_NAME + "/#", USERS_ID)
        matcher.addURI(CONTENT_AUTHORITY, UserTypesContract.TABLE_NAME + "/#", USERTYPES_ID)


        return matcher
    }

    override fun onCreate(): Boolean {
        Log.d(TAG, "onCreate: starts")
        return true
    }

    override fun getType(uri: Uri): String? {
        return null     // not good practice.
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        Log.d(TAG, "query: called with uri $uri")
        val match = uriMatcher.match(uri)
        Log.d(TAG, "query: uri match is $match")

        val queryBuilder = SQLiteQueryBuilder()

        when (match) {
            ACTIONS -> queryBuilder.tables = ActionsContract.TABLE_NAME

            ACTIONS_ID -> {
                queryBuilder.tables = ActionsContract.TABLE_NAME
                val actionId = ActionsContract.getId(uri)
                queryBuilder.appendWhere("${ActionsContract.Columns.ID} = ")
                queryBuilder.appendWhereEscapeString("$actionId")
            }
            BLOCKS -> queryBuilder.tables = BlocksContract.TABLE_NAME

            BLOCKS_ID -> {
                queryBuilder.tables = BlocksContract.TABLE_NAME
                val blockId = BlocksContract.getId(uri)
                queryBuilder.appendWhere("${BlocksContract.Columns.ID} = ")
                queryBuilder.appendWhereEscapeString("$blockId")
            }
            CHAPTERS -> queryBuilder.tables = ChaptersContract.TABLE_NAME

            CHAPTERS_ID -> {
                queryBuilder.tables = ChaptersContract.TABLE_NAME
                val chapterId = ChaptersContract.getId(uri)
                queryBuilder.appendWhere("${ChaptersContract.Columns.ID} = ")
                queryBuilder.appendWhereEscapeString("$chapterId")
            }

            OBSERVATIONAPPROVER -> queryBuilder.tables = ObservationApproverContract.TABLE_NAME

            OBSERVATIONAPPROVER_ID -> {
                queryBuilder.tables = ObservationApproverContract.TABLE_NAME
                val observationApproverId = ObservationApproverContract.getId(uri)
                queryBuilder.appendWhere("${ObservationApproverContract.Columns.ID} = ")
                queryBuilder.appendWhereEscapeString("$observationApproverId")
            }

            OBSERVATIONPROCEDURE -> queryBuilder.tables = ObservationProcedureContract.TABLE_NAME

            OBSERVATIONPROCEDURE_ID -> {
                queryBuilder.tables = ObservationProcedureContract.TABLE_NAME
                val observationProcedureId = ObservationProcedureContract.getId(uri)
                queryBuilder.appendWhere("${ObservationProcedureContract.Columns.ID} = ")
                queryBuilder.appendWhereEscapeString("$observationProcedureId")
            }

            OBSERVATIONS -> queryBuilder.tables = ObservationsContract.TABLE_NAME

            OBSERVATIONS_ID -> {
                queryBuilder.tables = ObservationsContract.TABLE_NAME
                val observationId = ObservationsContract.getId(uri)
                queryBuilder.appendWhere("${ObservationsContract.Columns.ID} = ")
                queryBuilder.appendWhereEscapeString("$observationId")
            }

            OBSERVATIONSTATUS -> queryBuilder.tables = ObservationStatusContract.TABLE_NAME

            OBSERVATIONSTATUS_ID -> {
                queryBuilder.tables = ObservationStatusContract.TABLE_NAME
                val observationStatusId = ObservationStatusContract.getId(uri)
                queryBuilder.appendWhere("${ObservationStatusContract.Columns.ID} = ")
                queryBuilder.appendWhereEscapeString("$observationStatusId")
            }

            POLLS -> queryBuilder.tables = PollsContract.TABLE_NAME

            POLLS_ID -> {
                queryBuilder.tables = PollsContract.TABLE_NAME
                val pollId = PollsContract.getId(uri)
                queryBuilder.appendWhere("${PollsContract.Columns.ID} = ")
                queryBuilder.appendWhereEscapeString("$pollId")
            }

            POLLUSER -> queryBuilder.tables = PollUserContract.TABLE_NAME

            POLLUSER_ID -> {
                queryBuilder.tables = PollUserContract.TABLE_NAME
                val pollUserId = PollUserContract.getId(uri)
                queryBuilder.appendWhere("${PollUserContract.Columns.ID} = ")
                queryBuilder.appendWhereEscapeString("$pollUserId")
            }

            PROCEDURES -> queryBuilder.tables = ProceduresContract.TABLE_NAME

            PROCEDURES_ID -> {
                queryBuilder.tables = ProceduresContract.TABLE_NAME
                val procedureId = ProceduresContract.getId(uri)
                queryBuilder.appendWhere("${ProceduresContract.Columns.ID} = ")
                queryBuilder.appendWhereEscapeString("$procedureId")
            }

            RANKS -> queryBuilder.tables = RanksContract.TABLE_NAME

            RANKS_ID -> {
                queryBuilder.tables = RanksContract.TABLE_NAME
                val rankId = RanksContract.getId(uri)
                queryBuilder.appendWhere("${RanksContract.Columns.ID} = ")
                queryBuilder.appendWhereEscapeString("$rankId")
            }

            SUBCHAPTERS -> queryBuilder.tables = SubChaptersContract.TABLE_NAME

            SUBCHAPTERS_ID -> {
                queryBuilder.tables = SubChaptersContract.TABLE_NAME
                val subChapterId = SubChaptersContract.getId(uri)
                queryBuilder.appendWhere("${SubChaptersContract.Columns.ID} = ")
                queryBuilder.appendWhereEscapeString("$subChapterId")
            }

            USERS -> queryBuilder.tables = UsersContract.TABLE_NAME

            USERS_ID -> {
                queryBuilder.tables = UsersContract.TABLE_NAME
                val userId = UsersContract.getId(uri)
                queryBuilder.appendWhere("${UsersContract.Columns.ID} = ")
                queryBuilder.appendWhereEscapeString("$userId")
            }

            USERTYPES -> queryBuilder.tables = UserTypesContract.TABLE_NAME

            USERTYPES_ID -> {
                queryBuilder.tables = UserTypesContract.TABLE_NAME
                val userTypeId = UserTypesContract.getId(uri)
                queryBuilder.appendWhere("${UserTypesContract.Columns.ID} = ")
                queryBuilder.appendWhereEscapeString("$userTypeId")
            }

            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }

        val db = AppDatabase.getInstance(context!!).readableDatabase
        val cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder)
        Log.d(TAG, "query: rows in returned cursor = ${cursor.count}") // TODO: remove this line
        return cursor
    }

    override fun insert(uri: Uri, values: ContentValues): Uri {
        Log.d(TAG, "insert: called with uri $uri")
        val match = uriMatcher.match(uri)
        Log.d(TAG, "insert: match is $match")

        val recordId: Long
        val returnUri: Uri

        when(match) {
            OBSERVATIONS -> {
                val db = AppDatabase.getInstance(context).writableDatabase
                recordId = db.insert(ObservationsContract.TABLE_NAME, null, values)
                if(recordId!=-1L) {
                    returnUri = ObservationsContract.buildUriFromId(recordId)
                } else {
                    throw SQLException("Failed to insert, Uri was $uri")
                }
            }

            USERS -> {
                val db = AppDatabase.getInstance(context).writableDatabase
                recordId = db.insert(UsersContract.TABLE_NAME, null, values)
                if(recordId!=-1L) {
                    returnUri = UsersContract.buildUriFromId(recordId)
                } else {
                    throw SQLException("Failed to insert, Uri was $uri")
                }
            }

            OBSERVATIONPROCEDURE -> {
                val db = AppDatabase.getInstance(context).writableDatabase
                recordId = db.insert(ObservationProcedureContract.TABLE_NAME, null, values)
                if(recordId!=-1L) {
                    returnUri = ObservationProcedureContract.buildUriFromId(recordId)
                } else {
                    throw SQLException("Failed to insert, Uri was $uri")
                }
            }

            else -> throw IllegalArgumentException("Unknown uri: $uri")
        }

        Log.d(TAG, "Exiting insert, returning $returnUri")
        return returnUri
    }


    override fun update(uri: Uri, values: ContentValues, selection: String?, selectionArgs: Array<String>?): Int {
        Log.d(TAG, "update: called with uri $uri")
        val match = uriMatcher.match(uri)
        Log.d(TAG, "update: match is $match")

        val count: Int
        var selectionCriteria: String

        when(match) {
            OBSERVATIONS -> {
                val db = AppDatabase.getInstance(context).writableDatabase
                count = db.update(ObservationsContract.TABLE_NAME, values, selection, selectionArgs)
            }

            OBSERVATIONS_ID -> {
                val db = AppDatabase.getInstance(context).writableDatabase
                val id = ObservationsContract.getId(uri)
                selectionCriteria = "${ObservationsContract.Columns.ID} = $id"

                if(selection != null && selection.isNotEmpty()) {
                    selectionCriteria += " AND ($selection)"
                }

                count = db.update(ObservationsContract.TABLE_NAME, values, selectionCriteria, selectionArgs)
            }
            USERS -> {
                val db = AppDatabase.getInstance(context).writableDatabase
                count = db.update(UsersContract.TABLE_NAME, values, selection, selectionArgs)
            }
            USERS_ID -> {
                val db = AppDatabase.getInstance(context).writableDatabase
                val id = UsersContract.getId(uri)
                selectionCriteria = "${UsersContract.Columns.ID} = $id"

                if(selection != null && selection.isNotEmpty()) {
                    selectionCriteria += " AND ($selection)"
                }

                count = db.update(UsersContract.TABLE_NAME, values, selectionCriteria, selectionArgs)
            }

            else -> throw IllegalArgumentException("Unknown uri: $uri")
        }

        Log.d(TAG, "Exiting update, returning $count")
        return count
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        Log.d(TAG, "delete: called with uri $uri")
        val match = uriMatcher.match(uri)
        Log.d(TAG, "delete: match is $match")

        val count: Int
        var selectionCriteria: String

        when(match) {
            OBSERVATIONS -> {
                val db = AppDatabase.getInstance(context).writableDatabase
                count = db.delete(ObservationsContract.TABLE_NAME, selection, selectionArgs)
            }

            OBSERVATIONS_ID -> {
                val db = AppDatabase.getInstance(context).writableDatabase
                val id = ObservationsContract.getId(uri)
                selectionCriteria = "${ObservationsContract.Columns.ID} = $id"

                if(selection != null && selection.isNotEmpty()) {
                    selectionCriteria += " AND ($selection)"
                }

                count = db.delete(ObservationsContract.TABLE_NAME, selectionCriteria, selectionArgs)
            }

            USERS -> {
                val db = AppDatabase.getInstance(context).writableDatabase
                count = db.delete(UsersContract.TABLE_NAME, selection, selectionArgs)
            }
            USERS_ID -> {
                val db = AppDatabase.getInstance(context).writableDatabase
                val id = UsersContract.getId(uri)
                selectionCriteria = "${UsersContract.Columns.ID} = $id"

                if(selection != null && selection.isNotEmpty()) {
                    selectionCriteria += " AND ($selection)"
                }

                count = db.delete(UsersContract.TABLE_NAME, selectionCriteria, selectionArgs)
            }

            else -> throw IllegalArgumentException("Unknown uri: $uri")
        }

        Log.d(TAG, "Exiting update, returning $count")
        return count
    }


}