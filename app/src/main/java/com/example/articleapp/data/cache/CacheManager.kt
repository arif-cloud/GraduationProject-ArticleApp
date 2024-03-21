package com.example.articleapp.data.cache

import androidx.collection.LruCache
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CacheManager @Inject constructor() {

    private val cache = LruCache<String, Any>(10 * 1024)

    fun <T> getData(key : String) : T? = cache[key] as T?

    fun putData(key : String, data : Any) {
        synchronized(cache) {
            if (cache[key] == null) {
                cache.put(key, data)
            }
        }
    }

    fun updateData(key: String, data: Any) {
        synchronized(cache) {
            cache.put(key, data)
        }
    }

}