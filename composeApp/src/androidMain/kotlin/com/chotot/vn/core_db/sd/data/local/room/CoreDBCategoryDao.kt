package com.chotot.vn.sd.data.local.room

/**
 * Copyright (c) 2023 ChoTot. All rights reserved.
 * Created by NoOne on 29/08/2022.
 */

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chotot.vn.base.CoreDBBaseDao
import com.chotot.vn.core_data.CoreDBCategories
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface CoreDBCategoryDao : CoreDBBaseDao<CoreDBCategories> {

    @Query("SELECT * FROM table_categories")
    fun getAllCategories(): Single<CoreDBCategories>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategories(data: CoreDBCategories): Completable

    @Query("DELETE FROM table_categories")
    fun deleteAllCategories(): Completable
}