package android.example.blowonboredom.data.repository

import android.example.blowonboredom.data.local.FavoriteActivitiesDAO
import android.example.blowonboredom.data.model.RandomActivity
import android.example.blowonboredom.data.remote.RandomActivityService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject


class RandomActivityRepositoryImpl @Inject constructor(
    private val remoteData: RandomActivityService
) : RandomActivityRepository<RandomActivity> {

    override fun getRandomActivity(): Single<RandomActivity> {
        return remoteData
            .getRandomActivityResult()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }




}