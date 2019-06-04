package cosc570.project.pooa

import android.content.ContentValues
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import cosc570.project.pooa.data.databasehelper.AppDatabase
import cosc570.project.pooa.data.interfaces.ObservationProcedureContract
import cosc570.project.pooa.data.interfaces.ObservationsContract
import kotlinx.android.synthetic.main.activity_add_observation.*
import kotlinx.android.synthetic.main.activity_edit_observation.*

private const val TAG = "AddObservation"

class AddObservation : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_observation)

        val intentBundle: Bundle? = intent.extras
        if (intentBundle != null) {
            ao_procedure_tv.text = intentBundle.getString("PROCEDURE_NAME")
            ao_procedure_id.text = intentBundle.getString("PROCEDURE_ID")
        }
    }

    fun onSubmitClicked(view: View) {

        val appDatabase = AppDatabase.getInstance(this)
        val db = appDatabase.writableDatabase

        val cv = ContentValues()
        val selectionArgsObservation= mutableListOf<String>()
        selectionArgsObservation.add(ao_procedure_id.text.toString())
        if(!ao_obsevation_et.text.isEmpty()) {
            cv.put(ObservationsContract.Columns.NAME, ao_obsevation_et.text.toString())

        }
        if(!ao_specialnotes_et.text.isEmpty()) {
            cv.put(ObservationsContract.Columns.SPECIAL_NOTES, ao_specialnotes_et.text.toString())
        }
        if(!ao_url_et.text.isEmpty()) {
            cv.put(ObservationsContract.Columns.URL, ao_url_et.text.toString())
        }

//        val newUri = contentResolver.insert(
//            ObservationsContract.CONTENT_URI,
//            cv
//        )
        val newId = db.insert("Observations", null, cv) //TODO replace with contentprovider

        val opCv = ContentValues()
        opCv.put(ObservationProcedureContract.Columns.OBSERVATION, newId.toString())
        opCv.put(ObservationProcedureContract.Columns.PROCEDURE, ao_procedure_id.text.toString())
        val newOpId = db.insert("ObservationProcedure", null, opCv)

        Log.d(TAG, "onSubmitClicked: new observation id $newId")
        Log.d(TAG, "onSubmitClicked: new observationprocedure id $newOpId")


        finish()
    }
}
