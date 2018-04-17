package phoenix.com.kbeauty.base.mvp

/**
 * Created by guoyali
 *
 *     on 2018/4/13.
 *
 *  use
 */
interface MvpPresenterInterface<V:BaseMvpView> {

    fun linkView(view :V)
}