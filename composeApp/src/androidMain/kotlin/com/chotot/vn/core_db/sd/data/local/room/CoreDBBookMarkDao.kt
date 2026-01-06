package com.chotot.vn.sd.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chotot.vn.core_data.saved_ad.CoreDBBookmark
import io.reactivex.Completable
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

/*
 * Copyright (c) 2019 Chotot Ltd Company. All rights reserved.
 * Created by trachlai@chotot.vn
 */
@Dao
interface CoreDBBookMarkDao {

    @Query("select * from ${CoreDBConstants.TABLE_BOOKMARK} order by ${CoreDBConstants.TABLE_BOOKMARK_COLUMN_ID} desc")
    fun getAllBookmark(): Single<List<CoreDBBookmark>>

    @Query("select * from ${CoreDBConstants.TABLE_BOOKMARK} order by ${CoreDBConstants.TABLE_BOOKMARK_COLUMN_ID} desc")
    fun getAllBookmarkAsync(): Flow<List<CoreDBBookmark>>

    @Query("select ${CoreDBConstants.TABLE_BOOKMARK_COLUMN_AD_ID} from ${CoreDBConstants.TABLE_BOOKMARK} order by ${CoreDBConstants.TABLE_BOOKMARK_COLUMN_ID} desc")
    fun getAllBookmarkId(): Single<List<Long>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(bookmark: CoreDBBookmark): Single<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsync(bookmark: CoreDBBookmark): Long

    @Query("delete from ${CoreDBConstants.TABLE_BOOKMARK}")
    fun deleteAllBookmark(): Completable

    @Delete
    fun delete(bookmark: CoreDBBookmark): Completable

    @Query("delete from ${CoreDBConstants.TABLE_BOOKMARK} where ${CoreDBConstants.TABLE_BOOKMARK_COLUMN_AD_ID} = :adId")
    suspend fun deleteAsync(adId: Long): Int

    @Query("delete from ${CoreDBConstants.TABLE_BOOKMARK} where ${CoreDBConstants.TABLE_BOOKMARK_COLUMN_AD_ID} = :adId")
    fun delete(adId: Long): Completable

    @Query("select count(*) from ${CoreDBConstants.TABLE_BOOKMARK} where ${CoreDBConstants.TABLE_BOOKMARK_COLUMN_AD_ID} = :adId")
    fun getSavedAdCount(adId: Long): Single<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(bookmarks: List<CoreDBBookmark>): Completable
}