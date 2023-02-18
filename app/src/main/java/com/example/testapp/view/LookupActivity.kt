package com.example.testapp.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.R
import com.example.testapp.databinding.RecyclerViewBinding
import com.example.testapp.viewmodel.LookupActivityViewModel
import kotlinx.coroutines.launch
import androidx.activity.viewModels
import com.example.testapp.TestApp
import javax.inject.Inject

class LookupActivity : AppCompatActivity(), ClickListener {

    private lateinit var binding: RecyclerViewBinding
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: LookupActivityViewModel by viewModels { viewModelFactory }

    @Inject
    lateinit var adapter: DefinitionAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        TestApp.appComponent.inject(this)
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.recycler_view)
        binding.listener = this
        binding.lifecycleOwner = this
        adapter = DefinitionAdapter()

        binding.recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@LookupActivity.adapter
        }
        //viewModel.fetchData(binding.inputText.toString())

        lifecycleScope.launch {
            viewModel.items.collect() { items ->
                Log.i("Kiran ", "printing from activity ${items.count()}")
                adapter.setItems(items)
            }
        }
    }

    override fun fetchData() {
        viewModel.fetchData(binding.inputText.toString())
    }
}