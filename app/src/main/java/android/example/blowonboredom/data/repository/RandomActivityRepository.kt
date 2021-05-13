package android.example.blowonboredom.data.repository

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface RandomActivityRepository <T> {

    fun getRandomActivity() : Single<T>

}