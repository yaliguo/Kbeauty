package phoenix.com.kbeauty

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.module.AppGlideModule

/**
 * Created by guoyali
 *
 *     on 2018/4/23.
 *
 *  use
 */

@GlideModule class MyGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.setDiskCache (InternalCacheDiskCacheFactory(context,50 * 1024 * 1024))
        builder.setMemoryCache(LruResourceCache( 20 * 1024 * 1024))
    }
}