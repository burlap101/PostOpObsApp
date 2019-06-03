package cosc570.project.pooa

import android.content.ContentValues
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import cosc570.project.pooa.data.interfaces.ObservationsContract
import kotlinx.android.synthetic.main.activity_edit_observation.*

class EditObservation : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_observation)

        val intentBundle: Bundle? = intent.extras
        if (intentBundle != null) {
            eoa_observation_et.setText(intentBundle.getString("OBSERVATION_NAME"))
            eoa_observation_id.text = intentBundle.getString("OBSERVATION_ID")
        }
    }

    fun onSubmitClicked(view: View) {
        val selectionArgsObservation= mutableListOf<String>()
        selectionArgsObservation.add(eoa_observation_id.text.toString())

        val cv = ContentValues()


        when (eoa_action_spinner.selectedItem.toString()) {
            "Append Special Note" -> {
                val cursor = contentResolver.query(
                    ObservationsContract.CONTENT_URI,
                    null,
                    "_id = ?",
                    selectionArgsObservation.toTypedArray(),
                    null
                )

                var oldSn = ""

                if(cursor.moveToNext() && cursor != null) {
                    oldSn = cursor.getString(cursor.getColumnIndex(ObservationsContract.Columns.SPECIAL_NOTES))
                } else {
                    Snackbar.make(view, "No observations found", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                }

                cv.put("special_notes", oldSn + eoa_text_et.text.toString())
                contentResolver.update(
                    ObservationsContract.CONTENT_URI,
                    cv,
                    "_id = ?",
                    selectionArgsObservation.toTypedArray()
                )
            }
            "Replace Special Note" -> {
                cv.put("special_notes", eoa_text_et.text.toString())
                contentResolver.update(
                    ObservationsContract.CONTENT_URI,
                    cv,
                    "_id = ?",
                    selectionArgsObservation.toTypedArray()
                )
            }
            "Change Name" -> {
                cv.put("name", eoa_text_et.text.toString())
                contentResolver.update(
                    ObservationsContract.CONTENT_URI,
                    cv,
                    "_id = ?",
                    selectionArgsObservation.toTypedArray()
                )
            }
            "Delete" -> {
                contentResolver.delete(
                    ObservationsContract.CONTENT_URI,
                    "_id = ?",
                    selectionArgsObservation.toTypedArray()
                )

            }
            else -> {
                Snackbar.make(view, "No action type selected", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }

        }
        finish()

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
