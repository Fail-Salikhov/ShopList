package com.example.shoplist.data

import android.app.Application
import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
import com.example.shoplist.presentation.ShopListApp
import javax.inject.Inject

class ShopListProvider(): ContentProvider() {

    private val component by lazy {
        (context as ShopListApp).component
    }

    @Inject
    lateinit var shopListDao : ShopListDao

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI("com.example.shoplist", "shop_items", GET_SHOP_ITEM_QUERY)
    }

    override fun onCreate(): Boolean {
        component.inject(this)
         return true
    }

    override fun query(
        p0: Uri,
        p1: Array<out String>?,
        p2: String?,
        p3: Array<out String>?,
        p4: String?
    ): Cursor? {
        return when (uriMatcher.match(p0)){
            GET_SHOP_ITEM_QUERY -> {
                shopListDao.getShopListCursor()
            }
            else -> null
        }
    }

    override fun getType(p0: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    companion object {

        private const val GET_SHOP_ITEM_QUERY = 100
    }
}