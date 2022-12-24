package com.example.shoplist.data

import com.example.shoplist.domain.ShopItem
import javax.inject.Inject

class ShopListMapper @Inject constructor(){

    fun mapEntityToDBModel(shopItem: ShopItem) = ShopItemDbModel(
        id = shopItem.id,
        name = shopItem.name,
        count = shopItem.count,
        enabled = shopItem.enabled
    )

    fun mapDbModelToEntity (shopItemDbModel: ShopItemDbModel) = ShopItem(
        id = shopItemDbModel.id,
        name = shopItemDbModel.name,
        enabled = shopItemDbModel.enabled,
        count = shopItemDbModel.count
    )

    fun mapListDBModelToListEntity (list: List<ShopItemDbModel>) = list.map {
        mapDbModelToEntity(it)
    }
}