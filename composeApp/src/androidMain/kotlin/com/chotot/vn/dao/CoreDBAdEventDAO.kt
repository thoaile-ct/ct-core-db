package com.chotot.vn.dao

import android.app.Application
import android.content.ContentValues
import android.database.sqlite.SQLiteException
import android.util.Log
import com.chotot.vn.core_data.model.CoreDataAdEvent
import com.chotot.vn.utils.CoreDBChototDatabaseHelper

/**
 * Created by mb on 10/20/15.
 */
class CoreDBAdEventDAO(application: Application) : CoreDBBaseDao(application) {

    // Insert a single
    fun insert(acAd: CoreDataAdEvent?): Long {
        acAd ?: return 0L
        var result = 0L
        try {
            openConnection()
            val values = ContentValues()
            values.put(CoreDBChototDatabaseHelper.TABLE_AD_EVENT_COLUMN_AD_ID, acAd.adId)
            values.put(CoreDBChototDatabaseHelper.TABLE_AD_EVENT_COLUMN_AD_LIST_ID, acAd.adListId)
            values.put(CoreDBChototDatabaseHelper.TABLE_AD_EVENT_COLUMN_ACCOUNT_ID, acAd.accountId)
            values.put(CoreDBChototDatabaseHelper.TABLE_AD_EVENT_COLUMN_AD_STATUS, acAd.adStatus)
            values.put(CoreDBChototDatabaseHelper.TABLE_AD_EVENT_COLUMN_MESSAGE, acAd.message)
            result = db?.insert(CoreDBChototDatabaseHelper.TABLE_AD_EVENT, null, values) ?: 0L
            closeConnection()
        } catch (e: SQLiteException) {
            Log.e(this.javaClass.name, e.message.orEmpty())
        }
        Log.d(this.javaClass.name, "result = $result")
        return result
    }
}