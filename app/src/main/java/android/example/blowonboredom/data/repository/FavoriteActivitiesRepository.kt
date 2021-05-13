package android.example.blowonboredom.data.repository

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface FavoriteActivitiesRepository<T, A> {
    fun getAllFavoriteActivities() : Single<List<T>>
    fun deleteFromFavoriteActivities(id : A) : Completable
    fun addToFavoriteActivities(item : T) : Completable

}