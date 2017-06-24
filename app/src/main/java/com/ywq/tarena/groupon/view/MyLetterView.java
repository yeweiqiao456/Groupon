package com.ywq.tarena.groupon.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * 自定义View
 * 进行城市分组的快速导航
 *
 * Created by tarena on 2017/6/23.
 */

public class MyLetterView extends View {

    private String[] letters = {
            "热门","A","B","C","D","E",
            "F","G","H","I","J",
            "K","L","M","N","O",
            "P","Q","R","S","T",
            "U","V","W","X","Y","Z"
    };

    Paint paint;

    OnTouchLetterListener listener;

    public MyLetterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public void setOnTouchLetterListener(OnTouchLetterListener listener){
        this.listener = listener;
    }

    /**
     * 画笔的初始化
     */
    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //14sp在当前设备上所对应的像素的大小
        float size = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,14,getResources().getDisplayMetrics());
        //用来定义使用这根画笔绘制出来的文字大小
        paint.setTextSize(size);
    }

    /**
     * 用来设定MyLetterView尺寸(宽高)
     *
     * 并不一定要重写
     * View的onMeasure方法已经有了很多的设定尺寸的代码，可以使用
     * 只有当View的onMeasure设定尺寸的代码逻辑不能满足实际要求时才有必要进行"改写"
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 一定要重写
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();//MyLetterView的宽度
        int height = getHeight();//MyLetterView的高度

        for (int i = 0;i < letters.length;i++){
            String letter = letters[i];
            //获取文字的边界大小
            Rect bounds = new Rect();
            paint.getTextBounds(letter,0,letter.length(),bounds);
            //bounds.width() / bounds.height()
            float x = width/2 - bounds.width()/2;
            float y = height/letters.length + bounds.height()/2 + i*height/letters.length;
            canvas.drawText(letter,x,y,paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //按下，移动，还是抬起
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_DOWN:
                //改变背景
                setBackgroundColor(Color.GRAY);


                break;
            default:
                setBackgroundColor(Color.TRANSPARENT);
                break;
        }

        return true;
    }

    public interface OnTouchLetterListener{
        void onTouchLetter(String letter);
    }

}
