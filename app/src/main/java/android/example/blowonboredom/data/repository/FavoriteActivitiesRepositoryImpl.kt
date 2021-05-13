package android.example.blowonboredom.data.repository

import android.database.Observable
import android.example.blowonboredom.data.local.FavoriteActivitiesDAO
import android.example.blowonboredom.data.model.RandomActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class FavoriteActivitiesRepositoryImpl @Inject constructor(
    private val localDataSource : FavoriteActivitiesDAO)
    : FavoriteActivitiesRepository<RandomActivity, String> {

    override fun getAllFavoriteActivities(): Single<List<RandomActivity>> {
        return localDataSource
            .loadAllActivities()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun addToFavoriteActivities(item: RandomActivity): Completable {
        return localDataSource
            .addActivity(item)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun deleteFromFavoriteActivities(id: String): Completable {
        return localDataSource
            .deleteActivity(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}