package android.example.blowonboredom.data.remote

import android.example.blowonboredom.data.model.RandomActivity
import android.example.blowonboredom.utils.Constants
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET


interface RandomActivityService {
    @GET(Constants.API_ENDPOINT_RANDOM_ACTIVITY)
    fun getRandomActivityResult() : Single<RandomActivity>

}