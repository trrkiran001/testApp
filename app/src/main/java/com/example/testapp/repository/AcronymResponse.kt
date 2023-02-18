package com.example.testapp.repository

import com.example.testapp.data.Definition

sealed class AcronymResult {
    object Error: AcronymResult()
    data class DefinitionResult(val definitions: List<Definition>): AcronymResult()
}