package az.zero.nsacoviddoctor.di.network

import android.app.Application
import androidx.databinding.library.BuildConfig
import az.zero.nsacoviddoctor.common.BASE_URL
import az.zero.nsacoviddoctor.data.data_source.network.CovidApi
import az.zero.nsacoviddoctor.data.repository.CovidRepositoryImpl
import az.zero.nsacoviddoctor.domain.repository.CovidRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    @Singleton
    fun providesLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)


    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient().newBuilder().apply {
            callTimeout(140, TimeUnit.SECONDS)
            connectTimeout(140, TimeUnit.SECONDS)
            readTimeout(140, TimeUnit.SECONDS)
            writeTimeout(140, TimeUnit.SECONDS)
            if (BuildConfig.DEBUG) addInterceptor(loggingInterceptor)
        }.build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): CovidApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CovidApi::class.java)


    @Provides
    @Singleton
    fun provideCovidRepository(api: CovidApi): CovidRepository = CovidRepositoryImpl(api)

}