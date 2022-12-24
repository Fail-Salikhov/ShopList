package com.example.shoplist.presentation

import android.app.Application
import androidx.lifecycle.*
import com.example.shoplist.data.ShopListRepositoryImpl
import com.example.shoplist.domain.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShopItemViewModel @Inject constructor(
    private val getShopItemUseCase : GetShopItemUseCase,
            private val editeShopItemUseCase : EditShopItemUseCase,
            private val addShopItemUseCase : AddShopItemUseCase,

) : ViewModel() {



    private val scope = CoroutineScope(Dispatchers.IO)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    fun getShopItem(shopItemId: Int) {
        viewModelScope.launch {
            val item = getShopItemUseCase.getShopItem(shopItemId)
            _shopItem.value = item
        }

    }

    fun addShopItem(inputName: String?, inputCount: String?) {

            val name = parseName(inputName)
            val count = parseCount(inputCount)
            val validResult = validateInput(name, count)
            if (validResult) {
                viewModelScope.launch {
                val shopItem = ShopItem(name, true, count)
                addShopItemUseCase.addShopItem(shopItem)
                workFinish()
                }
            }


    }

    fun editeShopItem(inputName: String?, inputCount: String?) {

            val name = parseName(inputName)
            val count = parseCount(inputCount)
            val validResult = validateInput(name, count)
            if (validResult) {
                _shopItem.value?.let {
                    viewModelScope.launch {
                    val item = it.copy(name = name, count = count)
                    editeShopItemUseCase.editShopItem(item)
                    workFinish()
                    }
                }

            }


    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: java.lang.Exception) {
            0
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (count <= 0) {
            _errorInputCount.value = true
            result = false
        }
        return result
    }

    fun resetErrorName() {
        _errorInputName.value = false
    }

    fun resetErrorCount() {
        _errorInputCount.value = false
    }

    private fun workFinish() {
        _shouldCloseScreen.value = Unit
    }
}