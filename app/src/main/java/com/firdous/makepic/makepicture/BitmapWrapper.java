package com.firdous.makepic.makepicture;

import android.graphics.Bitmap;

/**
 * Created by firdous on 12/03/15.
 */
public class BitmapWrapper {
    public Bitmap bitmap;
    public int bitIndex;

    public static BitmapWrapper bitmapFactory(Bitmap bm, int index){
        BitmapWrapper bmWrapper = new BitmapWrapper();
        bmWrapper.bitmap = bm;
        bmWrapper.bitIndex = index;

        return bmWrapper;
    }
}
