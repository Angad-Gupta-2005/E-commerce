package com.angad.ecommerce.repository

import com.angad.ecommerce.data.api.ApiBuilder
import com.angad.ecommerce.data.model.ProductResponse
import com.angad.ecommerce.ui.state.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class ProductRepository @Inject constructor(private val apiBuilder: ApiBuilder) {
    suspend fun getProductDetails(
        productId: String,
        variantId: String,
        lang: String = "en",
        store: String = "KWD"
    ): Flow<UiState<Response<ProductResponse>>> = flow {
        emit(UiState.Loading)
        try {
            val response = apiBuilder.api.getProductDetails(productId, variantId, lang, store)
            if (response.isSuccessful) {
                emit(UiState.Success(response))
            } else {
                emit(UiState.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(UiState.Error(e.message ?: "Unknown error"))
        }
    }
}