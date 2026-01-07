package com.chotot.vn.core_data.saved_ad

data class CoreDBBookmarks(
    val limit: Int,
    val bookmarks: List<CoreDBBookmark>,
)