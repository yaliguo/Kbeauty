package phoenix.com.kbeauty.ui

import android.content.Intent
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import phoenix.com.kbeauty.R
import phoenix.com.kbeauty.base.BaseActivity
import java.util.concurrent.TimeUnit

/**
 * Created by guoyali
 *
 *     on 2018/4/11.
 *
 *  use
 */
class SplashActivity : BaseActivity<SplashView,SplashPresenter> (){
    override fun createPresenter(): SplashPresenter {

        return  SplashPresenter()
    }

    override fun getMvpView(): SplashView {
        return  SplashView()
    }


    override fun getLayout(): Int {
        return R.layout.activity_splash
    }

    override fun initThis() {
       Observable.interval(1,TimeUnit.SECONDS)
               .take(1)
               .subscribe(Consumer {
                   startActivity(Intent(SplashActivity@this,MainActivity::class.java))
               })


    }



}