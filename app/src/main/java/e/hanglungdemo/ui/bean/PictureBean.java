package e.hanglungdemo.ui.bean;

import android.graphics.Bitmap;

public class PictureBean {
    Bitmap bitmap;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public PictureBean setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        return this;
    }
}
