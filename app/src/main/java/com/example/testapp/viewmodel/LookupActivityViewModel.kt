package com.example.testapp.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.repository.AcronymRepository
import com.example.testapp.repository.AcronymResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LookupActivityViewModel @Inject constructor(
        private val repository: AcronymRepository
    ) : ViewModel() {

    private val _items = MutableStateFlow(emptyList<String>())
    val items: StateFlow<List<String>>
        get() = _items

    @Suppress("UNCHECKED_CAST")
    fun fetchData(text: String = "HMM") {
        Log.i("Kiran", "calling service in viewmodel")
        viewModelScope.launch {

            repository.getAbbreviation(text).collect { acronymResult ->
                if (acronymResult is AcronymResult.DefinitionResult) {
                    val data = acronymResult.definitions
                    val results = data[0].lfs.let {
                        it.map { longFormObject -> longFormObject.lf }
                            .plus(it.flatMap { longformObject -> longformObject.variations }
                                .map { variation -> variation.lf })
                            .distinct()
                    }
                    _items.emit(results)
                } else {
                    Log.e("Kiran", "in viewmodel api failed sending empty list")
                    //Error, you may want to retry?
                    _items.emit(emptyList())
                }
            }
        }
    }

    fun fetchClicked(view: View) {
        fetchData()
    }

}