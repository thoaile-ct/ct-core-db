package com.chotot.vn.sd.data.local.room

/**
 * Copyright (c) 2023 ChoTot. All rights reserved.
 * Created by NoOne on 24/08/2022.
 */

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chotot.vn.base.CoreDBBaseDao
import com.chotot.vn.core_data.CoreDBQuickMenus
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface CoreDBQuickMenuDao : CoreDBBaseDao<CoreDBQuickMenus> {

    @Query("SELECT * FROM table_quick_menus")
    fun getAllQuickMenus(): Single<CoreDBQuickMenus>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuickMenus(data: CoreDBQuickMenus): Completable

    @Query("DELETE FROM table_quick_menus")
    fun deleteAllQuickMenus(): Completable
}