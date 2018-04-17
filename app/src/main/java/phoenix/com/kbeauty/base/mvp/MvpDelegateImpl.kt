package phoenix.com.kbeauty.base.mvp

import android.os.Bundle

/**
 * Created by guoyali
 *
 *     on 2018/4/11.
 *
 *  use
 */
class MvpDelegateImpl<V: BaseMvpView,P: MvpPresenterInterface<V>> constructor(callBack: MvpCallBack<V,P>) : MvpDelegate<V,P>  {
    override fun onCreate(bundle: Bundle?) {
    }


    init {

        var presenter :P =  callBack.createPresenter()
        callBack.setPresenter(presenter)

        var  view  : V =  callBack.getMvpView()

        presenter.linkView(view)

    }




    override fun onResume() {
    }

    override fun onStart() {
    }

    override fun onPause() {
    }

    override fun onStop() {
    }

    override fun onReStart() {
    }

    override fun onRestart() {
    }
}