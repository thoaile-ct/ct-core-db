package com.chotot.vn.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by mb on 11/3/14.
 */
class CoreDBSharedPreferenceHelper(context: Context?) {

    private val sharedPreferences: SharedPreferences
    private val sharedPreferencesPersistence: SharedPreferences

    @JvmOverloads
    fun putString(key: String?, value: String?, persistent: Boolean = false) {
        val editor = getPreference(persistent).edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(key: String?, defaultValue: String, persistent: Boolean): String {
        return getPreference(persistent).getString(key, defaultValue) ?: defaultValue
    }

    fun getNullableString(key: String?, defaultValue: String?, persistent: Boolean): String? {
        return getPreference(persistent).getString(key, defaultValue)
    }

    @JvmOverloads
    fun putInt(key: String?, value: Int, persistent: Boolean = false) {
        val editor = getPreference(persistent).edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun getInt(key: String?, defaultValue: Int, persistent: Boolean): Int {
        return getPreference(persistent).getInt(key, defaultValue)
    }

    fun putMap(key: String?, map: Map<String, String>, persistent: Boolean = false) {
        val jsonString = Gson().toJson(map)
        getPreference(persistent).edit().putString(key, jsonString).apply()
    }

    fun getMap(key: String?, persistent: Boolean = false): Map<String, String>? {
        val jsonString = getPreference(persistent).getString(key, "")
        if (jsonString.isNullOrEmpty()) {
            return emptyMap()
        }
        val listType = object : TypeToken<Map<String, String>>() {}.type
        return Gson().fromJson(jsonString, listType)
    }

    fun putStringList(key: String?, list: List<String>, persistent: Boolean = false) {
        val editor = getPreference(persistent).edit()
        val json: String = Gson().toJson(list)
        editor.putString(key, json)
        editor.apply()
    }

    fun getStringList(key: String?, persistent: Boolean = false): List<String>? {
        val json = getPreference(persistent).getString(key, null)
        val type = object : TypeToken<java.util.ArrayList<String>>() {}.type
        return Gson().fromJson(json, type)
    }

    @JvmOverloads
    fun putLong(key: String?, value: Long, persistent: Boolean = false) {
        val editor = getPreference(persistent).edit()
        editor.putLong(key, value)
        editor.apply()
    }

    fun getLong(key: String?, defaultValue: Long, persistent: Boolean): Long {
        return getPreference(persistent).getLong(key, defaultValue)
    }

    @JvmOverloads
    fun putBoolean(key: String?, value: Boolean, persistent: Boolean = false) {
        val editor = getPreference(persistent).edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBoolean(key: String?, defaultValue: Boolean, persistent: Boolean): Boolean {
        return getPreference(persistent).getBoolean(key, defaultValue)
    }

    fun putStringSet(key: String?, value: Set<String?>, persistent: Boolean) {
        val editor = getPreference(persistent).edit()
        editor.putStringSet(key, value)
        editor.apply()
    }

    fun getStringSet(key: String?, persistent: Boolean): Set<String>? {
        return getPreference(persistent).getStringSet(key, null)
    }

    @JvmOverloads
    fun remove(key: String?, persistent: Boolean = false) {
        val editor = getPreference(persistent).edit()
        editor.remove(key)
        editor.apply()
    }

    /* SAVE OBJECT IN SHARED PREFERENCE */ /* SAVE OBJECT IN SHARED PREFERENCE */
    @JvmOverloads
    fun putObject(
        key: String?,
        `object`: Any?,
        persistent: Boolean = false,
    ) {
        try {
            val editor = getPreference(persistent).edit()
            val jsonString = Gson().toJson(`object`)
            editor.putString(key, jsonString)
            editor.apply()
        } catch (e: Exception) {
            Log.e(javaClass.name, e.message!!)
        }
    }

    fun getString(key: String?, defaultValue: String): String {
        return getString(key, defaultValue, false)
    }

    fun getNullableString(key: String?, defaultValue: String?): String? {
        return getNullableString(key, defaultValue, false)
    }

    fun getInt(key: String?, defaultValue: Int): Int {
        return getInt(key, defaultValue, false)
    }

    fun getLong(key: String?, defaultValue: Long): Long {
        return getLong(key, defaultValue, false)
    }

    fun getBoolean(key: String?, defaultValue: Boolean): Boolean {
        return getBoolean(key, defaultValue, false)
    }

    fun getStringSet(key: String?): Set<String>? {
        return getStringSet(key, false)
    }

    fun putStringSet(key: String?, value: Set<String?>) {
        putStringSet(key, value, false)
    }

    private fun getPreference(persistent: Boolean): SharedPreferences {
        return if (persistent) {
            sharedPreferencesPersistence
        } else {
            sharedPreferences
        }
    }

    /* CLEAR ALL VALUE IN SHARED PREFERENCE */
    fun clear() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    companion object {

        private const val SHARED_PREFERENCE_NAME = "blocket"
        private const val SHARED_PREFERENCE_PERSISTENCE = "persistence"
        private var sharedPreferenceHelper: CoreDBSharedPreferenceHelper? = null

        @JvmStatic
        fun getInstance(context: Context?): CoreDBSharedPreferenceHelper {
            if (sharedPreferenceHelper == null) {
                sharedPreferenceHelper = CoreDBSharedPreferenceHelper(context)
            }
            return sharedPreferenceHelper!!
        }
    }

    init {
        sharedPreferences = context!!.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
        sharedPreferencesPersistence = context.getSharedPreferences(SHARED_PREFERENCE_PERSISTENCE, Context.MODE_PRIVATE)
    }
}