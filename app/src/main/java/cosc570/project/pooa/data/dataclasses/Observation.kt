package cosc570.project.pooa.data.dataclasses

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
class Observation(val name: String,
                  val specialNotes: String?,
                  val popularity: Long,
                  val status: Long,
                  val createdBy: Long,
                  val previousRevision: Long?,
                  val url: String?) : Parcelable {
    var id: Long = 0
}