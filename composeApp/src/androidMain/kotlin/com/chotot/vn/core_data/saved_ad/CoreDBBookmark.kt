package com.chotot.vn.core_data.saved_ad

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.chotot.vn.utils.CoreDBConstant

@Entity(tableName = CoreDBConstant.TABLE_BOOKMARK)
data class CoreDBBookmark(

    @ColumnInfo(name = CoreDBConstant.TABLE_BOOKMARK_COLUMN_AD_ID)
    var adId: Long? = 0,

    @ColumnInfo(name = CoreDBConstant.TABLE_BOOKMARK_COLUMN_AD_SUBJECT)
    var adSubject: String? = null,

    @ColumnInfo(name = CoreDBConstant.TABLE_BOOKMARK_COLUMN_AD_PRICE)
    var adPrice: String? = null,

    @ColumnInfo(name = CoreDBConstant.TABLE_BOOKMARK_COLUMN_AD_TIME)
    var adTime: String? = null,

    @ColumnInfo(name = CoreDBConstant.TABLE_BOOKMARK_COLUMN_AD_IMG_URL)
    var adImgUrl: String? = null,

    @ColumnInfo(name = CoreDBConstant.TABLE_BOOKMARK_COLUMN_AD_CATEGORY_ID)
    var adCategoryId: Int? = 0,

    @ColumnInfo(name = CoreDBConstant.TABLE_BOOKMARK_COLUMN_AD_BODY)
    var adBody: String? = null,

    @ColumnInfo(name = CoreDBConstant.TABLE_BOOKMARK_COLUMN_AD_NAME)
    var adName: String? = null,

    @ColumnInfo(name = CoreDBConstant.TABLE_BOOKMARK_COLUMN_AD_REGION)
    var adRegion: String? = null,

    @ColumnInfo(name = CoreDBConstant.TABLE_BOOKMARK_COLUMN_AD_PHONE)
    var adPhone: String? = null,

    @ColumnInfo(name = CoreDBConstant.TABLE_BOOKMARK_COLUMN_CREATED_TIME)
    var adCreateTime: Int = 0,

    @Ignore
    var adPriceOldValue: String? = null,
    @Ignore
    var status: String? = null,
    @Ignore
    var tagAdId: String? = null,
    @Ignore
    var owner: Boolean? = false,
    @Ignore
    var phone: String? = "",
    @Ignore
    var account_id: String? = "",

    @Ignore
    var veh_inspected: Int? = null,
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = CoreDBConstant.TABLE_BOOKMARK_COLUMN_ID)
    var id: Int? = null

    val isTrustedInventories: Boolean
        get() = veh_inspected == CoreDBConstant.VEH_INSPECTED_5_TOT_KEY
                || veh_inspected == CoreDBConstant.VEH_INSPECTED_5_TOT_ODO_KEY
}