package cosc570.project.pooa.adapters

import android.content.Intent
import android.database.Cursor
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cosc570.project.pooa.ObservationListActivity
import cosc570.project.pooa.R
import cosc570.project.pooa.data.dataclasses.Procedure
import cosc570.project.pooa.data.interfaces.ObservationsContract
import cosc570.project.pooa.data.interfaces.ProceduresContract
import kotlinx.android.synthetic.main.procedure_list_item.view.*

/**
 * [RecyclerView.Adapter] that can display a [Procedure] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 *
 */

private const val TAG = "ProcedureRVAdapter"

class ProcedureRecyclerViewAdapter(
    private var cursor: Cursor?
) : RecyclerView.Adapter<ProcedureRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d(TAG, "onCreateViewHolder: new view requested")
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.procedure_list_item, parent, false)
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

            val procedure = Procedure(
                cursor.getString(cursor.getColumnIndex(ProceduresContract.Columns.NAME)),
                cursor.getString(cursor.getColumnIndex(ProceduresContract.Columns.BLOCK)),
                cursor.getLong(cursor.getColumnIndex(ProceduresContract.Columns.POPULARITY)),
                cursor.getLong(cursor.getColumnIndex(ProceduresContract.Columns.LOCATION))
            )

            procedure.id = cursor.getLong(cursor.getColumnIndex(ObservationsContract.Columns.ID))

            holder.mIdView.text = procedure.id.toString()
            holder.mContentView.text = procedure.name

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
        val mIdView: TextView = mView.pli_id
        val mContentView: TextView = mView.pli_name

        init {
            mView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            Log.d(TAG, "Procedure clicked: " + mContentView.text)
            val context = v.context
            val obsIntent = Intent(context, ObservationListActivity::class.java)
            obsIntent.putExtra("PROCEDURE_ID", mIdView.text)
            obsIntent.putExtra("PROCEDURE_NAME", mContentView.text)
            context.startActivity(obsIntent)

        }

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}