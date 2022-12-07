package com.example.shoplist.domain

data class ShopItem(

    val name: String,
    val enabled: Boolean,
    val count: Int,
    var id: Int = UNDEFINED_ID
){
    companion object{
        val UNDEFINED_ID = -1
    }
}
