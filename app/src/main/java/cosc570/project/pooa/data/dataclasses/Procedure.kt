package cosc570.project.pooa.data.dataclasses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Procedure(val name: String,
                val block: String,
                val popularity: Long,
                val location: Long) : Parcelable {
    var id: Long = 0
}