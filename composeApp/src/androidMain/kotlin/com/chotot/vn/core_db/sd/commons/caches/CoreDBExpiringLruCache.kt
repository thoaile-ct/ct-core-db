package com.chotot.vn.sd.commons.caches

import android.os.SystemClock
import android.util.LruCache

/**
 * An Lru Cache that allows entries to expire after
 * a period of time. Items are evicted based on a combination
 * of time, and usage. Adding items past the {@code maxSize}
 * will evict entries regardless of expiry. Items are also evicted
 * upon attempted retrieval via {@link #get(Object)} if they are
 * expired.
 *
 * Time is based on elapsed real time since device boot,
 * including device sleep time.
 *
 * @param <K> Key
 * @param <V> Value
 *
 */
class CoreDBExpiringLruCache<K, V>(val maxSize: Int, val expireTime: Long) {

    private var cache = LruCache<K, V>(maxSize)
    private var expirationTimes: MutableMap<K, Long> = HashMap(maxSize)

    @Synchronized
    operator fun get(key: K): V? {
        val value: V = cache.get(key)
        if (value != null && elapsedRealtime() >= getExpiryTime(key)) {
            remove(key)
            return null
        }
        return value
    }

    @Synchronized
    fun put(key: K, value: V): V {
        val oldValue: V = cache.put(key, value)
        expirationTimes[key] = elapsedRealtime() + expireTime
        return oldValue
    }

    fun elapsedRealtime(): Long {
        return SystemClock.elapsedRealtime()
    }

    fun getExpiryTime(key: K): Long {
        return expirationTimes[key] ?: return 0
    }

    fun removeExpiryTime(key: K) {
        expirationTimes.remove(key)
    }

    fun remove(key: K): V {
        return cache.remove(key)
    }

    fun snapshot(): Map<K, V> {
        return cache.snapshot()
    }

    fun trimToSize(maxSize: Int) {
        cache.trimToSize(maxSize)
    }

    fun createCount(): Int {
        return cache.createCount()
    }

    fun evictAll() {
        cache.evictAll()
    }

    fun evictionCount(): Int {
        return cache.evictionCount()
    }

    fun hitCount(): Int {
        return cache.hitCount()
    }

    fun maxSize(): Int {
        return cache.maxSize()
    }

    fun missCount(): Int {
        return cache.missCount()
    }

    fun putCount(): Int {
        return cache.putCount()
    }

    fun size(): Int {
        return cache.size()
    }

    /**
     * Extended the LruCache to override the [.entryRemoved] method
     * so we can remove expiration time entries when things are evicted from the cache.
     *
     * Gotta love some of those Google engineers making things difficult with paranoid
     * usage of the `final` keyword.
     */
    inner class MyLruCache(maxSize: Int) : LruCache<K, V>(maxSize) {

        override fun entryRemoved(evicted: Boolean, key: K, oldValue: V, newValue: V) {
            this@CoreDBExpiringLruCache.removeExpiryTime(key) // dirty hack
        }

        override fun sizeOf(key: K, value: V): Int {
            return this@CoreDBExpiringLruCache.sizeOf(key, value)
        }
    }

    /**
     * Returns the size of the entry for `key` and `value` in
     * user-defined units.  The default implementation returns 1 so that size
     * is the number of entries and max size is the maximum number of entries.
     *
     *
     * An entry's size must not change while it is in the cache.
     */
    fun sizeOf(key: K, value: V): Int {
        return 1
    }
}