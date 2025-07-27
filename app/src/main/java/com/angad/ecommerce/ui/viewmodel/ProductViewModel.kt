package com.angad.ecommerce.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angad.ecommerce.data.model.ProductResponse
import com.angad.ecommerce.repository.ProductRepository
import com.angad.ecommerce.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val repository: ProductRepository) : ViewModel()  {

//    MutableStateFlow for get product details
    private val _getProductDetails = MutableStateFlow(GetProductDetailsState())
    val getProductDetails = _getProductDetails.asStateFlow()

    fun fetchProductDetails(productId: String, variantId: String) {
         viewModelScope.launch(Dispatchers.IO) {
            repository.getProductDetails(productId, variantId).collect {
                when (it) {
                    is UiState.Loading -> {
                        _getProductDetails.value = GetProductDetailsState(isLoading = true)
                    }

                    is UiState.Error -> {
                        _getProductDetails.value = GetProductDetailsState(error = it.message, isLoading = false)
                    }

                    is UiState.Success -> {
                        val response = it.data
                        if (response.isSuccessful && response.body() != null) {
                            _getProductDetails.value = GetProductDetailsState(data = response.body(), isLoading = false)
                        } else {
                            _getProductDetails.value = GetProductDetailsState(error = response.message(), isLoading = false)
                        }
                    }
                }
            }
         }
    }

}

data class GetProductDetailsState(
    val isLoading: Boolean = false,
    val error: String? = null,
    var data: ProductResponse? = null
)