package android.example.blowonboredom.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = RandomActivity.FAVORITE_ACTIVITIES_TABLE)
data class RandomActivity(
    val accessibility: Double?,
    val activity: String?,
    @PrimaryKey
    val key: String,
    val link: String?,
    val participants: Int?,
    val price: Double?,
    val type: String?,
    var isFavorite: Boolean?
) {

    companion object {
        const val FAVORITE_ACTIVITIES_TABLE = "fav_activities_table"
    }
}