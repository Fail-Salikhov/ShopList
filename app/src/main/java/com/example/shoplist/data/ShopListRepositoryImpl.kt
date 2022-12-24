package com.example.shoplist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.shoplist.domain.ShopItem
import com.example.shoplist.domain.ShopListRepository
import javax.inject.Inject
import kotlin.random.Random

class ShopListRepositoryImpl @Inject constructor(
    private val shopListDao: ShopListDao,
    private val mapper: ShopListMapper
) : ShopListRepository {


    override suspend fun addShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDBModel(shopItem))
    }

    override suspend fun deleteShopItem(shopItem: ShopItem) {
        shopListDao.deleteShopItem(shopItem.id)
    }

    override suspend fun editShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDBModel(shopItem))
    }

    override fun getShopList(): LiveData<List<ShopItem>> =
        Transformations.map(shopListDao.getShopList()) {
            mapper.mapListDBModelToListEntity(it)
        }


    override suspend fun getShopItem(shopItemId: Int): ShopItem {
        val dbModel = shopListDao.getShopItem(shopItemId)
        return mapper.mapDbModelToEntity(dbModel)
    }
}