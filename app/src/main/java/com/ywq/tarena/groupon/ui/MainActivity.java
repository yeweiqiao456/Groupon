package com.ywq.tarena.groupon.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ywq.tarena.groupon.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    //头部
    @BindView(R.id.ll_main_top_left)
    LinearLayout cityContainer;
    @BindView(R.id.textView_main_top_cityName)
    TextView textView_cityName;//显示城市名称
    @BindView(R.id.imageView_main_top_right)
    ImageView imageView_top_right;
    @BindView(R.id.menu_main_layout)
    View menuLayout;


    //中段
    @BindView(R.id.ptrlv_main)
    PullToRefreshListView ptrListView;

    ListView listView;
    List<String> datas;
    ArrayAdapter<String> adapter;


    //脚部
    @BindView(R.id.radioGroup_main_bottom)
    RadioGroup radioGroup_Bottom;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        initialListView();
    }

    @OnClick(R.id.ll_main_top_left)
    public void jumpToCity(View view){
        startActivity(new Intent(MainActivity.this,CityActivity.class));
    }

    @OnClick(R.id.imageView_main_top_right)
    public void toggleMenu(View view){
        if (menuLayout.getVisibility() == View.VISIBLE){
            menuLayout.setVisibility(View.INVISIBLE);
        } {
            menuLayout.setVisibility(View.VISIBLE);
        }
    }

    private void initialListView() {
        listView = ptrListView.getRefreshableView();
        datas = new ArrayList<>();
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,datas);
        //为listView添加若干头部
        LayoutInflater inflater = LayoutInflater.from(this);

        View listHeaderIcons = inflater.inflate(R.layout.header_list_icons,listView,false);
        View listHeaderSquare = inflater.inflate(R.layout.header_list_square,listView,false);
        View listHeaderAds = inflater.inflate(R.layout.header_list_ads,listView,false);
        View listHeaderCategories = inflater.inflate(R.layout.header_list_categories,listView,false);
        View listHeaderRecommend = inflater.inflate(R.layout.header_list_recommend,listView,false);

        listView.addHeaderView(listHeaderIcons);
        listView.addHeaderView(listHeaderSquare);
        listView.addHeaderView(listHeaderAds);
        listView.addHeaderView(listHeaderCategories);
        listView.addHeaderView(listHeaderRecommend);

        listView.setAdapter(adapter);
        
        initialListViewHeaderIcon(listHeaderIcons);

        //添加下拉刷新事件
        ptrListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        datas.add(0,"新增数据");
                        adapter.notifyDataSetChanged();
                        ptrListView.onRefreshComplete();
                    }
                },1500);
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem == 0){
                    cityContainer.setVisibility(View.VISIBLE);
                    imageView_top_right.setVisibility(View.VISIBLE);
                } else {
                    cityContainer.setVisibility(View.GONE);
                    imageView_top_right.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initialListViewHeaderIcon(View listHeaderIcons) {
        final ViewPager viewPager = (ViewPager) listHeaderIcons.findViewById(R.id.vp_header_list_icons);

        PagerAdapter adapter = new PagerAdapter() {

            int[] resIDs = {
                    R.layout.icons_list_1,
                    R.layout.icons_list_2,
                    R.layout.icons_list_3
            };
            @Override
            public int getCount() {
                return 30000;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                int layoutID = resIDs[position%3];
                View view = LayoutInflater.from(MainActivity.this).inflate(layoutID,viewPager,false);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);

            }
        };

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(15000);

        final ImageView iv_1 = (ImageView) listHeaderIcons.findViewById(R.id.iv_header_list_icons_indicator1);
        final ImageView iv_2 = (ImageView) listHeaderIcons.findViewById(R.id.iv_header_list_icons_indicator2);
        final ImageView iv_3 = (ImageView) listHeaderIcons.findViewById(R.id.iv_header_list_icons_indicator3);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                iv_1.setImageResource(R.drawable.banner_dot);
                iv_2.setImageResource(R.drawable.banner_dot);
                iv_3.setImageResource(R.drawable.banner_dot);

                switch (position%3){
                    case 0:
                        iv_1.setImageResource(R.drawable.banner_dot_pressed);
                        break;
                    case 1:
                        iv_2.setImageResource(R.drawable.banner_dot_pressed);
                        break;
                    case 2:
                        iv_3.setImageResource(R.drawable.banner_dot_pressed);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initialData();
    }

    private void initialData() {
        datas.add("aaa");
        datas.add("bbb");
        datas.add("ccc");
        datas.add("ddd");
        datas.add("rrr");
        datas.add("eee");
        datas.add("fff");
        datas.add("hhh");
        adapter.notifyDataSetChanged();
    }
}
