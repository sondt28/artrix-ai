package com.son.myapplication.ui.screen

import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModel
import com.son.myapplication.data.model.Style
import com.son.myapplication.data.repository.StyleRepository
import com.son.myapplication.util.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val styleRepository: StyleRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchStyles()
    }

    private fun fetchStyles() {
        viewModelScope.launch {
            when (val styles = styleRepository.getStyles()) {
                is NetworkResult.Success -> {
                    Log.d("TAG", ": ${styles.data}")
                    _uiState.value = _uiState.value.copy(listStyle = styles.data)
                }

                is NetworkResult.Error -> {
                    Log.d("TAG", ": ${styles.message}")
                    // Handle error
                }

                is NetworkResult.Exception -> {
                    Log.d("TAG", ": ${styles.e}")
                    // Handle exception
                }
            }
        }
    }

    fun setCurrentImage(image: Uri) {
        _uiState.value = _uiState.value.copy(currentImage = image.toString())
    }
}

data class MainUiState(
    val listStyle: List<Style>? = null,
    val currentImage: String? = null
)