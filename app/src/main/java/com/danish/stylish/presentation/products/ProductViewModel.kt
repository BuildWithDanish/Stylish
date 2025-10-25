package com.danish.stylish.presentation.products

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danish.stylish.domain.model.Product
import com.danish.stylish.domain.usecase.GetProductUseCase
import com.danish.stylish.domain.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val getProductUseCase: GetProductUseCase) :
    ViewModel() {

    private val _productSate = MutableStateFlow<Result<List<Product>>>(Result.Idle)
    var productState: StateFlow<Result<List<Product>>> = _productSate.asStateFlow()

    init {
        loadProducts()
    }

    fun loadProducts() {
        Log.d(
            "com.store.ecommerceapplication.Presentation.Products.ProductViewModel",
            "Loading products"
        )
        _productSate.value = Result.Loading
        viewModelScope.launch {
            try {
                val result = getProductUseCase()
                Log.d(
                    "com.store.ecommerceapplication.Presentation.Products.ProductViewModel",
                    "Products loaded: $result"
                )
                _productSate.value = result
            } catch (e: Exception) {
                Log.e(
                    "com.store.ecommerceapplication.Presentation.Products.ProductViewModel",
                    "Error loading products: ${e.message}",
                    e
                )
                _productSate.value = Result.Failure(e.message ?: "Unknown error")
            }
        }
    }

    fun retryLoading() {
        loadProducts()
    }
}