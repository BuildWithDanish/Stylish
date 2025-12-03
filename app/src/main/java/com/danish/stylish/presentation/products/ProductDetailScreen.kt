package com.danish.stylish.presentation.products

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.danish.stylish.domain.model.Product
import com.danish.stylish.domain.utils.Result
import com.danish.stylish.presentation.cart.CartViewModel
import com.danish.stylish.presentation.wishlist.WishListViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: Int = 1,
    productViewModel: ProductViewModel = hiltViewModel(),
    navController: NavController,
    wishListViewmodel: WishListViewModel = hiltViewModel(),
    cartViewModel:  CartViewModel = hiltViewModel()
) {

    val productState by productViewModel.productState.collectAsState()
    var isFavourite by remember { mutableStateOf(false) }
    var selectedImageIndex by remember { mutableStateOf(0) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    //check if product is in wishlist
    LaunchedEffect(productId) {
        isFavourite = wishListViewmodel.isInWishlist(productId)
    }

    // Share function
    fun shareProduct(product: Product) {
        val shareText = "Check out this amazing product: ${product.title}\n\n" +
                "${product.description}\n\n" +
                "Price: ₹${String.format("%.0f", product.price * 83)}\n" +
                "Rating: ${String.format("%.1f", product.rating)} stars\n\n" +
                "Get it now on EcoMart!"

        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
            putExtra(Intent.EXTRA_SUBJECT, "Check out ${product.title}")
        }

        val chooserIntent = Intent.createChooser(shareIntent, "Share Product")
        context.startActivity(chooserIntent)
    }

    //find Product by ID
    val product = when (val state = productState) {
        is Result.Success -> state.data.find { it.id == productId }
        else -> null
    }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = "Product Details") },
            navigationIcon = {
                IconButton(onClick = {navController.popBackStack()}) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                //share
                IconButton(onClick = {
                    product?.let { shareProduct(it) }
                }) {
                    Icon(
                        Icons.Default.Share,
                        contentDescription = "Share"
                    )
                }
                //Favourite
                IconButton(onClick = {
                    product?.let { product ->
                        coroutineScope.launch {
                            if (isFavourite) {
                                wishListViewmodel.removeFromWishlist(product.id)
                            } else {
                                wishListViewmodel.addToWishlist(product)
                                isFavourite = true
                            }
                        }
                    }
                }) {
                    Icon(
                        if (isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favourite",
                        tint = if (isFavourite) Color.Red else Color.Gray
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White
            )
        )
    }) { paddingValues ->
        when {
            productState is Result.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            product == null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Product not found")
                }
            }

            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState())
                ) {
                    //Product Image
                    ProductImageGallery(
                        product = product,
                        selectedImageIndex = selectedImageIndex,
                        onImageSelected = { selectedImageIndex = it }

                    )

                    //Product Detail section
                    ProductDetailSection(product = product)

                    // Add to cart
                    AddToCartSection(
                        product = product,
                        onAddToCart =  {
                            product?.let { prod ->
                                cartViewModel.addToCart(prod, 1)
                                Toast.makeText(
                                    context,
                                    "${prod.title} added to cart",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ProductImageGallery(
    product: Product,
    selectedImageIndex: Int,
    onImageSelected: (Int) -> Unit,
) {

    val context = LocalContext.current
    val images = if (product.images.isNotEmpty()) product.images else listOf(product.thumbnail)

    Column {
        //main Image
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(context)
                    .data(images.getOrNull(selectedImageIndex) ?: product.thumbnail)
                    .crossfade(true)
                    .build(),
                contentDescription = product.title,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                contentScale = ContentScale.Fit,
                loading = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                },
                error = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFFF5F5F5)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Image not available",
                            color = Color.Gray
                        )
                    }
                }
            )
        }

        //Image Thumbnail
        if (images.size > 1) {
            LazyRow(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(images.size) { index ->
                    Card(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        colors = CardDefaults.cardColors(
                            containerColor = if (index == selectedImageIndex) Color(0xFF4285F4).copy(
                                0.1f
                            ) else Color.White
                        ),
                        onClick = { onImageSelected(index) }
                    ) {
                        SubcomposeAsyncImage(
                            model = ImageRequest.Builder(context).data(images[index])
                                .crossfade(true)
                                .build(),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                            loading = {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(20.dp),
                                        strokeWidth = 2.dp
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProductDetailSection(product: Product) {
    Column(modifier = Modifier.padding(16.dp)) {
        //Brand name
        if (product.brand.isNotEmpty()) {
            Text(
                text = product.brand,
                fontSize = 14.sp,
                color = Color(0xFF4285F4),
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(4.dp))
        }

        // Title
        Text(
            text = product.title,
            fontSize = 24.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        //Rating and Review

        Row(verticalAlignment = Alignment.CenterVertically) {
            StarRating(rating = product.rating)
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "${
                    String.format(
                        "%.1f",
                        product.rating
                    )
                } (${(product.rating * 100).toInt()} reviews)",
                color = Color.DarkGray,
                fontSize = 14.sp
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        //Price
        Row(verticalAlignment = Alignment.CenterVertically) {
            val originalPrice = product.price / (1 - product.discountPercentage / 100)
            Text(
                text = "₹${(product.price * 83).toInt()}",
                fontSize = 28.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            if (product.discountPercentage > 0) {
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "${String.format("%.0f", originalPrice * 83)}",
                    fontSize = 18.sp,
                    color = Color.Gray,
                    textDecoration = TextDecoration.LineThrough
                )
                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = "${product.discountPercentage.toInt()}% OFF",
                    fontSize = 14.sp,
                    color = Color(0xFF4CAF50),
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        //Category
        if (product.category.isNotEmpty()) {
            Text(
                text = "Category: ${product.category.replaceFirstChar { it.uppercase() }}",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(8.dp))
        }
        //Stock status
        Text(
            text = if (product.stock > 0) "In Stock" else "Out of Stock",
            fontSize = 14.sp,
            color = if (product.stock < 0) Color.Red else Color(0xFF4CAF50),
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Description
        Text(
            text = "Description",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = product.description,
            fontSize = 16.sp,
            color = Color.Gray,
            lineHeight = 24.sp
        )
    }
}

//Add to Cart
@Composable
fun AddToCartSection(
    product: Product,
    onAddToCart: () -> Unit,
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Button(
            onAddToCart,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = product.stock > 0,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4285F4),
                disabledContentColor = Color.Gray
            )
        ) {
            Text(
                text = "Add to Cart",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        //buy button
        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            enabled = product.stock > 0,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE05428),
                disabledContentColor = Color.Gray
            )
        ) {
            Text(
                text = "Buy Now",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

/*@Composable
@Preview(showBackground = true)
fun ProductImageGalleryPre() {
    ProductImageGallery(
        product = Product(
            id = 1,
            title = "Essence Mascara Lash Princess",
            description = "The Essence Mascara Lash Princess is a popular mascara known for its volumizing and lengthening effects. Achieve dramatic lashes with this long-lasting and cruelty-free formula.",
            price = 9.99,
            discountPercentage = 10.48,
            rating = 2.56,
            stock = 99,
            brand = "Essence",
            category = "beauty",
            thumbnail = "https://cdn.dummyjson.com/product-images/beauty/essence-mascara-lash-princess/thumbnail.webp",
            images = listOf(
                "https://cdn.dummyjson.com/product-images/beauty/essence-mascara-lash-princess/1.webp"
            )
        ),
        selectedImageIndex = 0,
        onImageSelected = {}

    )
}
*/
/*

@Composable
@Preview(showBackground = true)
fun ProductDetailSectionPre() {
    ProductDetailSection(
        product = Product(
            id = 1,
            title = "Essence Mascara Lash Princess",
            description = "The Essence Mascara Lash Princess is a popular mascara known for its volumizing and lengthening effects. Achieve dramatic lashes with this long-lasting and cruelty-free formula.",
            price = 9.99,
            discountPercentage = 10.48,
            rating = 2.56,
            stock = 99,
            brand = "Essence",
            category = "beauty",
            thumbnail = "https://cdn.dummyjson.com/product-images/beauty/essence-mascara-lash-princess/thumbnail.webp",
            images = listOf(
                "https://cdn.dummyjson.com/product-images/beauty/essence-mascara-lash-princess/1.webp"
            )
        )
    )
}
*/

/*
@Composable
@Preview(showBackground = true)
fun AddToCartPre() {
    AddToCartSection(
        Product(
            id = 1,
            title = "Essence Mascara Lash Princess",
            description = "The Essence Mascara Lash Princess is a popular mascara known for its volumizing and lengthening effects. Achieve dramatic lashes with this long-lasting and cruelty-free formula.",
            price = 9.99,
            discountPercentage = 10.48,
            rating = 2.56,
            stock = 99,
            brand = "Essence",
            category = "beauty",
            thumbnail = "https://cdn.dummyjson.com/product-images/beauty/essence-mascara-lash-princess/thumbnail.webp",
            images = listOf(
                "https://cdn.dummyjson.com/product-images/beauty/essence-mascara-lash-princess/1.webp"
            )
        )
    ) { }
}*/
