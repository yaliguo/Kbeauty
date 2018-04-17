package phoenix.com.kbeauty.base.mvp

import android.os.Bundle


/**
 * Created by guoyali
 *
 *     on 2018/4/11.
 *
 *  use
 */
  interface MvpDelegate<V: BaseMvpView,P: MvpPresenterInterface<V>> {
    fun onCreate(bundle : Bundle?)
    fun onResume()
    fun onStart()
    fun onPause()
    fun onStop()
    fun onReStart()
    fun onRestart()
}