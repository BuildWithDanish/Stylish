package com.danish.stylish.presentation.products

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danish.stylish.domain.model.Product
import com.danish.stylish.domain.usecase.GetProductUseCase
import com.danish.stylish.domain.usecase.SearchProductsUseCase
import com.danish.stylish.domain.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase,
    private val searchProductsUseCase: SearchProductsUseCase,
) :
    ViewModel() {

    private val _productState = MutableStateFlow<Result<List<Product>>>(Result.Idle)
    var productState: StateFlow<Result<List<Product>>> = _productState.asStateFlow()
    private var searchJob: Job? = null

    init {
        loadProducts()
    }

    fun loadProducts() {
        Log.d(
            "com.store.ecommerceapplication.Presentation.Products.ProductViewModel",
            "Loading products"
        )
        _productState.value = Result.Loading
        viewModelScope.launch {
            try {
                val result = getProductUseCase()
                Log.d(
                    "com.store.ecommerceapplication.Presentation.Products.ProductViewModel",
                    "Products loaded: $result"
                )
                _productState.value = result
            } catch (e: Exception) {
                Log.e(
                    "com.store.ecommerceapplication.Presentation.Products.ProductViewModel",
                    "Error loading products: ${e.message}",
                    e
                )
                _productState.value = Result.Failure(e.message ?: "Unknown error")
            }
        }
    }

    fun retryLoading() {
        loadProducts()
    }

    fun searchProduct(query: String) {
        //Cancel previous job
        searchJob?.cancel()

        // Debounce search - wait 300ms before executing
        searchJob = viewModelScope.launch {
            delay(300)
            Log.d("ProductViewModel", "Searching for: $query")
            _productState.value = Result.Loading
            try {
                val result = searchProductsUseCase(query)
                Log.d("ProductViewModel", "Search results: $result")
                _productState.value = result
            } catch (e: Exception) {
                Log.e("ProductViewModel", "Error searching products: ${e.message}", e)
                _productState.value = Result.Failure(e.message ?: "Unknown error")
            }
        }
    }
}