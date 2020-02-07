package capatan.kurt.nbateamviewer.datasource

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Team (

    @Json(name = "id") val id: Int,
    @Json(name = "full_name") val name: String,
    @Json(name = "wins") val wins: Int,
    @Json(name = "losses") val losses: Int,
    @Json(name = "players") val players: List<Player>

): Parcelable
