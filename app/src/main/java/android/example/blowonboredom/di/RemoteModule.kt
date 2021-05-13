package android.example.blowonboredom.di

import android.example.blowonboredom.data.remote.RandomActivityService
import android.example.blowonboredom.utils.Constants
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
object RemoteModule {
    @Provides
    fun provideInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .build()

    @Provides
    fun provideGSON(): Gson =
        GsonBuilder()
            .create()

    @Provides
    fun provideRxJavaCallAdapterFactory(): RxJava3CallAdapterFactory =
        RxJava3CallAdapterFactory
            .create()

    @Provides
    @Singleton
    fun provideRetrofit(
        gson: Gson,
        okHttpClient: OkHttpClient,
        rxJava3CallAdapterFactory: RxJava3CallAdapterFactory
    ): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(
                GsonConverterFactory
                    .create(gson)
            ).addCallAdapterFactory(rxJava3CallAdapterFactory)
            .build()

    @Provides
    fun provideRandomActivityService(retrofit: Retrofit): RandomActivityService =
        retrofit.create(RandomActivityService::class.java)
}