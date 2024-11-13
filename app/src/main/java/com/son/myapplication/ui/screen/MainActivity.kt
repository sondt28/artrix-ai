package com.son.myapplication.ui.screen

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.son.myapplication.databinding.ActivityMainBinding
import com.son.myapplication.ui.adapter.StyleAdapter
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModel<MainViewModel>()
    private val styleAdapter by lazy { StyleAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupData()
        setupObserver()
        setupListener()
    }

    private fun setupListener() {
        binding.btnGen.setOnClickListener {

        }
    }

    private fun setupData() {
        binding.rcStyle.adapter = styleAdapter
    }

    private fun setupObserver() {
        viewModel.uiState
            .map {
                it.listStyle
            }
            .filterNotNull()
            .distinctUntilChanged()
            .onEach {
                styleAdapter.submitList(it)
            }
            .launchIn(lifecycleScope)
    }
}