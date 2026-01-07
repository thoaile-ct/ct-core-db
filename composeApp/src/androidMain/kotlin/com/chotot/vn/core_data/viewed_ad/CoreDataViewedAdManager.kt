package com.chotot.vn.core_data.viewed_ad

import android.util.LruCache
import com.chotot.vn.utils.CoreDBSharedPreferenceHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

interface CoreDataViewedAdManager : List<Long> {

    fun init()

    fun markViewedAd(listId: Long)

    fun getCacheFlow(): Flow<List<Long>>

    class Simple(
        private val sharedPreferenceHelper: CoreDBSharedPreferenceHelper,
    ) : CoreDataViewedAdManager {

        val cache: LruCache<Long, String> = LruCache(500)

        private val cacheFlow: MutableStateFlow<List<Long>> = MutableStateFlow(emptyList())

        override fun init() {
            val cacheList = sharedPreferenceHelper.getStringList("viewed_ad", false)
                .orEmpty()
                .map { it.toLong() }

            cacheList.forEach { listId -> cache.put(listId, "") }
            cacheFlow.update { cache.snapshot().keys.toList() }
        }

        override fun markViewedAd(listId: Long) {
            cache.put(listId, "")
            sharedPreferenceHelper.putStringList("viewed_ad", cache.snapshot().keys.map { it.toString() }.toList())
            cacheFlow.update { cache.snapshot().keys.toList() }
        }


        override fun getCacheFlow(): Flow<List<Long>> {
            return cacheFlow
        }

        override val size: Int
            get() = cache.size()

        override fun contains(element: Long): Boolean {
            return cache.get(element) != null
        }

        override fun containsAll(elements: Collection<Long>) = false

        override fun get(index: Int): Long {
            return cache.snapshot().keys.toList()[index]
        }

        override fun indexOf(element: Long): Int {
            return cache.snapshot().keys.indexOf(element)
        }

        override fun isEmpty(): Boolean {
            return cache.size() == 0
        }

        override fun iterator(): Iterator<Long> {
            return cache.snapshot().keys.iterator()
        }

        override fun lastIndexOf(element: Long): Int {
            return cache.snapshot().keys.lastIndexOf(element)
        }

        override fun listIterator(): ListIterator<Long> {
            return cache.snapshot().keys.toList().listIterator()
        }

        override fun listIterator(index: Int): ListIterator<Long> {
            return cache.snapshot().keys.toList().listIterator(index)
        }

        override fun subList(fromIndex: Int, toIndex: Int): List<Long> {
            return cache.snapshot().keys.toList().subList(fromIndex, toIndex)
        }
    }
}