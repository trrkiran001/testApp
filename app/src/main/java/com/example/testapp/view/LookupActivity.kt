package com.example.testapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.R
import com.example.testapp.databinding.RecyclerViewBinding
import com.example.testapp.viewmodel.LookupActivityViewModel
import kotlinx.coroutines.launch

class LookupActivity : AppCompatActivity() {

    private lateinit var binding: RecyclerViewBinding
    private lateinit var viewModel: LookupActivityViewModel
    private lateinit var adapter: DefinitionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.recycler_view)
        viewModel = ViewModelProvider(this).get(LookupActivityViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        adapter = DefinitionAdapter()
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(context)
        }

        lifecycleScope.launch {
            viewModel.items.collect { items ->
                adapter.setItems(items)
            }
        }

//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.container, MainFragment.newInstance())
//                .commit()
//        }
    }
}