package com.example.alumno.proytastyburger.Image;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Alumno-CT on 28/06/2018.
 */

public class ResizableImageView extends ImageView{


    public ResizableImageView(Context context) {
        super(context);
    }

    public ResizableImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ResizableImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ResizableImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable d= getDrawable();
        if(d==null){
            super.setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
            return;
        }

        int imageHeight = d.getIntrinsicHeight();
        int imageWidth = d.getIntrinsicWidth();

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        float imageRatio = 0.0F;
        if(imageHeight>0){
            imageRatio = imageWidth/imageHeight;
        }
        float sizeRatio =0.0F;
        if(heightSize>0){
            sizeRatio = widthSize/heightSize;
        }

        int width;
        int height;

        if(imageRatio>=sizeRatio){
            width=widthSize;
            height=width*imageHeight/imageWidth;
        }else{
            height=heightSize;
            width=height*imageWidth/imageHeight;
        }

        setMeasuredDimension(width,height);
    }
}
