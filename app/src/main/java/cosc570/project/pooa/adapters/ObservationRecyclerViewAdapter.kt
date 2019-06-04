package cosc570.project.pooa.adapters


import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cosc570.project.pooa.EditObservation
import cosc570.project.pooa.data.dataclasses.Observation
import cosc570.project.pooa.data.interfaces.ObservationsContract
import kotlinx.android.synthetic.main.observation_list_item.view.*


/**
 * [RecyclerView.Adapter] that can display a [Observation] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 *
 */

private const val TAG = "ObservationRVAdapter"

class ObservationRecyclerViewAdapter(
    private var cursor: Cursor?
) : RecyclerView.Adapter<ObservationRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d(TAG, "onCreateViewHolder: new view requested")
        val view = LayoutInflater.from(parent.context)
            .inflate(cosc570.project.pooa.R.layout.observation_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: started")
        val cursor = cursor     //avoid problems with smart cast

        if(cursor == null || cursor.count == 0) {
            Log.d(TAG, "onBindViewHolder: No records")
        } else {
            if(!cursor.moveToPosition(position)) {
                throw IllegalStateException("Couldn't move cursor to position $position")
            }

            val observation = Observation(
                cursor.getString(cursor.getColumnIndex(ObservationsContract.Columns.NAME)),
                cursor.getString(cursor.getColumnIndex(ObservationsContract.Columns.SPECIAL_NOTES)),
                cursor.getLong(cursor.getColumnIndex(ObservationsContract.Columns.POPULARITY)),
                cursor.getLong(cursor.getColumnIndex(ObservationsContract.Columns.STATUS)),
                cursor.getLong(cursor.getColumnIndex(ObservationsContract.Columns.CREATED_BY)),
                cursor.getLong(cursor.getColumnIndex(ObservationsContract.Columns.PREVIOUS_REVISION)),
                cursor.getString(cursor.getColumnIndex(ObservationsContract.Columns.URL))
            )

            observation.id = cursor.getLong(cursor.getColumnIndex(ObservationsContract.Columns.ID))

            holder.mIdView.text = observation.id.toString()
            holder.mContentView.text = observation.name
            holder.mSpecialNotesView.text = observation.specialNotes
            holder.mUrlView.text = observation.url
        }


    }

    override fun getItemCount(): Int {
        val cursor = cursor
        return if(cursor == null || cursor.count == 0) {
            0
        } else {
            cursor.count
        }
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView), View.OnClickListener {
        val mIdView: TextView = mView.item_number
        val mContentView: TextView = mView.content
        val mSpecialNotesView: TextView = mView.special_notes
        val mUrlView: TextView = mView.oli_url

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }

        init {
            mView.setOnClickListener(this)
            mView.oli_edit_btn.setOnClickListener { v: View ->
                val context = v.context
                val editIntent = Intent(context, EditObservation::class.java)

                editIntent.putExtra("OBSERVATION_ID", mIdView.text)
                editIntent.putExtra("OBSERVATION_NAME", mContentView.text)
                context.startActivity(editIntent)
            }
        }

        override fun onClick(v: View) {
            val url = mUrlView.text
            Log.d(TAG, "onClick: going to $url")
            if(url.isNotEmpty()) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url.toString()))
                v.context.startActivity(browserIntent)
            } else {
                Snackbar.make(v, "No URL specified for this observation", Snackbar.LENGTH_LONG).show()
            }
        }
    }
}
