package phoenix.com.kbeauty.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by guoyali
 * <p>
 * on 2018/4/16.
 * <p>
 * use
 */

public class ScalImgView extends ImageView {
    private int originalWidth  =100;
    private int originalHeight =100;

    public ScalImgView(Context context) {
        super(context);
    }

    public ScalImgView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScalImgView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (originalWidth > 0 && originalHeight > 0) {
            float ratio = (float) originalWidth / (float) originalHeight;

            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = MeasureSpec.getSize(heightMeasureSpec);
            // TODO: 现在只支持固定宽度
            if (width > 0) {
                height = (int) ((float) width / ratio);
            }

            setMeasuredDimension(width, height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
