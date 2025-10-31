package com.danish.stylish.presentation.wishlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danish.stylish.domain.model.Product
import com.danish.stylish.domain.repository.WishListRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class WishListState(
    val allProducts: List<Product> = emptyList(),
    val filteredProducts: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = "",
)

@HiltViewModel
class WishListViewModel @Inject constructor(
    private val wishListRepo: WishListRepo,
) : ViewModel() {
    private val _state = MutableStateFlow(WishListState())
    val state = _state.asStateFlow()

    init {
        loadWishListProduct()
    }

    private fun loadWishListProduct() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                wishListRepo.getWishListProducts().collect { products ->
                    _state.value = _state.value.copy(
                        allProducts = products,
                        filteredProducts = products,
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false, error = e.message ?: "Failed to load wishlist"
                )
            }
        }
    }

    fun searchProducts(query: String) {
        _state.value = _state.value.copy(searchQuery = query)

        val filtered = if (query.isBlank()) {
            _state.value.allProducts
        } else {
            _state.value.allProducts.filter { product ->
                product.title.contains(query, ignoreCase = true) ||
                        product.description.contains(query, ignoreCase = true) ||
                        product.brand.contains(query, ignoreCase = true) ||
                        product.category.contains(query, ignoreCase = true)
            }
        }
        _state.value = _state.value.copy(filteredProducts = filtered)
    }

    fun addToWishlist(product: Product) {
        viewModelScope.launch {
            try {
                wishListRepo.addToWishList(product)
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = e.message ?: "Failed to add to wishlist"
                )
            }
        }
    }

    fun removeFromWishlist(productId: Int) {
        viewModelScope.launch {
            try {
                wishListRepo.removeFromWishList(productId)
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = e.message ?: "Failed to remove from wishlist"
                )
            }
        }
    }

    suspend fun isInWishlist(productId: Int): Boolean {
        return try {
            wishListRepo.isInWishList(productId)
        } catch (e: Exception) {
            false
        }
    }
}