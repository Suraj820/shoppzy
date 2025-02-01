package com.shoppzy.ui.features.home

import android.util.Log
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.shoppzy.R
import com.shoppzy.domain.model.Product
import org.koin.androidx.compose.koinViewModel


@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = koinViewModel()) {


    Scaffold {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            val uiState = viewModel.uiState.collectAsState().value

            when (uiState) {
                is HomeScreenUIEvents.Loading -> {
                    CircularProgressIndicator()
                }

                is HomeScreenUIEvents.Error -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Log.e("Suraj==>>", "HomeScreen: " + uiState.message)
                        Text(text = uiState.message)
                    }
                }

                is HomeScreenUIEvents.Success -> {
                    val data = uiState
                    /*LazyColumn {
                        items(data.size){
                            ProductItem(product = data[it])
                        }
                    }*/
                    HomeContent(uiState.featuredProducts, uiState.popularProduct,uiState.categories)
                }
            }
        }
    }


}

@Composable
fun HomeContent(featured: List<Product>, popular: List<Product>,categories:List<String>) {
    LazyColumn {
        item {
            ProfileHeader()
            Spacer(modifier = Modifier.size(16.dp))
            SearchBar("") {

            }
            Spacer(modifier = Modifier.size(16.dp))

        }
        item {
            if (categories.isNotEmpty()){
                LazyRow {
                    items(categories.size){
                        Text(
                            text = categories[it].replaceFirstChar { it.uppercase() },
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(horizontal = 8.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)).padding(8.dp),

                        )
                    }
                }
                Spacer(modifier = Modifier.size(16.dp))
            }

            if (featured.isNotEmpty()) {
                HomeProductsRow(featured, "Featured")
                Spacer(modifier = Modifier.size(16.dp))
            }
            if (popular.isNotEmpty()) {
                HomeProductsRow(popular, "Popular")
            }
        }
    }
}



@Composable
fun HomeProductsRow(products: List<Product>, title: String) {
    Column {
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterStart),
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "View all",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.CenterEnd)
            )

        }
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow {
            items(products.size) {
                ProductItem(product = products[it])
            }
        }
    }

}

@Composable
fun ProductItem(product: Product) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .size(width = 126.dp, height = 144.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.LightGray.copy(alpha = 0.3f))
    ) {

        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = product.image,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(96.dp),
                placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
                contentScale = ContentScale.FillBounds
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = product.title,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 8.dp),
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "$${product.price}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = 8.dp),
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )
        }

    }
}

@Composable
fun ProfileHeader(modifier: Modifier = Modifier) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            AsyncImage(
                model = "https://avatars.githubusercontent.com/u/54694312?v=4",
                contentScale = ContentScale.Inside,
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.White, CircleShape),
                placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
            )
            Spacer(modifier = Modifier.size(8.dp))
            Column {
                Text(text = "Hello,", style = MaterialTheme.typography.bodySmall)
                Text(text = "Suraj", style = MaterialTheme.typography.titleMedium)
            }


        }
        Image(
            painter = painterResource(id = R.drawable.ic_notification),
            contentDescription = null,
            modifier = Modifier
                .size(38.dp)
                .align(Alignment.CenterEnd)
                .clip(CircleShape)
                .background(Color.LightGray.copy(alpha = 0.3f)),
            contentScale = ContentScale.Inside
        )
    }
}

@Composable
fun SearchBar(value: String, onTextChange: (String) -> Unit) {
    var previousValue by remember { mutableStateOf("") }
    TextField(
        value = previousValue,
        onValueChange = {
            previousValue = it
            onTextChange(it)
        },
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(32.dp),
        placeholder = { Text(text = "Search here", style = MaterialTheme.typography.bodySmall) },
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = Color.LightGray.copy(alpha = 0.3f),
            unfocusedContainerColor = Color.LightGray.copy(alpha = 0.3f)
        )

    )
}




@Preview(showBackground = true)
@Composable
private fun PreviewSearchBar() {

    SearchBar("") { }
}


@Preview(showBackground = true)
@Composable
private fun PreviewProfileHeader() {
    ProfileHeader()
}

@Preview(showBackground = true)
@Composable
private fun PreviewProductItem() {
    ProductItem(
        Product(
            1,
            "Watch",
            100.0.toDouble(),
            "electronics",
            "Cool watch",
            "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg"
        )
    )

}

@Preview(showBackground = true)
@Composable
private fun PreviewHomeContent() {
    val featured = listOf(
        Product(
            1,
            "Watch",
            100.0.toDouble(),
            "electronics",
            "Cool watch",
            "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg"
        )
    )

    val popular = listOf(
        Product(
            1,
            "Mobile",
            100.0.toDouble(),
            "electronics",
            "Cool watch",
            "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg"
        )
    )
    val categories = listOf("electronics","jewelery")
    HomeContent(featured, popular,categories)
}