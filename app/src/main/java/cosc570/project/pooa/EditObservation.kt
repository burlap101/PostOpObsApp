package cosc570.project.pooa

import android.content.ContentValues
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import cosc570.project.pooa.data.interfaces.ObservationsContract
import kotlinx.android.synthetic.main.activity_edit_observation.*

private const val TAG = "EditObservation"

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
        val cv = ContentValues()
        val selectionArgsObservation= mutableListOf<String>()
        selectionArgsObservation.add(eoa_observation_id.text.toString())

        when (eoa_action_spinner.selectedItem.toString()) {
            "Append Special Note" -> {
                val cursor = contentResolver.query(
                    ObservationsContract.CONTENT_URI,
                    null,
                    "_id = ?",
                    selectionArgsObservation.toTypedArray(),
                    null
                )

                var oldSn: String? = null

                if (cursor.moveToNext() && cursor != null) {
                    oldSn = cursor.getString(cursor.getColumnIndex(ObservationsContract.Columns.SPECIAL_NOTES))
                } else {
                    oldSn = ""
                }

                Log.d(TAG, "onSubmit: old special notes: $oldSn")

                cv.put("special_notes", oldSn + eoa_text_et.text.toString())
                contentResolver.update(
                    ObservationsContract.CONTENT_URI,
                    cv,
                    "_id = ?",
                    selectionArgsObservation.toTypedArray()
                )
                finish()
            }
            "Replace Special Note" -> {
                cv.put("special_notes", eoa_text_et.text.toString())
                contentResolver.update(
                    ObservationsContract.CONTENT_URI,
                    cv,
                    "_id = ?",
                    selectionArgsObservation.toTypedArray()
                )
                finish()
            }
            "Change Observation Name" -> {
                cv.put("name", eoa_text_et.text.toString())
                contentResolver.update(
                    ObservationsContract.CONTENT_URI,
                    cv,
                    "_id = ?",
                    selectionArgsObservation.toTypedArray()
                )
                finish()
            }
            "Delete" -> {
                val sb = Snackbar.make(view, "Are you sure you want to delete this observation?", Snackbar.LENGTH_INDEFINITE)
                sb.setAction("Yes", SnackbarYesListener())
                sb.setAction("No", null)
                sb.show()
                contentResolver.delete(
                    ObservationsContract.CONTENT_URI,
                    "_id = ?",
                    selectionArgsObservation.toTypedArray()
                )
                finish()
            }
            "Change URL" -> {
                cv.put("url", eoa_text_et.text.toString())
                contentResolver.update(
                    ObservationsContract.CONTENT_URI,
                    cv,
                    "_id = ?",
                    selectionArgsObservation.toTypedArray()
                )
                finish()
            }
            else -> {
                Snackbar.make(view, "No action type selected", Snackbar.LENGTH_LONG)
                    .show()
            }

        }

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private class SnackbarYesListener() : View.OnClickListener {
        override fun onClick(v: View?) {
            // TODO: Get this working to only delete when yes is pressed
        }
    }
}
