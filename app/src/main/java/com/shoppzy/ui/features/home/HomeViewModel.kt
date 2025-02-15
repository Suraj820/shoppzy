package com.shoppzy.ui.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoppzy.domain.model.Product
import com.shoppzy.domain.netwrok.ResultWrapper
import com.shoppzy.domain.usecase.GetCategoriesUseCase
import com.shoppzy.domain.usecase.GetProductUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class HomeViewModel(private val getProductUseCase: GetProductUseCase,private val getCategoriesUseCase: GetCategoriesUseCase):ViewModel() {

    private val _uiState = MutableStateFlow<HomeScreenUIEvents>(HomeScreenUIEvents.Loading)
    val uiState: StateFlow<HomeScreenUIEvents> = _uiState.asStateFlow()

    init {
        getAllProducts()
    }
    private fun getAllProducts(){
        viewModelScope.launch {
            _uiState.value = HomeScreenUIEvents.Loading
            val featuredProducts = getProducts("electronics")
            val popularProducts = getProducts("jewelery")
            val categories = getCategories()
            if (featuredProducts.isEmpty() && popularProducts.isEmpty() && categories.isEmpty()){
                _uiState.value = HomeScreenUIEvents.Error("something went wrong")
                return@launch
            }

            _uiState.value = HomeScreenUIEvents.Success(featuredProducts,popularProducts,categories)
        }
    }
    private suspend fun getProducts(category: String?):List<Product>{
            getProductUseCase.execute(category).let { result->
                when(result){
                    is ResultWrapper.Failure -> {
                        //val error = (result).exception.message?:"something went wrong"
                        //_uiState.value = HomeScreenUIEvents.Error(error)
                        return emptyList()
                    }
                    is ResultWrapper.Success -> {
                        return result.value
                    }
                }
            }

    }
    private suspend fun getCategories():List<String>{
        getCategoriesUseCase.execute().let { result->
            when(result){
                is ResultWrapper.Failure -> {
                    //val error = (result).exception.message?:"something went wrong"
                    //_uiState.value = HomeScreenUIEvents.Error(error)
                    return emptyList()
                }
                is ResultWrapper.Success -> {
                    return result.value
                }
            }

        }
    }
}

sealed class HomeScreenUIEvents(){
    object Loading:HomeScreenUIEvents()
    data class Success(
        val featuredProducts:List<Product>,
        val popularProduct: List<Product>,
        val categories:List<String>
    ):HomeScreenUIEvents()
    data class Error(val message:String):HomeScreenUIEvents()


}