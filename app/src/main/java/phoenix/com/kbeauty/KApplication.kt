package phoenix.com.kbeauty

import android.app.Application

/**
 * Created by guoyali
 *
 *     on 2018/4/17.
 *
 *  use
 */
class KApplication : Application() {
    companion object {
        private var instance : Application? =null
        fun instance() = instance!!

    }

    override fun onCreate() {
        super.onCreate()

        instance=this

    }


}