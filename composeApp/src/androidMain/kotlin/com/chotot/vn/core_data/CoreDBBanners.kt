package com.chotot.vn.core_data

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.chotot.vn.core_db.utils.CoreDBConstant

/**
 * this data class use for Room DataBase, if you want to update/add anything field
 * -> please migrate database version into class [CoreDBRoomDatabase.kt] to prevent DataBase crash
 *
 * If you want to add new field -> please set your field default nullable
 * (like below fields, excepted field [primaryKeys] must not be null)
 */

@Entity(
    tableName = CoreDBConstant.TABLE_BANNERS,
    primaryKeys = [CoreDBConstant.TABLE_BANNERS_ID]
)
data class CoreDBBanners(
    @ColumnInfo(name = CoreDBConstant.TABLE_BANNERS_ID)
    val id: String = CoreDBConstant.TABLE_BANNERS_ID_POSITION, // this id is hardcode for the position of UI view

    @ColumnInfo(name = CoreDBConstant.TABLE_BANNERS_COLUMN_STATUS)
    val status: String? = null,

    @ColumnInfo(name = CoreDBConstant.TABLE_BANNERS_COLUMN_LIST)
    val bannerList: List<CoreDBBanner>? = null,
)

data class CoreDBBanner(
    val id: String = "",
    val name: String? = null,
    val url: String? = null,
    val mobileImage: String? = null,
    val isWebView: Boolean? = null,
    val slugName: String? = null
)