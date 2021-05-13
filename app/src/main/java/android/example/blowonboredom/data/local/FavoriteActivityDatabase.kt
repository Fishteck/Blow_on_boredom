package android.example.blowonboredom.data.local

import android.example.blowonboredom.data.model.RandomActivity
import androidx.room.Database
import androidx.room.RoomDatabase

@Database( entities = [RandomActivity::class], version = 1, exportSchema = true)
abstract class FavoriteActivityDatabase : RoomDatabase(){
    abstract fun getFavoriteActivitiesDAO() : FavoriteActivitiesDAO
}