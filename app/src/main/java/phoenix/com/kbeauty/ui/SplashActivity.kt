package phoenix.com.kbeauty.ui

import android.content.Intent
import android.util.Log
import io.reactivex.Observable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_splash.*
import org.reactivestreams.Subscriber
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
       Observable.interval(1000/100,TimeUnit.MILLISECONDS)
               .map { num ->   act_splash_pg.progress= num.toInt() }
               .take(100)
               .subscribe( { i -> Log.e(SplashActivity@this.localClassName,i.toString()) },  {  },  {
                   startActivity(Intent(SplashActivity@this,MainActivity::class.java))
                    finish()
               })


    }



}