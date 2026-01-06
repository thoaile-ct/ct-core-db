package com.chotot.vn.core_data

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.chotot.vn.core_data.saved_ad.CoreDataBookmarkConstants
import com.chotot.vn.core_data.saved_ad.CoreDataBookmarkConstants.TABLE_QUICK_MENUS_ID_POSITION

/**
 * this data class use for Room DataBase, if you want to update/add anything field
 * -> please migrate database version into class [CoreDBRoomDatabase.kt] to prevent DataBase crash
 *
 * If you want to add new field -> please set your field default nullable
 * (like below fields, excepted field [primaryKeys] must not be null)
 */

@Entity(
    tableName = CoreDataBookmarkConstants.TABLE_QUICK_MENUS,
    primaryKeys = [CoreDataBookmarkConstants.TABLE_QUICK_MENUS_ID]
)
data class CoreDBQuickMenus(
    @ColumnInfo(name = CoreDataBookmarkConstants.TABLE_QUICK_MENUS_ID)
    val id: String = TABLE_QUICK_MENUS_ID_POSITION, // this id is hardcode for the position of UI view

    @ColumnInfo(name = CoreDataBookmarkConstants.TABLE_QUICK_MENUS_COLUMN_STATUS)
    val status: String? = null,

    @ColumnInfo(name = CoreDataBookmarkConstants.TABLE_QUICK_MENUS_COLUMN_MENUS_LIST)
    val menusList: List<CoreDBQuickMenu>? = null,

    @ColumnInfo(name = CoreDataBookmarkConstants.TABLE_QUICK_MENUS_COLUMN_LABEL)
    val label: String? = null,

    @ColumnInfo(name = CoreDataBookmarkConstants.TABLE_QUICK_MENUS_COLUMN_DESCRIPTION)
    val description: String? = null,
)

data class CoreDBQuickMenu(
    val menuName: String = "",
    val subMenuName: String? = null,
    val textColor: String? = null,
    val menuIcon: String? = null,
    val appUrl: String? = null,
    val isWebView: Boolean? = null,
    val position: Int? = null,
    val isNew: Boolean? = null,
    val isHot: Boolean? = null,
    val useLocalRegion: Boolean? = null,
    val categoryId: Int? = null,
)
