package com.example.testapp.network.services

import com.example.testapp.data.Definition
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * ApiService for the given api to query acronyms
 */
interface AcronymLookupService {
    @GET("software/acromine/dictionary.py")
    suspend fun getFullTextForAcronym(@Query("sf") sf: String): Response<List<Definition>>
}