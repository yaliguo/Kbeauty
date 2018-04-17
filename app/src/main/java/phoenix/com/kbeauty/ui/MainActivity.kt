package phoenix.com.kbeauty.ui

import android.os.Handler
import android.support.v7.widget.StaggeredGridLayoutManager
import com.andview.refreshview.XRefreshView
import phoenix.com.kbeauty.R
import phoenix.com.kbeauty.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainView,MainPresenter>(),MainView {


    var adapter : SimpleAdapter  ?= null
    var index = 1
    override fun createPresenter(): MainPresenter {
        return MainPresenter()
    }

    override fun getMvpView(): MainView {
        return  this
    }



    override fun flushUi(fuliBean: FuliBean) {
        if(index ==1){
            adapter!!.clear()
            adapter!!.setData(fuliBean.results)
            act_main_xrefresh.stopRefresh()
        }else{
           for (item in fuliBean.results){
               adapter!!.insert(item, adapter!!.adapterItemCount)
           }
            act_main_xrefresh.stopLoadMore()
        }

    }
    override fun initThis() {

        adapter   =  SimpleAdapter(this)

        //init recycle view
        act_main_rv.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        act_main_rv.adapter =adapter

        //init adapter
        adapter!!.customLoadMoreView = XRefreshViewFooter(this)

        //init rv
        act_main_xrefresh.setPinnedTime(1000)
        act_main_xrefresh.setMoveForHorizontal(true)
        act_main_xrefresh.pullLoadEnable=true


        act_main_xrefresh.setXRefreshViewListener(object : XRefreshView.SimpleXRefreshListener() {
            override fun onLoadMore(isSilence: Boolean) {
                getdata(index+1)
                index++

            }

            override fun onRefresh(isPullDown: Boolean) {
                index=1
                getdata(index)

            }
        })


        getdata(1)
    }







    private fun getdata(index : Int) {

       mPresenter!!.getFulis(index)

    }

    override fun getLayout(): Int {
        return R.layout.activity_main
    }


}

