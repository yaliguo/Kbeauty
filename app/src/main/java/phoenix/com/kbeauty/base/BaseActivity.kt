package phoenix.com.kbeauty.base

import android.os.Bundle
import phoenix.com.kbeauty.base.mvp.BaseMvbActivity
import phoenix.com.kbeauty.base.mvp.BaseMvpPresenter
import phoenix.com.kbeauty.base.mvp.BaseMvpView

/**
 * Created by guoyali
 *
 *     on 2018/4/11.
 *
 *  use
 */
abstract class BaseActivity <V: BaseMvpView,P: BaseMvpPresenter<V>> : BaseMvbActivity<V, P>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        initThis()
    }

    abstract fun initThis()

    abstract fun getLayout(): Int

}