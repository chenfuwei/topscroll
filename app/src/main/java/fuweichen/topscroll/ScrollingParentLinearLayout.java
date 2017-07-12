package fuweichen.topscroll;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by Administrator on 2017/7/11.
 */

public class ScrollingParentLinearLayout extends LinearLayout implements NestedScrollingParent{
    private int nHeigth = 0;
    private int nConsumeHeight = 0;
    private boolean isScrollToBottom = true;
    private boolean isScrollToTop = false;
    private static final String TAG = "ScrollingParent";
    private Scroller mScroller;
    public ScrollingParentLinearLayout(Context context) {
        this(context, null);
    }

    public ScrollingParentLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollingParentLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ScrollingParentLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        boolean bHas = hasNestedScrollingParent();
        boolean bTrue = startNestedScroll(nestedScrollAxes);
        Log.d(TAG, "onStartNestedScroll() called with: nestedScrollAxes = [" + nestedScrollAxes + "bTrue = " + bTrue +
                " bHas = " + bHas + "]");
        return (nestedScrollAxes & SCROLL_AXIS_VERTICAL) != 0 ;
    }


    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
//        super.onNestedPreScroll(target, dx, dy, consumed);

        int[] parentConsumed = new int[2];
        int[] offsetInWindow = new int[2];
        boolean bTrue = dispatchNestedPreScroll(dx, dy, parentConsumed, offsetInWindow);
//        boolean bEnable = isNestedScrollingEnabled();
//        boolean bHas = hasNestedScrollingParent();
//        Log.d(TAG, "onNestedPreScroll() called with:  dx = [" + dx + "], dy = [" + dy + "], " +
//                "consumed = [" + consumed + " bTrue = " + bTrue + " bEnabe = " + bEnable + " bHas = " + bHas + "]");

        dy -= parentConsumed[1];
        int nScrollY = getScrollY();
        if(nScrollY > nHeigth || nScrollY < 0)
        {
            consumed[1] = 0;
        }else if(dy > 0 && nScrollY + dy >= nHeigth)
        {
            int nTmpY = nHeigth - nScrollY;
            consumed[1] = nTmpY;
            scrollBy(0, nTmpY);
        }else if(dy < 0 && nScrollY + dy <= 0)
        {
            int nTmpY = -nScrollY;
            consumed[1] = nTmpY;
            scrollBy(0, nTmpY);
        }else
        {
            consumed[1] = dy;
            scrollBy(0, dy);
        }
//        int nScrollY = getScrollY();
//        if(nScrollY > nHeigth || nScrollY < 0)
//        {
//            consumed[1] = 0;
//        }else if(dy > 0 && nScrollY + dy >= nHeigth)
//        {
//            int nTmpY = nHeigth - nScrollY;
//            consumed[1] = nTmpY;
//            scrollBy(0, nTmpY);
//        }else if(dy < 0 && nScrollY + dy <= 0)
//        {
//            int nTmpY = -nScrollY;
//            consumed[1] = nTmpY;
//            scrollBy(0, nTmpY);
//        }else
//        {
//            consumed[1] = dy;
//            scrollBy(0, dy);
//        }
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
       // super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, null);
        Log.d(TAG, "onNestedScroll() called with:  dxConsumed = [" + dxConsumed + "], dyConsumed = [" + dyConsumed + "], dxUnconsumed = [" + dxUnconsumed + "], dyUnconsumed = [" + dyUnconsumed + "]");
    }

    @Override
    public void onStopNestedScroll(View child) {
        super.onStopNestedScroll(child);
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        //return super.onNestedPreFling(target, velocityX, velocityY);
        Log.d(TAG, "onNestedPreFling() called with: velocityX = [" + velocityX + "], velocityY = [" + velocityY + "]");
        if (getScrollY() >= nHeigth) return false;
        fling((int) velocityY);
        return true;
    }

    public void fling(int velocityY)
    {
        mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, nHeigth);
        invalidate();
    }

    @Override
    public void scrollTo(int x, int y)
    {
        if (y < 0)
        {
            y = 0;
        }
        if (y > nHeigth)
        {
            y = nHeigth;
        }
        if (y != getScrollY())
        {
            super.scrollTo(x, y);
        }
    }

    private void init(Context context)
    {
        nHeigth = context.getResources().getDimensionPixelSize(R.dimen.scroll_height) / 2;
        mScroller = new Scroller(context);
    }
}
