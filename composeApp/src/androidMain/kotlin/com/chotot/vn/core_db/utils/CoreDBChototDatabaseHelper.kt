package com.chotot.vn.utils

import android.app.Application
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class CoreDBChototDatabaseHelper private constructor(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {

        // Database creation sql statement
        val sqlCreateTableBookmarks = ("CREATE TABLE IF NOT EXISTS " +
                TABLE_BOOKMARK + "(" +
                TABLE_BOOKMARK_COLUMN_ID + " integer primary key autoincrement, " +
                TABLE_BOOKMARK_COLUMN_AD_ID + " integer, " +
                TABLE_BOOKMARK_COLUMN_AD_SUBJECT + " text, " +
                TABLE_BOOKMARK_COLUMN_AD_PRICE + " text, " +
                TABLE_BOOKMARK_COLUMN_AD_TIME + " text, " +
                TABLE_BOOKMARK_COLUMN_AD_IMG_URL + " text, " +
                TABLE_BOOKMARK_COLUMN_AD_CATEGORY_ID + " integer, " +
                TABLE_BOOKMARK_COLUMN_AD_BODY + " text, " +
                TABLE_BOOKMARK_COLUMN_AD_NAME + " text, " +
                TABLE_BOOKMARK_COLUMN_AD_REGION + " text, " +
                TABLE_BOOKMARK_COLUMN_AD_PHONE + " text, " +
                TABLE_BOOKMARK_COLUMN_CREATED_TIME + " integer not null " +
                ");")
        val sqlCreateTableParameters = ("CREATE TABLE IF NOT EXISTS " +
                TABLE_PARAMETER + "(" +
                TABLE_PARAMETER_COLUMN_AD_ID + " integer, " +
                TABLE_PARAMETER_COLUMN_PARAM_ID + " text, " +
                TABLE_PARAMETER_COLUMN_PARAM_LABEL + " text, " +
                TABLE_PARAMETER_COLUMN_PARAM_VALUE + " text" +
                ");")
        val sqlCreateTableAdWatches = ("CREATE TABLE IF NOT EXISTS " +
                TABLE_AD_WATCH + "(" +
                TABLE_AD_WATCH_COLUMN_ID + " integer primary key autoincrement, " +
                TABLE_AD_WATCH_COLUMN_NAME + " text, " +
                TABLE_AD_WATCH_COLUMN_QUERY + " text, " +
                TABLE_AD_WATCH_COLUMN_FILTER + " text, " +
                TABLE_AD_WATCH_COLUMN_CREATED_TIME + " integer" +
                ");")
        val sqlCreateTableAdEvent = ("CREATE TABLE IF NOT EXISTS " +
                TABLE_AD_EVENT + "(" +
                TABLE_AD_EVENT_COLUMN_ID + " integer primary key autoincrement, " +
                TABLE_AD_EVENT_COLUMN_AD_ID + " text, " +
                TABLE_AD_EVENT_COLUMN_AD_LIST_ID + " text, " +
                TABLE_AD_EVENT_COLUMN_ACCOUNT_ID + " text, " +
                TABLE_AD_EVENT_COLUMN_AD_STATUS + " text, " +
                TABLE_AD_EVENT_COLUMN_MESSAGE + " text " +
                ");")
        db.execSQL(sqlCreateTableBookmarks)
        db.execSQL(sqlCreateTableParameters)
        db.execSQL(sqlCreateTableAdWatches)
        db.execSQL(sqlCreateTableAdEvent)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.w(
            CoreDBChototDatabaseHelper::class.java.name, "Upgrading database from version " +
                    oldVersion + " to " + newVersion +
                    ", which will destroy all old data"
        )
        db.execSQL("DROP TABLE IF EXISTS $TABLE_BOOKMARK")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PARAMETER")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_AD_WATCH")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_AD_EVENT")
        onCreate(db)
    }

    companion object {

        private const val DATABASE_NAME = "chotot.db"
        private const val DATABASE_VERSION = 3
        const val TABLE_BOOKMARK = "bookmarks"
        const val TABLE_BOOKMARK_COLUMN_ID = "_id"
        const val TABLE_BOOKMARK_COLUMN_AD_ID = "ad_id"
        const val TABLE_BOOKMARK_COLUMN_AD_SUBJECT = "ad_subject"
        const val TABLE_BOOKMARK_COLUMN_AD_PRICE = "ad_price"
        const val TABLE_BOOKMARK_COLUMN_AD_PRICE_STRING = "ad_price_string"
        const val TABLE_BOOKMARK_COLUMN_AD_TIME = "ad_time"
        const val TABLE_BOOKMARK_COLUMN_AD_IMG_URL = "ad_img_url"
        const val TABLE_BOOKMARK_COLUMN_AD_CATEGORY_ID = "ad_category_id"
        const val TABLE_BOOKMARK_COLUMN_AD_BODY = "ad_body"
        const val TABLE_BOOKMARK_COLUMN_AD_NAME = "ad_name"
        const val TABLE_BOOKMARK_COLUMN_AD_REGION = "ad_region"
        const val TABLE_BOOKMARK_COLUMN_AD_PHONE = "ad_phone"
        const val TABLE_BOOKMARK_COLUMN_CREATED_TIME = "created_time"
        const val TABLE_PARAMETER = "parameters"
        const val TABLE_PARAMETER_COLUMN_AD_ID = "ad_id"
        const val TABLE_PARAMETER_COLUMN_PARAM_ID = "param_id"
        const val TABLE_PARAMETER_COLUMN_PARAM_VALUE = "param_value"
        const val TABLE_PARAMETER_COLUMN_PARAM_LABEL = "param_label"
        const val TABLE_AD_WATCH = "ad_watches"
        const val TABLE_AD_WATCH_COLUMN_ID = "_id"
        const val TABLE_AD_WATCH_COLUMN_NAME = "name"
        const val TABLE_AD_WATCH_COLUMN_QUERY = "query"
        const val TABLE_AD_WATCH_COLUMN_FILTER = "filter"
        const val TABLE_AD_WATCH_COLUMN_CREATED_TIME = "created_time"
        const val TABLE_AD_SYNC_WATCH = "ad_sync_watches"
        const val TABLE_AD_SYNC_WATCH_COLUMN_READDABLE = "ad_sync_readdable"
        const val TABLE_AD_SYNC_WATCH_COLUMN_TEXT_QUERY = "ad_sync_query"
        const val TABLE_AD_SYNC_WATCH_COLUMN_REGION = "ad_sync_region"
        const val TABLE_AD_SYNC_WATCH_COLUMN_PUSH_REGION = "ad_sync_push_region"
        const val TABLE_AD_SYNC_WATCH_COLUMN_RAW_PARAM = "ad_sync_raw_param"
        const val TABLE_AD_SYNC_WATCH_COLUMN_CATEGORY = "ad_sync_category"
        const val TABLE_AD_SYNC_WATCH_COLUMN_SHOW = "ad_sync_show"
        const val TABLE_AD_SYNC_WATCH_COLUMN_PUSH_ENABLE = "ad_sync_push_enable"
        const val TABLE_AD_SYNC_WATCH_COLUMN_CREATED_TIME = "ad_sync_created_time"
        const val TABLE_AD_EVENT = "ad_event"
        const val TABLE_AD_EVENT_COLUMN_ID = "_id"
        const val TABLE_AD_EVENT_COLUMN_AD_ID = "ad_id"
        const val TABLE_AD_EVENT_COLUMN_AD_LIST_ID = "ad_listId"
        const val TABLE_AD_EVENT_COLUMN_ACCOUNT_ID = "account_id"
        const val TABLE_AD_EVENT_COLUMN_AD_STATUS = "ad_status"
        const val TABLE_AD_EVENT_COLUMN_MESSAGE = "message"

        private var db: CoreDBChototDatabaseHelper? = null

        @Synchronized
        fun getInstance(application: Application): CoreDBChototDatabaseHelper {
            if (db == null) {
                db = CoreDBChototDatabaseHelper(application)
            }
            return db!!
        }
    }
}