package com.chotot.vn.sd.data.local.room

import androidx.annotation.VisibleForTesting
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.chotot.vn.base.CoreDBConverters
import com.chotot.vn.core_data.CoreDBBanners
import com.chotot.vn.core_data.CoreDBCategories
import com.chotot.vn.core_data.CoreDBQuickMenus
import com.chotot.vn.core_data.saved_ad.CoreDBBookmark

/*
 * Copyright (c) 2019 Chotot Ltd Company. All rights reserved.
 * Created by trachlai@chotot.vn
 */

@Database(
    entities = [
        CoreDBBookmark::class,

        CoreDBBanners::class,
        CoreDBQuickMenus::class,

        CoreDBCategories::class
    ],
    version = CoreDBConstants.DATABASE_VERSION
)
@TypeConverters(CoreDBConverters::class)
abstract class CoreDBRoomDatabase : RoomDatabase() {

    abstract fun bookmarkDao(): CoreDBBookMarkDao
    abstract fun bannerDao(): CoreDBBannerDao
    abstract fun quickMenuDao(): CoreDBQuickMenuDao

    abstract fun categoryDao(): CoreDBCategoryDao

    companion object {

        /**
         * Migrate from:
         * version 1 - using the SQLiteDatabase API
         * to
         * version 2 - using Room
         */
        @VisibleForTesting
        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Room uses an own database hash to uniquely identify the database
                // Since version 1 does not use Room, it doesn't have the database hash associated.
                // By implementing a Migration class, we're telling Room that it should use the data
                // from version 1 to version 2.
                // If no migration is provided, then the tables will be dropped and recreated.
                // Since we didn't alter the table, there's nothing else to do here.
            }
        }

        /**
         * Migrate from:
         * version 2 - using Room
         * to
         * version 3 - using Room
         */
        @VisibleForTesting
        val MIGRATION_2_3: Migration = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
            }
        }

        /**
         * Migrate from:
         * version 3 - using Room
         * to
         * version 4 - using Room where the [CoreDBBookmark] drop some fields:
         */
        @VisibleForTesting
        val MIGRATION_3_4: Migration = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
            }
        }

        /**
         * Migrate from:
         * version 1 - using Room
         * to
         * version 4 - using Room where the [CoreDBBookmark] drop some fields:
         */
        @VisibleForTesting
        val MIGRATION_1_4: Migration = object : Migration(1, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
            }
        }

        /**
         * Migrate from:
         * version 1 - using Room
         * to
         * version 5 - add new [CoreDBBanners, CoreDBQuickMenus, CoreDBCategories, CoreDBHeader]
         */
        @VisibleForTesting
        val MIGRATION_1_5: Migration = object : Migration(1, 5) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // CoreDBBanners
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS `banners` (" +
                            "`status` TEXT NOT NULL, " +
                            "`bannerList` TEXT," +
                            "PRIMARY KEY (`status`))"
                )

                // CoreDBQuickMenus
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS `quick_menus` (" +
                            "`status` TEXT NOT NULL, " +
                            "`menusList` TEXT," +
                            "`label` TEXT," +
                            "`description` TEXT," +
                            "PRIMARY KEY (`status`))"
                )

                // CoreDBCategories
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS `categories` (" +
                            "`code` TEXT NOT NULL, " +
                            "`categories` TEXT," +
                            "PRIMARY KEY (`code`))"
                )

                // CoreDBHeader
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS `header` (" +
                            "`title` TEXT NOT NULL, " +
                            "`background` TEXT," +
                            "PRIMARY KEY (`title`))"
                )
            }
        }

        /**
         * Migrate from:
         * version 1 - using Room
         * to
         * version 6 -
         * remove old table "header"
         * add new [CoreDBHeaderOfCategories, CoreDBHeaderOfAdCollections, CoreDBAdCollection]
         */
        @VisibleForTesting
        val MIGRATION_1_6: Migration = object : Migration(1, 6) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Remove old "header" table
                database.execSQL("DROP TABLE IF EXISTS `header`")

                // CoreDBHeaderOfCategories
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS `header_of_categories` (" +
                            "`title` TEXT NOT NULL, " +
                            "`background` TEXT," +
                            "PRIMARY KEY (`title`))"
                )

                // CoreDBHeaderOfAdCollections
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS `header_of_ad_collections` (" +
                            "`title` TEXT NOT NULL, " +
                            "`background` TEXT," +
                            "PRIMARY KEY (`title`))"
                )

                // CoreDBAdCollection
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS `ad_collections` (" +
                            "`status` TEXT NOT NULL, " +
                            "`menusList` TEXT," +
                            "`label` TEXT," +
                            "`description` TEXT," +
                            "PRIMARY KEY (`status`))"
                )
            }
        }

        /**
         * Migrate from:
         * version 1 - using Room
         * to
         * version 7 -
         * remove all old tables and re-add new table
         */
        @VisibleForTesting
        val MIGRATION_1_7: Migration = object : Migration(1, 7) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("DROP TABLE IF EXISTS `banners`")
                database.execSQL("DROP TABLE IF EXISTS `quick_menus`")

                database.execSQL("DROP TABLE IF EXISTS `header_of_categories`")
                database.execSQL("DROP TABLE IF EXISTS `categories`")

                database.execSQL("DROP TABLE IF EXISTS `header_of_ad_collections`")
                database.execSQL("DROP TABLE IF EXISTS `ad_collections`")
            }
        }
    }
}