package phoenix.com.kbeauty.base.mvp

/**
 * Created by guoyali
 *
 *     on 2018/4/11.
 *
 *  use
 */
open class BaseMvpPresenter<V:BaseMvpView> :MvpPresenterInterface<V> {
    var mView :V ?= null
    override   fun linkView(view: V) {
        mView = view

    }


}