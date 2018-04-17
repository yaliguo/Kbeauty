package phoenix.com.kbeauty.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.bumptech.glide.Glide;


import java.util.ArrayList;
import java.util.List;

import phoenix.com.kbeauty.R;
import phoenix.com.kbeauty.utils.DensityUtil;

public class SimpleAdapter extends BaseRecyclerAdapter<SimpleAdapter.SimpleAdapterViewHolder> {
    private List<FuliBean.FuliInfo> list = new ArrayList<>();
    private int largeCardHeight, smallCardHeight;
    private Context context;

    public SimpleAdapter( Context context) {
        largeCardHeight = DensityUtil.dip2px(context, 150);
        smallCardHeight = DensityUtil.dip2px(context, 100);
        this.context = context;
    }

    public void insertAll(List<FuliBean.FuliInfo> list){
        //
    }

    @Override
    public void onBindViewHolder(SimpleAdapterViewHolder holder, int position, boolean isItem) {
       FuliBean.FuliInfo fuli = list.get(position);
        //Todo 图片加载框架未处理
        Glide.with(context)
                .load(fuli.getUrl())

               // .placeholder(placeholder)
               // .error(error)
               // .override(width, height)
                .into(holder.nameTv);
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            holder.rootView.getLayoutParams().height
                    = position % 2 != 0 ? largeCardHeight : smallCardHeight;
        }
    }

    @Override
    public int getAdapterItemViewType(int position) {
        return 0;
    }

    @Override
    public int getAdapterItemCount() {
        return list.size();
    }

    @Override
    public SimpleAdapterViewHolder getViewHolder(View view) {
        return new SimpleAdapterViewHolder(view, false);
    }

    public void setData(List<FuliBean.FuliInfo> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public SimpleAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_recylerview, parent, false);
        SimpleAdapterViewHolder vh = new SimpleAdapterViewHolder(v, true);
        return vh;
    }

    public void insert(FuliBean.FuliInfo person, int position) {
        insert(list, person, position);
    }

    public void remove(int position) {
        remove(list, position);
    }

    public void clear() {
        clear(list);
    }

    public class SimpleAdapterViewHolder extends RecyclerView.ViewHolder {

        public View rootView;
        public ImageView nameTv;
        public int position;

        public SimpleAdapterViewHolder(View itemView, boolean isItem) {
            super(itemView);
            if (isItem) {
                nameTv = (ImageView) itemView
                        .findViewById(R.id.rc_item_img);
                rootView = itemView
                        .findViewById(R.id.card_view);
            }

        }
    }

    public FuliBean.FuliInfo getItem(int position) {
        if (position < list.size())
            return list.get(position);
        else
            return null;
    }

}