package com.example.shoplist.domain

data class ShopItem(

    var name: String,
    val enabled: Boolean,
    val count: Int,
    var id: Int = UNDEFINED_ID
){
    companion object{
        const val UNDEFINED_ID = 0
    }
}
