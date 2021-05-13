package android.example.blowonboredom.data.local

import android.database.Observable
import android.example.blowonboredom.data.model.RandomActivity
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

@Dao
interface FavoriteActivitiesDAO {

    @Query("SELECT * FROM ${RandomActivity.FAVORITE_ACTIVITIES_TABLE}")
    fun loadAllActivities() : Single<List<RandomActivity>>

    @Query("SELECT * FROM ${RandomActivity.FAVORITE_ACTIVITIES_TABLE} WHERE `key` like :id")
     fun loadDetailActivity(id : String) : Maybe<RandomActivity>

    @Query("DELETE FROM ${RandomActivity.FAVORITE_ACTIVITIES_TABLE} WHERE `key` like :id")
    fun deleteActivity(id : String) : Completable

    @Insert(entity = RandomActivity::class, onConflict = OnConflictStrategy.REPLACE)
    fun addActivity(randomActivity: RandomActivity) : Completable

}