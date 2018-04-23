package phoenix.com.kbeauty.utils

import android.content.Context
import com.bumptech.glide.Glide

/**
 * Created by guoyali
 *
 *     on 2018/4/23.
 *
 *  use
 */
class GlideUtils(context :Context){
    var last : String =  "asdf"
    get() = field

    var glide : Glide ? =null

    init {

        glide = Glide.get(context)


    }


    fun with (url : String){


    }


//    fun getGlide  (context :Context) : GlideUtils {
//
//     //   return Inner.single
//
//   }
//

    private object  Inner{


       // val single = GlideUtils()

    }

}