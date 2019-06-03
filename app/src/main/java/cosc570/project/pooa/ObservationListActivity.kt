package cosc570.project.pooa

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import cosc570.project.pooa.adapters.ObservationRecyclerViewAdapter
import cosc570.project.pooa.data.databasehelper.AppDatabase
import cosc570.project.pooa.data.interfaces.ObservationProcedureContract
import cosc570.project.pooa.data.interfaces.ObservationsContract
import kotlinx.android.synthetic.main.activity_observation_list.*

private const val TAG = "ObservationListActivity"

class ObservationListActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate: starts")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_observation_list)
//        setSupportActionBar(findViewById(R.id.ola_toolbar)) TODO: implement custom toolbar


        val intentBundle: Bundle? = intent.extras
        if(intentBundle != null) {
            ola_procedure_id.text = intentBundle.getString("PROCEDURE_ID")
            ola_procedure_name.text = intentBundle.getString("PROCEDURE_NAME")
        }

        val selectionArgsOp = arrayOf(ola_procedure_id.text.toString())
        Log.d(TAG, "procedure id: ${ola_procedure_id.text}")
        val opCursor = contentResolver.query(
            ObservationProcedureContract.CONTENT_URI,
            null,
            "procedure = ?",
            selectionArgsOp,
            null
        )

        val selectionArgsObservation= mutableListOf<String>()
        if(opCursor != null) {
            while (opCursor.moveToNext()) {
                val ob = opCursor.getString(opCursor.getColumnIndex(ObservationProcedureContract.Columns.OBSERVATION))
                Log.d(TAG, "ob value: $ob")
                selectionArgsObservation.add(ob)
            }

            opCursor.close()
        }

        val qms = " ?,".repeat(selectionArgsObservation.count()).trimEnd(',')
        Log.d(TAG, "qms: $qms")


        val cursor = contentResolver.query(
            ObservationsContract.CONTENT_URI,
            null,
            "_id IN ($qms)",
            selectionArgsObservation.toTypedArray(),
            null
        )

        viewManager = LinearLayoutManager(this)
        viewAdapter = ObservationRecyclerViewAdapter(cursor)

        recyclerView = findViewById(R.id.ola_recycler_view)

        recyclerView.layoutManager = viewManager
        recyclerView.adapter = viewAdapter
    }

    fun onAddClicked(view: View) {
        val addIntent = Intent(view.context, AddObservation::class.java)

        addIntent.putExtra("PROCEDURE_ID", ola_procedure_id.text)
        addIntent.putExtra("PROCEDURE_NAME", ola_procedure_name.text)
        view.context.startActivity(addIntent)
    }

    override fun onResume() {
        Log.d(TAG, "onResume: starts")
        super.onResume()

    }
}
