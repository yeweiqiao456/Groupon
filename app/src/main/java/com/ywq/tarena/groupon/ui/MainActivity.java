package com.ywq.tarena.groupon.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ywq.tarena.groupon.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity {

    @BindView(R.id.ptrlv_main)
    PullToRefreshListView ptrListView;

    ListView listView;
    List<String> datas;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        initialListView();
    }

    private void initialListView() {
        listView = ptrListView.getRefreshableView();
        datas = new ArrayList<>();
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,datas);
        listView.setAdapter(adapter);

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
