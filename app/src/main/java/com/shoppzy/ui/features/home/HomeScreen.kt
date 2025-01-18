package com.shoppzy.ui.features.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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
           val data  = uiState.data
           LazyColumn {
               items(data.size){
                   ProductItem(product = data[it])
               }
           }
       }
   }

}

@Composable
fun ProductItem(product: Product) {
    Card(modifier = Modifier.padding(8.dp)) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text= product.title, style = MaterialTheme.typography.titleLarge)
                Text(text= "${product.price}", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}