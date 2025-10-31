package com.danish.stylish.presentation.products

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.danish.stylish.R
import com.danish.stylish.domain.model.Product
import com.danish.stylish.domain.utils.Result
import com.danish.stylish.navigation.Routes
import com.danish.stylish.presentation.component.BottomNavItem
import com.danish.stylish.presentation.component.BottomNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
//@Preview(showSystemUi = true)
fun ProductListScreen(
    navController: NavHostController,
    productViewModel: ProductViewModel = hiltViewModel(),
) {

    val productsState by productViewModel.productState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    // Center logo
                    Box(
                        modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_splash),
                            contentDescription = "Brand Logo",
                            modifier = Modifier.height(32.dp)
                        )
                    }
                }, navigationIcon = {
                    // Left menu icon
                    IconButton(onClick = { /* Handle menu */ }) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_menu),
                            contentDescription = "Menu",
                            modifier = Modifier
                                .size(28.dp)
                                .background(Color.White)
                        )
                    }
                }, actions = {
                    // Right profile avatar
                    IconButton(onClick = { /* Handle profile */ }) {
                        Image(
                            painter = painterResource(id = R.drawable.profile_avatar),
                            contentDescription = "Profile",
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .border(2.dp, Color(0xFFE0E0E0), CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(
                currentRoute = "home",
                onItemClick = { item ->
                    when (item) {
                        is BottomNavItem.WishList -> {navController.navigate(Routes.WishListScreen)}
                        is BottomNavItem.Cart -> {}
                        is BottomNavItem.Home -> {}
                        is BottomNavItem.Search -> {}
                        is BottomNavItem.Setting -> {}
                    }
                })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFf9f9f9))
        ) {
            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    // Trigger search on every text change
                    productViewModel.searchProduct(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(68.dp)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = {
                    Text(
                        text = "Search any Product..", color = Color(0xFFBBBBBB), fontSize = 14.sp
                    )
                },
                leadingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_magnifier),
                        contentDescription = "Search",
                        modifier = Modifier.size(20.dp)
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { /* Handle voice search */ }) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_mic),
                            contentDescription = "Voice Search",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                },
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )


            // Items count and filter row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                when (val state = productsState) {
                    is Result.Success -> {
                        Text(
                            text = "${state.data.size} Items",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    else -> {
                        Text(
                            text = "Loading...",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Row {
                    TextButton(
                        onClick = { /* Handle sort */ }) {
                        Text("Sort")
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            Icons.Default.MoreVert,
                            contentDescription = "Sort",
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    TextButton(
                        onClick = { /* Handle filter */ }) {
                        Text("Filter")
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            Icons.Default.Settings,
                            contentDescription = "Filter",
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            // Product Grid
            when (val state = productsState) {
                is Result.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is Result.Success -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(state.data) { product ->
                            ProductCard(
                                product = product, onClick = {
                                    navController.navigate(Routes.ProductDetailScreen(product.id))
                                })
                        }
                    }
                }

                is Result.Failure -> {
                    Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Error: ${state.message}", color = Color.Red
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            TextButton(
                                onClick = { productViewModel.retryLoading() }) {
                                Text("Retry")
                            }
                        }
                    }
                }

                Result.Idle -> {
                    // Initial state
                }
            }
        }
    }
}

@Composable

fun ProductCard(
    product: Product,
    onClick: () -> Unit,
) {
    val context = LocalContext.current

    // Share function
    fun shareProduct(product: Product) {
        val shareText =
            "Check out this amazing product: ${product.title}\n\n" + "${product.description}\n\n" + "Price: ₹${
                String.format("%.0f", product.price * 83)
            }\n" + "Rating: ${
                String.format(
                    "%.1f", product.rating
                )
            } stars\n\n" + "Get it now on EcoMart!"

        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
            putExtra(Intent.EXTRA_SUBJECT, "Check out ${product.title}")
        }

        val chooserIntent = Intent.createChooser(shareIntent, "Share Product")
        context.startActivity(chooserIntent)
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            // Product Image
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(context).data(product.thumbnail).crossfade(true)
                    .build(),
                contentDescription = product.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFF5F5F5)),
                contentScale = ContentScale.Fit,
                loading = {
                    Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp), strokeWidth = 2.dp
                        )
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
                            text = "No Image", fontSize = 12.sp, color = Color.Gray
                        )
                    }
                })

            Spacer(modifier = Modifier.height(8.dp))

            // Product Title
            Text(
                text = product.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Product Description
            Text(
                text = product.description,
                fontSize = 12.sp,
                color = Color.Gray,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Price and Rating Row
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {
                Row {
                    // Discounted Price
                    Text(
                        text = "₹${
                            String.format(
                                "%.0f", product.price * 83
                            )
                        }", // Convert to INR approximately
                        fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    // Original Price (if there's a discount)
                    if (product.discountPercentage > 0) {
                        val originalPrice = product.price / (1 - product.discountPercentage / 100)
                        Text(
                            text = "₹${String.format("%.0f", originalPrice * 83)}",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            textDecoration = TextDecoration.LineThrough
                        )
                    }
                }


                // Rating and Share
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween
                ) {
                    StarRating(
                        rating = product.rating, starSize = 14.dp
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = String.format("%.1f", product.rating),
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = { shareProduct(product) }, modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            Icons.Default.Share,
                            contentDescription = "Share",
                            tint = Color.Gray,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun StarRating(
    rating: Double = 2.3,
    starSize: Dp = 16.dp,
    maxStars: Int = 5,
) {
    Row {
        repeat(maxStars) { index ->
            val starRating = when {
                rating >= index + 1 -> 1.0 // Full star
                rating > index -> rating - index // Partial star
                else -> 0.0 // Empty star
            }

            Box {
                // Background (empty) star
                Icon(
                    Icons.Default.Star,
                    contentDescription = null,
                    tint = Color(0xFFE0E0E0), // Light gray for empty
                    modifier = Modifier.size(starSize)
                )

                // Foreground (filled) star with clipping for partial fill
                if (starRating > 0) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFA726), // Orange for filled
                        modifier = Modifier
                            .size(starSize)
                            .clipToBounds()
                            .drawWithContent {
                                clipRect(
                                    right = size.width * starRating.toFloat()
                                ) {
                                    this@drawWithContent.drawContent()
                                }
                            })
                }
            }
        }
    }
}
