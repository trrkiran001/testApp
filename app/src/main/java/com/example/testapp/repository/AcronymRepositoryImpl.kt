package com.example.testapp.repository

import com.example.testapp.network.services.AcronymLookupService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Implementation class of [AcronymRepository].
 */
class AcronymRepositoryImpl @Inject constructor(private val service: AcronymLookupService): AcronymRepository {

    override suspend fun getAbbreviation(s: String): Flow<AcronymResult> = flow {
        try {
            val response = service.getFullTextForAcronym("HMM")
            if (response.isSuccessful) {
                emit(AcronymResult.DefinitionResult(response.body() ?: emptyList()))
            } else {
                //unsuccessful api request
                emit(AcronymResult.Error)
            }
        } catch (e: Exception) {
            //exception while running api call
            emit(AcronymResult.Error)
        }

    }

}