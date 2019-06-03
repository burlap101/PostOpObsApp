package cosc570.project.pooa

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import cosc570.project.pooa.adapters.ProcedureRecyclerViewAdapter
import cosc570.project.pooa.data.interfaces.ProceduresContract
import cosc570.project.pooa.data.interfaces.UsersContract

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var topRecyclerView: RecyclerView
    private lateinit var topViewAdapter: RecyclerView.Adapter<*>
    private lateinit var topViewManager: RecyclerView.LayoutManager
    private lateinit var midRecyclerView: RecyclerView
    private lateinit var midViewAdapter: RecyclerView.Adapter<*>
    private lateinit var midViewManager: RecyclerView.LayoutManager
    private lateinit var bottomRecyclerView: RecyclerView
    private lateinit var bottomViewAdapter: RecyclerView.Adapter<*>
    private lateinit var bottomViewManager: RecyclerView.LayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val selectionArgs = arrayOf("1")

        val topCursor = contentResolver.query(
            ProceduresContract.CONTENT_URI,
            null,
            "location = ?",
            selectionArgs,
            null
        )

        selectionArgs[0] = "2"

        val midCursor = contentResolver.query(
            ProceduresContract.CONTENT_URI,
            null,
            "location = ?",
            selectionArgs,
            null
        )

        selectionArgs[0] = "3"

        val bottomCursor = contentResolver.query(
            ProceduresContract.CONTENT_URI,
            null,
            "location = ?",
            selectionArgs,
            null
        )

        topViewManager = LinearLayoutManager(this)
        topViewAdapter = ProcedureRecyclerViewAdapter(topCursor)

        this.topRecyclerView = findViewById(R.id.main_topobs_rv)

        this.topRecyclerView.layoutManager = topViewManager
        this.topRecyclerView.adapter = topViewAdapter

        midViewManager = LinearLayoutManager(this)
        midViewAdapter = ProcedureRecyclerViewAdapter(midCursor)

        this.midRecyclerView = findViewById(R.id.main_midobs_rv)

        this.midRecyclerView.layoutManager = midViewManager
        this.midRecyclerView.adapter = midViewAdapter

        this.bottomViewManager = LinearLayoutManager(this)
        this.bottomViewAdapter = ProcedureRecyclerViewAdapter(bottomCursor)

        this.bottomRecyclerView = findViewById(R.id.main_bottomobs_rv)

        this.bottomRecyclerView.layoutManager = this.bottomViewManager
        this.bottomRecyclerView.adapter = this.bottomViewAdapter
    }

    private fun testUpdate() {
        val values = ContentValues().apply {
            put(UsersContract.Columns.USERNAME, "FatFace78")
            put(UsersContract.Columns.USER_TYPE, 3)
            put(UsersContract.Columns.FIRST_NAME, "Jennifer")
            put(UsersContract.Columns.LAST_NAME, "Lopez")
            put(UsersContract.Columns.EMAIL, "trollytroll@fake.com")
            put(UsersContract.Columns.PASSWORD, "1234password")
        }

        val userUri = UsersContract.buildUriFromId(1)
        val rowsAffected = contentResolver.update(userUri, values, null, null)
        Log.d(TAG, "Number of rows updated is $rowsAffected")
    }
}
