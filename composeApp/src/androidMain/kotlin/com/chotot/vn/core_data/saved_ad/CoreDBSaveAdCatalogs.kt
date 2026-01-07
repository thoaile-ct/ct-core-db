/*
 * Copyright (c) 2021 Chotot Ltd Company. All rights reserved.
 * Created by trachlai@chotot.vn
 */

package com.chotot.vn.core_data.saved_ad

import com.chotot.vn.core_data.adlisting.CoreDataAdModel
import com.chotot.vn.core_data.homepage.CoreDataHeader

data class SaveAdCatalogs(val items: List<CatalogItem>)

data class CatalogItem(
    val itemType: Type = Type.NONE,
    val header: CoreDataHeader? = null,
    val bookmark: CoreDBBookmark? = null,
    val personalizedAd: CoreDataAdModel? = null,
) {

    class Builder {

        private var personalizedAd: CoreDataAdModel? = null
        private var bookmark: CoreDBBookmark? = null
        private var header: CoreDataHeader? = null

        fun bookmark(bookmark: CoreDBBookmark?) = apply { this.bookmark = bookmark }
        fun personalizedAd(personalizedAd: CoreDataAdModel?) = apply { this.personalizedAd = personalizedAd }
        fun header(header: CoreDataHeader) = apply { this.header = header }

        fun build(type: Type): CatalogItem {
            if (type == Type.NONE) throw IllegalArgumentException("we do not support this type")
            val catalogItem = when (type) {
                Type.HEADER -> header?.let { CatalogItem(itemType = type, header = it) }
                Type.SAVED_AD -> bookmark?.let { CatalogItem(itemType = Type.SAVED_AD, bookmark = it) }
                Type.PERSONALIZED_ADS -> personalizedAd?.let { CatalogItem(itemType = Type.PERSONALIZED_ADS, personalizedAd = it) }
                Type.FOOTER -> CatalogItem(itemType = Type.FOOTER)
                Type.SAVE_AD_EMPTY -> CatalogItem(itemType = Type.SAVE_AD_EMPTY)
                else -> CatalogItem()
            }
            return catalogItem ?: CatalogItem()
        }
    }
}

enum class Type {
    HEADER,
    SAVED_AD,
    PERSONALIZED_ADS,
    SAVE_AD_EMPTY,
    FOOTER,
    PROGRESS,
    NONE;

    companion object {

        fun ordinalOf(ordinal: Int): Type? {
            return values().firstOrNull { it.ordinal == ordinal }
        }
    }
}
