package com.chotot.vn.sd.data.local.room

/**
 * Copyright (c) 2023 ChoTot. All rights reserved.
 * Created by NoOne on 23/08/2022.
 */

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chotot.vn.base.CoreDBBaseDao
import com.chotot.vn.core_data.CoreDBBanners
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface CoreDBBannerDao : CoreDBBaseDao<CoreDBBanners> {

    @Query("SELECT * FROM table_banners")
    fun getAllBanners(): Single<CoreDBBanners>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBanners(data: CoreDBBanners): Completable

    @Query("DELETE FROM table_banners")
    fun deleteAllBanners(): Completable
}