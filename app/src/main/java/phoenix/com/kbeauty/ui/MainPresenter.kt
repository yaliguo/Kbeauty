package phoenix.com.kbeauty.ui

import android.widget.Toast
import com.alibaba.fastjson.JSON
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import okhttp3.ResponseBody
import phoenix.com.kbeauty.base.Apis
import phoenix.com.kbeauty.base.BaseBean
import phoenix.com.kbeauty.base.mvp.BaseMvpPresenter
import phoenix.com.kbeauty.net.LogUtil
import phoenix.com.kbeauty.net.NetClient

/**
 * Created by guoyali
 *
 *     on 2018/4/13.
 *
 *  use
 */
open class MainPresenter : BaseMvpPresenter<MainView> (){
    //todo 修改请求方式REST
    open fun getFulis( index : Int) {
     NetClient.getInstance().getJsonBody(Apis.fuli+"/"+"10/"+index,HashMap<String,String>())
             .map { res -> res.string() }
             .map { str -> JSON.parseObject(str,FuliBean::class.java) }
             .subscribe( { bean -> mView?.flushUi(bean)  },  {t ->  LogUtil.e("发生一个错误")  })

    }

}