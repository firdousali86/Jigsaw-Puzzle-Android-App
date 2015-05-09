package com.firdous.makepic.makepicture;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by firdous on 11/03/15.
 */
public class GridCell extends ImageView {
    public BitmapWrapper bitmapWrapper;
    public int lineIndex;

    public GridCell(Context context) {
        super(context);
    }

    public GridCell(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridCell(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public GridCell(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setImageWrapperObject(BitmapWrapper bmWrapper){
        bitmapWrapper = bmWrapper;

        if (bitmapWrapper != null){
            setImageBitmap(bitmapWrapper.bitmap);
        }
        else {
            setImageBitmap(null);
        }
    }
}
