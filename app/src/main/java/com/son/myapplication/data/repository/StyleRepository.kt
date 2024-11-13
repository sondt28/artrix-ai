package com.son.myapplication.data.repository

import com.son.myapplication.data.model.Style
import com.son.myapplication.util.NetworkResult

interface StyleRepository {
    suspend fun getStyles() : NetworkResult<List<Style>>
}