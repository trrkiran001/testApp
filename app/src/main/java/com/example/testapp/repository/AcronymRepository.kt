package com.example.testapp.repository

import kotlinx.coroutines.flow.Flow

/**
 * Repository for Acronym api service.
 */
interface AcronymRepository {
    suspend fun getAbbreviation(s: String): Flow<AcronymResult>
}