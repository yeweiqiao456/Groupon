package com.ywq.tarena.groupon.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;

import com.viewpagerindicator.CirclePageIndicator;
import com.ywq.tarena.groupon.R;
import com.ywq.tarena.groupon.adapter.MyPagerAdapter;
import com.ywq.tarena.groupon.fragment.FragmentA;
import com.ywq.tarena.groupon.fragment.FragmentB;
import com.ywq.tarena.groupon.fragment.FragmentC;
import com.ywq.tarena.groupon.fragment.FragmentD;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuideActivity extends FragmentActivity {

    @BindView(R.id.viewPager_guide)
    ViewPager viewPager_guide;

    MyPagerAdapter adapter;

    FragmentA fragmentA;
    FragmentB fragmentB;
    FragmentC fragmentC;
    FragmentD fragmentD;

    List<Fragment> fragments ;

    @BindView(R.id.indicator)
    CirclePageIndicator indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        initialFragment();
    }

    private void initialFragment() {
        fragmentA = new FragmentA();
        fragmentB = new FragmentB();
        fragmentC = new FragmentC();
        fragmentD = new FragmentD();

        fragments = new ArrayList<>();
        fragments.add(fragmentA);
        fragments.add(fragmentB);
        fragments.add(fragmentC);
        fragments.add(fragmentD);

        adapter = new MyPagerAdapter(getSupportFragmentManager(),fragments);

        viewPager_guide.setAdapter(adapter);
        indicator.setViewPager(viewPager_guide);

        //当前运行程序所使用的设备的屏幕密度
        //低密度 ldpi  120px/1 inch(2.54cm)
        //中密度 mdpi  160px/1 inch(2.54cm)
        //高密度 hdpi  240px/1 inch(2.54cm)
        //很高密度 xhdpi    320px/1 inch(2.54cm)
        //非常高密度 xxhdpi  480px/1 inch(2.54cm)

        //dp是一个绝对单位 160 dp = 1 inch
        //1dp 在低密度屏幕上 0.75px
        //1dp 在中密度屏幕上 1px
        //1dp 在高密度屏幕上 1.5px
        //1dp 在很高密度屏幕上 2px
        //1dp 在非常高度屏幕上 3px

        //另外一种获得5dp在当前设备屏幕密度上的像素值得方式
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,5,getResources().getDisplayMetrics());

        final float density = getResources().getDisplayMetrics().density;
        //indicator.setBackgroundColor(0x00ff6633);
        //5dp在当前设备上所对应的像素值(px)
        indicator.setRadius(5 * density);
        indicator.setPageColor(0x00ff6633);
        indicator.setFillColor(0xffff6633);
        indicator.setStrokeColor(0xffC0C0C0);
        indicator.setStrokeWidth(density);

        viewPager_guide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //NO_OP
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 3){
                    indicator.setVisibility(View.INVISIBLE);
                } else {
                    indicator.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //NO_OP
            }
        });
    }


}
