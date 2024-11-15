package com.son.myapplication.data.repository.impl

import com.son.myapplication.data.model.Style
import com.son.myapplication.data.network.ApiService.styleService
import com.son.myapplication.data.network.response.handleApi
import com.son.myapplication.data.repository.StyleRepository
import com.son.myapplication.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StyleRepositoryImpl : StyleRepository {
    override suspend fun getStyles(): NetworkResult<List<Style>> {
        return withContext(Dispatchers.IO) {
            when (val response = handleApi { styleService.getStyles() }) {
                is NetworkResult.Success -> {
                    val styles = response.data.data.items
                    NetworkResult.Success(styles)
                }
                is NetworkResult.Error -> NetworkResult.Error(response.code, response.message)
                is NetworkResult.Exception -> NetworkResult.Exception(response.e)
            }
        }
    }
}