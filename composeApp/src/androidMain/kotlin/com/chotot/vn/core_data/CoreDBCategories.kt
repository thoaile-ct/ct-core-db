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
    tableName = CoreDBConstant.TABLE_CATEGORIES,
    primaryKeys = [CoreDBConstant.TABLE_CATEGORIES_ID]
)
data class CoreDBCategories(
    @ColumnInfo(name = CoreDBConstant.TABLE_CATEGORIES_ID)
    val id: String = CoreDBConstant.TABLE_CATEGORIES_ID_POSITION, // this id is hardcode for the position of UI view

    @ColumnInfo(name = CoreDBConstant.TABLE_CATEGORIES_COLUMN_CODE)
    val code: String? = null,

    @ColumnInfo(name = CoreDBConstant.TABLE_CATEGORIES_COLUMN_LIST)
    val categories: List<CoreDBCategory>? = null,
)

data class CoreDBCategory(
    val categoryId: Int = -1,
    val categoryIcon: String? = null,
    val categoryName: String? = null,
    val isFilterGiveAway: Boolean? = null,
    val isNew: Boolean? = null,
    val position: Int? = null,
)