package fuweichen.topscroll;

import android.graphics.Color;
import android.graphics.drawable.RippleDrawable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import static fuweichen.topscroll.R.id.v_collapse_mask;
import static fuweichen.topscroll.R.id.v_expand_mask;
import static fuweichen.topscroll.R.id.v_pay_mask;

public class TopScrollActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener{
    private static final String TAG = "TopScrollActivity";
    private AppBarLayout mAppBarLayout;

    private View expandView, expandMaskView, collaspeView, collaspeMaskView;
    private View payMaskView;

    private int mMaskColor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_scroll2);
        mMaskColor = getResources().getColor(R.color.blue_dark);
        mAppBarLayout = (AppBarLayout)findViewById(R.id.appBarLayout);
        mAppBarLayout.addOnOffsetChangedListener(this);

        expandView = findViewById(R.id.expand_top_view);
        expandMaskView = findViewById(v_expand_mask);
        collaspeView = findViewById(R.id.collaspe_top_view);
        collaspeMaskView = findViewById(v_collapse_mask);
        payMaskView = findViewById(v_pay_mask);

    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int total = appBarLayout.getTotalScrollRange();
        int offset = Math.abs(verticalOffset);
        int alphaIn = (int)((offset / (float)total) * 255);
        int alphaOut = 255 - alphaIn;//(200 - offset) < 0 ? 0 : 200 - offset;
        int maskColorIn = Color.argb(alphaIn, Color.red(mMaskColor), Color.green(mMaskColor), Color.blue(mMaskColor));
        int maskColorInDouble = Color.argb(alphaIn * 2, Color.red(mMaskColor), Color.green(mMaskColor), Color.blue(mMaskColor));
        int maskColorOut = Color.argb(alphaOut*2, Color.red(mMaskColor),
                Color.green(mMaskColor), Color.blue(mMaskColor));
        if (offset <= total / 2) {
            expandView.setVisibility(View.VISIBLE);
            collaspeView.setVisibility(View.GONE);
            expandMaskView.setBackgroundColor(maskColorInDouble);
        } else {
            expandView.setVisibility(View.GONE);
            collaspeView.setVisibility(View.VISIBLE);
            collaspeMaskView.setBackgroundColor(maskColorOut);
        }
        payMaskView.setBackgroundColor(maskColorIn);
    }
}
