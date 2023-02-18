package com.example.testapp.network

import com.example.testapp.network.services.AcronymLookupService
import com.example.testapp.repository.AcronymRepository
import com.example.testapp.repository.AcronymRepositoryImpl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


/**
 * Networking module that builds retrofit instance and creates APILookupService to the clients.
 */
@Module
class NetworkModule {

    @Provides
    fun providesApiService(): AcronymLookupService {
        val retrofit = buildRetrofitInstance()
        return retrofit.create(AcronymLookupService::class.java)
    }


    @Singleton
    @Provides
    fun provideAcronymRepository(service: AcronymLookupService): AcronymRepository = AcronymRepositoryImpl(service)
    private fun buildRetrofitInstance(): Retrofit{

        /**
         * This is a not a good practice to skip cert validation and trust all.
         * The given api "http://www.nactem.ac.uk" is not using https and is not allowed in android
         * hence I changed it to use https, but it seems it is using a dev cert and
         * I had to do this below to by pass it.
         */
        val trustAllCerts = arrayOf<TrustManager>(
            object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                }

                override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            }
        )

        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())

        val sslSocketFactory = sslContext.socketFactory

        val client = OkHttpClient.Builder()
            .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            .hostnameVerifier { _, _ -> true }
            .build()

        val retrofit = Retrofit.Builder().baseUrl("https://www.nactem.ac.uk/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit
    }
}