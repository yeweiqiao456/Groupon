package com.ywq.tarena.groupon.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Response;
import com.google.gson.Gson;
import com.ywq.tarena.groupon.R;
import com.ywq.tarena.groupon.adapter.CityAdapter;
import com.ywq.tarena.groupon.bean.CityBean;
import com.ywq.tarena.groupon.bean.CitynameBean;
import com.ywq.tarena.groupon.util.HttpUtil;
import com.ywq.tarena.groupon.util.PinYinUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;

public class CityActivity extends Activity {

    @BindView(R.id.recyclerView_city_cities)
    RecyclerView recyclerView;
    //适配器
    CityAdapter adapter;
    //数据源
    List<CitynameBean> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        ButterKnife.bind(this);

        initialRecyclerView();
    }

    private void initialRecyclerView() {

        //初始化数据，适配器

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        datas = new ArrayList<>();
        adapter = new CityAdapter(this, datas);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        //调用HttpUtil获取城市信息
        /*HttpUtil.getCitiesByVolley(new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                Gson gson = new Gson();
                CityBean cityBean = gson.fromJson(s, CityBean.class);
                //"全国，上海，杭州，北京，其他城市..."
                List<String> list = cityBean.getCities();
                //根据List<String>创建一个List<CitynameBean>
                //将List<CitynameBean>放到RecyclerView中显示

                List<CitynameBean> citynameBeanList = new ArrayList<CitynameBean>();
                for (String name:list) {
                    if (!name.equals("全国") && !name.equals("其它城市") && !name.equals("点评实验室")){
                        CitynameBean citynameBean = new CitynameBean();
                        citynameBean.setCityName(name);
                        citynameBean.setPyName(PinYinUtil.getPinYin(name));
                        citynameBean.setLetter(PinYinUtil.getLetter(name));
                        citynameBeanList.add(citynameBean);
                    }
                }

                Collections.sort(citynameBeanList, new Comparator<CitynameBean>() {
                    @Override
                    public int compare(CitynameBean t1, CitynameBean t2) {
                        return t1.getPyName().compareTo(t2.getPyName());
                    }
                });

                adapter.addAll(citynameBeanList,true);
            }
        });*/
        HttpUtil.getCitiesByRetrofitClient(new Callback<CityBean>() {
            @Override
            public void onResponse(Call<CityBean> call, retrofit2.Response<CityBean> response) {
                CityBean cityBean = response.body();
                //"全国，上海，杭州，北京，其他城市..."
                List<String> list = cityBean.getCities();
                //根据List<String>创建一个List<CitynameBean>
                //将List<CitynameBean>放到RecyclerView中显示

                List<CitynameBean> citynameBeanList = new ArrayList<CitynameBean>();
                for (String name:list) {
                    if (!name.equals("全国") && !name.equals("其它城市") && !name.equals("点评实验室")){
                        CitynameBean citynameBean = new CitynameBean();
                        citynameBean.setCityName(name);
                        citynameBean.setPyName(PinYinUtil.getPinYin(name));
                        citynameBean.setLetter(PinYinUtil.getLetter(name));
                        citynameBeanList.add(citynameBean);
                    }
                }

                Collections.sort(citynameBeanList, new Comparator<CitynameBean>() {
                    @Override
                    public int compare(CitynameBean t1, CitynameBean t2) {
                        return t1.getPyName().compareTo(t2.getPyName());
                    }
                });

                adapter.addAll(citynameBeanList,true);
            }

            @Override
            public void onFailure(Call<CityBean> call, Throwable throwable) {

            }
        });


    }
}
