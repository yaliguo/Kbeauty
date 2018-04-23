package phoenix.com.kbeauty.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.andview.refreshview.utils.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import phoenix.com.kbeauty.GlideApp;
import phoenix.com.kbeauty.R;
import phoenix.com.kbeauty.utils.DensityUtil;

public class SimpleAdapter extends BaseRecyclerAdapter<SimpleAdapter.SimpleAdapterViewHolder> {
    private List<FuliBean.FuliInfo> list = new ArrayList<>();
    private Context context;
    private final int screenWidth;
    private final int heightPixels;
    public SimpleAdapter( Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        screenWidth = displayMetrics.widthPixels;
        heightPixels = displayMetrics.heightPixels;
        this.context = context;
    }

    public void insertAll(List<FuliBean.FuliInfo> list){
        //
    }

    @Override
    public void onBindViewHolder(final SimpleAdapterViewHolder holder, int position, boolean isItem) {

        //存在记录的高度时先Layout再异步加载图片
        if (list.get(holder.getAdapterPosition()).getHeight() > 0) {
            ViewGroup.LayoutParams layoutParams = holder.img.getLayoutParams();
            layoutParams.height = list.get(holder.getAdapterPosition()).getHeight();
        }
        FuliBean.FuliInfo fuli = list.get(position);
        GlideApp.with(context)
                .load(fuli.getUrl())
                .placeholder(R.mipmap.placeholder_banner)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        if(holder.getAdapterPosition() != RecyclerView.NO_POSITION) {
                            if (list.get(holder.getAdapterPosition()).getHeight() <= 0) {
                                int width = resource.getIntrinsicWidth();
                                int height = resource.getIntrinsicHeight();
                                int realHeight = screenWidth * height / width / 2;
                                list.get(holder.getAdapterPosition()).setHeight(realHeight);
                                ViewGroup.LayoutParams lp = holder.img.getLayoutParams();
                                lp.height = realHeight;
                                if(width < screenWidth / 2)
                                    lp.width = screenWidth / 2;
                            }
                            holder.img.setImageDrawable(resource);
                        }
                    }
                });

        holder.textView.setText(fuli.getDesc());
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
    }

    @Override
    public int getAdapterItemViewType(int position) {
        return Math.round((float) screenWidth / (float) list.get(position).getHeight() * 10f);
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
        public ImageView img;
        public TextView textView;
        public int position;

        public SimpleAdapterViewHolder(View itemView, boolean isItem) {
            super(itemView);
            if (isItem) {
                img =   itemView
                        .findViewById(R.id.rc_item_img);
                rootView = itemView
                        .findViewById(R.id.card_view);
                textView = itemView
                        .findViewById(R.id.rc_item_tv);
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