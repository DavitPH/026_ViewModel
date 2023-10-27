package com.example.a026_viewmodel.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.a026_viewmodel.Data.DataForm
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class CobaViewModel : ViewModel(){
    var namaUsr : String by mutableStateOf("")
        private set
    var noTlp : String by mutableStateOf("")
        private set
    var jenisKL : String by mutableStateOf("")
        private set
    var status : String by mutableStateOf("")
        private set

    private val _uiState = MutableStateFlow(DataForm())
    val uiState: StateFlow<DataForm> = _uiState.asStateFlow()

    private val _uiStatus = MutableStateFlow(DataForm())
    val uiStatus: StateFlow<DataForm> = _uiStatus.asStateFlow()

    fun InsertData(namanya:String,teleponnya:String, jenisnya:String, statusnya:String){
        namaUsr = namanya;
        noTlp = teleponnya;
        jenisKL = jenisnya;
        status = statusnya;
    }

    fun setJK(plhJK:String){
        _uiState.update{currentState -> currentState.copy (sex = plhJK)}
    }
    fun setST(plhST:String){
        _uiStatus.update{currentState -> currentState.copy (sex = plhST)}
    }


}