package phoenix.com.kbeauty.ui

import phoenix.com.kbeauty.base.BaseBean

/**
 * Created by guoyali
 *
 *     on 2018/4/13.
 *
 *  use
 */
 data class FuliBean (val results:List<FuliInfo> ){

 data class FuliInfo(val url : String ?,var desc : String ?,var height : Int)


}

