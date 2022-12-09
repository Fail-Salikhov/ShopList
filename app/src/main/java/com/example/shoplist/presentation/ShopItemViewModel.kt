package com.example.shoplist.presentation

import androidx.lifecycle.ViewModel
import com.example.shoplist.data.ShopListRepositoryImpl
import com.example.shoplist.domain.*

class ShopItemViewModel: ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val editeShopItemUseCase = EditShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)

    fun getShopItem (shopItemId: Int) {
        val item = getShopItemUseCase.getShopItem(shopItemId)
    }

    fun addShopItem (inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val validResult = validateInput(name, count)
        if (validResult){
            val shopItem = ShopItem(name,true, count)
            addShopItemUseCase.addShopItem(shopItem)
        }

    }

    fun editeShopItem (inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val validResult = validateInput(name, count)
        if (validResult) {
            val shopItem = ShopItem(name,true, count)
            editeShopItemUseCase.editShopItem(shopItem)
        }
    }

    private fun parseName (inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseCount (inputCount: String?):Int {
        return try {
            inputCount?.trim().toInt() ?: 0
        }catch (e: java.lang.Exception) {
            0
        }
    }

    private fun validateInput (name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            result = false
        }
        if (count <= 0){
            result = false
        }
        return result
    }
}