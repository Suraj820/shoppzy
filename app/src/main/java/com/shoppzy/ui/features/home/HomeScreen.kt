package com.shoppzy.ui.features.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.shoppzy.domain.model.Product
import org.koin.androidx.compose.koinViewModel


@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = koinViewModel()) {

    val uiState = viewModel.uiState.collectAsState().value

   when(uiState){
       is HomeScreenUIEvents.Loading ->{
           CircularProgressIndicator()
       }
       is HomeScreenUIEvents.Error -> {
           Column(
               horizontalAlignment = Alignment.CenterHorizontally,
               verticalArrangement = Arrangement.Center,
           ) {
               Log.e("Suraj==>>", "HomeScreen: "+uiState.message )
               Text(text = uiState.message,)
           }
       }
       is HomeScreenUIEvents.Success ->{
           val data  = uiState.featuredProducts
           /*LazyColumn {
               items(data.size){
                   ProductItem(product = data[it])
               }
           }*/
           HomeProductsRow(data,"Featured")
       }
   }

}

@Composable
fun HomeProductsRow(products: List<Product>,title:String) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(5.dp)) {
        Text(text = title)
        LazyRow {
            items(products.size){
                ProductItem(product = products[it])
            }
        }
    }

}

@Composable
fun ProductItem(product: Product) {
    Card(modifier = Modifier.padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Column{
                AsyncImage(
                    model = product.image,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(96.dp)
                )
                Text(text= product.title, style = MaterialTheme.typography.titleSmall)
                Text(text= "$ ${product.priceString}", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewProductItem() {
    ProductItem(Product(
        1,"Watch",100.0.toDouble(),"electronics","Cool watch","https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg"
    ))

}