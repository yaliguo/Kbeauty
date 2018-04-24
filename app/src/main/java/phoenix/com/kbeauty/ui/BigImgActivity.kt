package phoenix.com.kbeauty.ui

import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import phoenix.com.kbeauty.GlideApp
import phoenix.com.kbeauty.R
import phoenix.com.kbeauty.base.Apis.Companion.fuli
import phoenix.com.kbeauty.base.BaseActivity

/**
 * Created by guoyali
 *
 *     on 2018/4/24.
 *
 *  use
 */
class BigImgActivity : BaseActivity<BigImgView, BigImgPresenter>(),BigImgView {
    override fun initThis() {
        var img : ImageView =  findViewById(R.id.act_big_img)
      var url =  intent.getStringExtra("url")
        GlideApp.with(this@BigImgActivity)
                .load(url)
                .placeholder(R.mipmap.placeholder_banner)
                .into(img)

    }

    override fun getLayout(): Int {
        return R.layout.activity_bigimg
    }

    override fun createPresenter(): BigImgPresenter {
        return   BigImgPresenter()
    }

    override fun getMvpView(): BigImgView {
        return  this@BigImgActivity
    }
}