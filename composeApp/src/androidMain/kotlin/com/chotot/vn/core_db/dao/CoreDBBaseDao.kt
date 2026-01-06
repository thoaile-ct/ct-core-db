package com.chotot.vn.dao

import android.app.Application
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.chotot.vn.utils.CoreDBChototDatabaseHelper

/**
 * Created by thanhnguyen on 6/11/15.
 */
open class CoreDBBaseDao(application: Application) {

    // Database fields
    @JvmField
    protected var db: SQLiteDatabase? = null

    private val dbHelper: CoreDBChototDatabaseHelper by lazy { CoreDBChototDatabaseHelper.getInstance(application) }

    @Throws(SQLException::class)
    fun openConnection() {
        db = dbHelper.writableDatabase
    }

    fun closeConnection() {
        db?.let {
            if (it.isOpen) {
                it.close()
            }
        }
    }
}