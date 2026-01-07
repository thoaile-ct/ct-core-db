package com.chotot.vn.base

/**
 * Copyright (c) 2023 ChoTot. All rights reserved.
 * Created by NoOne on 23/08/2022.
 */

import androidx.room.TypeConverter
import com.chotot.vn.core_data.CoreDBBanner
import com.chotot.vn.core_data.CoreDBCategory
import com.chotot.vn.core_data.CoreDBQuickMenu
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object CoreDBConverters {

    @TypeConverter
    fun fromString(value: String?): ArrayList<String> {
        if (value.isNullOrBlank() || value == "[]") {
            return arrayListOf()
        }

        var formatterValue = value
        if (value.contains("[")) {
            formatterValue = value.replace("[", "")
        }

        if (formatterValue.contains("]")) {
            formatterValue = formatterValue.replace("]", "")
        }
        val valueArrayList = arrayListOf<String>()
        val valueString = formatterValue.split(",")
        for (i in valueString.indices) {
            valueArrayList.add(valueString[i].trim())
        }
        return valueArrayList
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<String?>?): String {
        list?.let {
            return it.toString()
        }
        return ""
    }

    @TypeConverter
    fun fromStringToHashMap(value: String?): HashMap<String, Any>? {
        return if (value.isNullOrBlank()) {
            null
        } else {
            val type = object : TypeToken<HashMap<String, Any>>() {}.type
            Gson().fromJson(value, type)
        }
    }

    @TypeConverter
    fun fromHashMapToString(value: HashMap<String, Any>?): String? {
        return Gson().toJson(value)
    }

    /**
     * Convert for [CoreDBBanner]
     */
    @TypeConverter
    fun fromStringToCoreDBBanners(value: String?): List<CoreDBBanner>? {
        if (value.isNullOrEmpty()) return null

        val type = object : TypeToken<List<CoreDBBanner>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromCoreDBBannersToString(value: List<CoreDBBanner>?): String? {
        if (value == null) return ""

        val type = object : TypeToken<List<CoreDBBanner>>() {}.type
        return Gson().toJson(value, type)
    }

    /**
     * Convert for [CoreDBQuickMenu]
     */
    @TypeConverter
    fun fromStringToCoreDBQuickMenus(value: String?): List<CoreDBQuickMenu>? {
        if (value.isNullOrEmpty()) return null

        val type = object : TypeToken<List<CoreDBQuickMenu>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromCoreDBQuickMenusToString(value: List<CoreDBQuickMenu>?): String? {
        if (value == null) return ""

        val type = object : TypeToken<List<CoreDBQuickMenu>>() {}.type
        return Gson().toJson(value, type)
    }

    /**
     * Convert for [CoreDBCategory]
     */
    @TypeConverter
    fun fromStringToCoreDBCategories(value: String?): List<CoreDBCategory>? {
        if (value.isNullOrEmpty()) return null

        val type = object : TypeToken<List<CoreDBCategory>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromCoreDBCategoriesToString(value: List<CoreDBCategory>?): String? {
        if (value == null) return ""

        val type = object : TypeToken<List<CoreDBCategory>>() {}.type
        return Gson().toJson(value, type)
    }
}