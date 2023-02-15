package com.example.testapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.data.Definition
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class LookupActivityViewModel : ViewModel() {

    private val _items = MutableStateFlow(emptyList<String>())
    val items: StateFlow<List<String>>
        get() = _items

    val retrofit = Retrofit.Builder().baseUrl("http://www.nactem.ac.uk/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(ApiLookupService::class.java)

    @Suppress("UNCHECKED_CAST")
    fun fetchData() {
        viewModelScope.launch {
            try {
                val data = service.getFullTextForAcronym("HMM")
                //TODO - Need to handle 0 results for non existing sf key
                val results = data[0].lfs.map { longFormObject -> longFormObject.lf }
                    .plus(data[0].lfs.map { longformObject -> longformObject.variations.map{ variation -> variation.lf } })
                    .distinct() as List<String>
                _items.emit(results)
            } catch (e: Exception) {
                //Network error
            }
        }
    }

    interface ApiLookupService {
        @GET("software/acromine/dictionary.py")
        suspend fun getFullTextForAcronym(@Query("sf") sf: String): List<Definition>
    }

}