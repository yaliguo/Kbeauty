package phoenix.com.kbeauty.base.mvp

/**
 * Created by guoyali
 *
 *     on 2018/4/11.
 *
 *  use
 */
interface MvpCallBack <V: BaseMvpView,P: MvpPresenterInterface<V>>{

    fun createPresenter():P

    fun setPresenter(presenter: P)

    fun  getMvpView() : V
}