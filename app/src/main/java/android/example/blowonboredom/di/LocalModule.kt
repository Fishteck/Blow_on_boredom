package android.example.blowonboredom.di

import android.content.Context
import android.example.blowonboredom.data.local.FavoriteActivitiesDAO
import android.example.blowonboredom.data.local.FavoriteActivityDatabase
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) : FavoriteActivityDatabase =
        Room.databaseBuilder(
            context,
            FavoriteActivityDatabase::class.java,
            "favorite_activities_database"
        ).build()

    @Provides
    @Singleton
    fun provideLocalDataSource(database: FavoriteActivityDatabase) : FavoriteActivitiesDAO =
        database.getFavoriteActivitiesDAO()

}