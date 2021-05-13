package android.example.blowonboredom.di

import android.example.blowonboredom.data.local.FavoriteActivitiesDAO
import android.example.blowonboredom.data.model.RandomActivity
import android.example.blowonboredom.data.remote.RandomActivityService
import android.example.blowonboredom.data.repository.FavoriteActivitiesRepository
import android.example.blowonboredom.data.repository.FavoriteActivitiesRepositoryImpl
import android.example.blowonboredom.data.repository.RandomActivityRepository
import android.example.blowonboredom.data.repository.RandomActivityRepositoryImpl
import android.example.blowonboredom.utils.Constants
import android.example.blowonboredom.utils.Constants.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideRandomActivityRepository(
        remoteData: RandomActivityService
    ) : RandomActivityRepository<RandomActivity>
    = RandomActivityRepositoryImpl(remoteData)

    @Provides
    fun provideFavoriteActivityRepository(localDataSource : FavoriteActivitiesDAO)
    : FavoriteActivitiesRepository<RandomActivity, String> =
        FavoriteActivitiesRepositoryImpl(localDataSource)
}