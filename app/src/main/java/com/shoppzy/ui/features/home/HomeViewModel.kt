package com.shoppzy.ui.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoppzy.domain.model.Product
import com.shoppzy.domain.netwrok.ResultWrapper
import com.shoppzy.domain.usecase.GetProductUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class HomeViewModel(private val getProductUseCase: GetProductUseCase):ViewModel() {

    private val _uiState = MutableStateFlow<HomeScreenUIEvents>(HomeScreenUIEvents.Loading)
    val uiState: StateFlow<HomeScreenUIEvents> = _uiState.asStateFlow()

    fun getProducts(){
        viewModelScope.launch{
            getProductUseCase.execute().let { result->
                when(result){
                    is ResultWrapper.Failure -> {
                        _uiState.value =
                            result.exception.message?.let { HomeScreenUIEvents.Error(it) }!!
                    }
                    is ResultWrapper.Success -> {
                        _uiState.value = HomeScreenUIEvents.Success(result.value)
                    }
                }
            }
        }
    }
}

sealed class HomeScreenUIEvents(){
    object Loading:HomeScreenUIEvents()
    data class Success(val data:List<Product>):HomeScreenUIEvents()
    data class Error(val message:String):HomeScreenUIEvents()


}