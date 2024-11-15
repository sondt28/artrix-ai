package com.son.myapplication.ui.screen

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.Manifest.permission.READ_MEDIA_VIDEO
import android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.son.myapplication.databinding.ActivityMainBinding
import com.son.myapplication.ui.adapter.StyleAdapter
import com.son.myapplication.util.FileUtil
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

    private val requestPermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->

        }

    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                viewModel.setCurrentImage(uri)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupData()
        setupObserver()
        setupListener()
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(this, READ_MEDIA_IMAGES) == PERMISSION_GRANTED
        ) {
            openPhotoPicker()

        } else if (
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE &&
            ContextCompat.checkSelfPermission(
                this,
                READ_MEDIA_VISUAL_USER_SELECTED
            ) == PERMISSION_GRANTED
        ) {
            openPhotoPicker()
        } else if (ContextCompat.checkSelfPermission(
                this,
                READ_EXTERNAL_STORAGE
            ) == PERMISSION_GRANTED
        ) {
            openPhotoPicker()
        } else {
            requestPermission()
        }
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            requestPermissions.launch(arrayOf(READ_MEDIA_IMAGES, READ_MEDIA_VISUAL_USER_SELECTED))
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions.launch(arrayOf(READ_MEDIA_IMAGES))
        } else {
            requestPermissions.launch(arrayOf(READ_EXTERNAL_STORAGE))
        }
    }

    private fun openPhotoPicker() {
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun setupListener() {
        binding.btnGen.setOnClickListener {

        }
        binding.vBackgroundAddPhoto.setOnClickListener {
            checkPermission()
        }

        styleAdapter.onItemClickListener = {
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

        viewModel.uiState
            .map {
                it.currentImage
            }
            .distinctUntilChanged()
            .onEach {
                binding.groupAddPhoto.isVisible = it == null
                binding.imgChange.isVisible = it != null
                Glide.with(this).load(it).into(binding.imgPreview)
            }
            .launchIn(lifecycleScope)
    }
}