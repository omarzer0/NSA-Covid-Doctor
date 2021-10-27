package az.zero.nsacoviddoctor.di.network

import androidx.databinding.library.BuildConfig
import az.zero.nsacoviddoctor.common.CORONA_BASE_URL
import az.zero.nsacoviddoctor.common.timeOut
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
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    @Singleton
    fun providesLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)


    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient().newBuilder().apply {
            callTimeout(timeOut, TimeUnit.SECONDS)
            connectTimeout(timeOut, TimeUnit.SECONDS)
            readTimeout(timeOut, TimeUnit.SECONDS)
            writeTimeout(timeOut, TimeUnit.SECONDS)
            if (BuildConfig.DEBUG) addInterceptor(loggingInterceptor)
        }.build()

    @Provides
    @Singleton
    @Named("provideRetrofitForCovidInfo")
    fun provideRetrofitForCovidInfo(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(CORONA_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

//    @Provides
//    @Singleton
//    @Named("provideRetrofitPostsAndAIModel")
//    fun provideRetrofitPostsAndAIModel(
//        okHttpClient: OkHttpClient
//    ): Retrofit = Retrofit.Builder()
//        .baseUrl(CORONA_BASE_URL)
//        .client(okHttpClient)
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()

    @Provides
    @Singleton
    fun provideCovidApi(@Named("provideRetrofitForCovidInfo") retrofit: Retrofit): CovidApi =
        retrofit.create(CovidApi::class.java)


    @Provides
    @Singleton
    fun provideCovidRepository(api: CovidApi): CovidRepository = CovidRepositoryImpl(api)


}