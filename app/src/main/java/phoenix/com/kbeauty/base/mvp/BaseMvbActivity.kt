package phoenix.com.kbeauty.base.mvp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by guoyali
 *
 *     on 2018/4/11.
 *
 *  use
 */
 abstract class BaseMvbActivity <V: BaseMvpView,P: MvpPresenterInterface<V>> : AppCompatActivity(),MvpCallBack <V,P>{
   open var mPresenter :P  ?= null
    var mMvpDelegate : MvpDelegate<V,P>
    init {
        mMvpDelegate= getMyDelegate()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMvpDelegate.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        mMvpDelegate.onResume()

    }

    override fun onStart() {
        super.onStart()
        mMvpDelegate.onRestart()
    }

    override fun onPause() {
        super.onPause()
        mMvpDelegate.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMvpDelegate.onReStart()
    }


    override fun onRestart() {
        super.onRestart()
        mMvpDelegate.onReStart()
    }


    override fun setPresenter(presenter: P) {
        mPresenter = presenter
    }

    open fun getMyDelegate(): MvpDelegate<V,P> {
            return  MvpDelegateImpl(this)

    }

}