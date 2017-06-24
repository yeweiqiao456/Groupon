package com.ywq.tarena.groupon.util;

import android.util.Log;

import com.ywq.tarena.groupon.bean.CityBean;
import com.ywq.tarena.groupon.bean.TuanBean;
import com.ywq.tarena.groupon.config.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by tarena on 2017/6/19.
 */

public class RetrofitClient {

    private static RetrofitClient INSTANCE;

    public static RetrofitClient getInstance(){
        if (INSTANCE == null){
            synchronized (RetrofitClient.class){
                if (INSTANCE == null){
                    INSTANCE = new RetrofitClient();
                }
            }
        }
        return INSTANCE;
    }

    private Retrofit retrofit;

    private OkHttpClient okHttpClient;

    private NetService netService;

    private RetrofitClient(){
        okHttpClient = new OkHttpClient.Builder().addInterceptor(new MyOkHttpInterceptor()).build();
        retrofit = new Retrofit.Builder().client(okHttpClient).baseUrl(Constant.BASEURL).addConverterFactory(ScalarsConverterFactory.create()).addConverterFactory(GsonConverterFactory.create()).build();
        netService = retrofit.create(NetService.class);
    }

    public void test() {
        Map<String, String> params = new HashMap<>();
        params.put("city","北京");
        params.put("category","美食");
        String sign = HttpUtil.getSign(Constant.APPKEY,Constant.APPSECRET,params);
        Call<String> call = netService.test(Constant.APPKEY, sign, params);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String string = response.body();
                Log.d("TAG", "通过retrofit获得的响应：" + string);
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {

            }
        });
    }

    public  void getDailyDeals(String city,final Callback<String> callback2){

        Map<String, String> params = new HashMap<>();
        params.put("city",city);
        String date = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
        params.put("date",date);
        final String sign = HttpUtil.getSign(Constant.APPKEY,Constant.APPSECRET,params);
        Call<String> ids = netService.getDailyIds(Constant.APPKEY,sign,params);
        ids.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body());
                    JSONArray jsonArray = jsonObject.getJSONArray("id_list");
                    Log.d("TAG", "onResponse: " + response.body().toString());
                    int size = jsonArray.length();
                    if (size>40){
                        size = 40;
                    }

                    StringBuilder sb = new StringBuilder();
                    for (int i = 0;i < size;i++){
                        sb.append(jsonArray.get(i).toString()).append(",");
                    }

                    if (sb.length() > 0){

                        String idList = sb.substring(0,sb.length() - 1);
                        Map<String, String> params2 = new HashMap<String, String>();
                        params2.put("deal_ids",idList);
                        String sign2 = HttpUtil.getSign(Constant.APPKEY,Constant.APPSECRET,params2);
                        Call<String> call2 = netService.getDailyDeals(Constant.APPKEY, sign2, params2);
                        call2.enqueue(callback2);
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {

            }
        });

    }

    public void getDailyDeals2(String city,final Callback<TuanBean> callback2){
        Map<String, String> params = new HashMap<>();
        params.put("city",city);
        String date = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
        params.put("date",date);
        final String sign = HttpUtil.getSign(Constant.APPKEY,Constant.APPSECRET,params);
        Call<String> ids = netService.getDailyIds(Constant.APPKEY,sign,params);
        ids.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body());
                    JSONArray jsonArray = jsonObject.getJSONArray("id_list");
                    Log.d("TAG", "onResponse: " + response.body().toString());
                    int size = jsonArray.length();
                    if (size>40){
                        size = 40;
                    }

                    StringBuilder sb = new StringBuilder();
                    for (int i = 0;i < size;i++){
                        sb.append(jsonArray.get(i).toString()).append(",");
                    }

                    if (sb.length() > 0){

                        String idList = sb.substring(0,sb.length() - 1);
                        Map<String, String> params2 = new HashMap<String, String>();
                        params2.put("deal_ids",idList);
                        String sign2 = HttpUtil.getSign(Constant.APPKEY,Constant.APPSECRET,params2);
                        Call<TuanBean> call2 = netService.getDailyDeals2(Constant.APPKEY, sign2, params2);
                        call2.enqueue(callback2);
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {

            }
        });
    }

    public void getDailyDeals3(String city, final Callback<TuanBean> callback2){
        Map<String, String> params = new HashMap<>();
        params.put("city",city);
        String date = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
        params.put("date",date);
        Call<String> ids = netService.getDailyIds3(params);
        ids.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body());
                    Log.d("TAG", "jsonObject: "+jsonObject);
                    JSONArray jsonArray = jsonObject.getJSONArray("id_list");

                    int size = jsonArray.length();
                    if (size>40){
                        size = 40;
                    }

                    StringBuilder sb = new StringBuilder();
                    for (int i = 0;i < size;i++){
                        sb.append(jsonArray.get(i).toString()).append(",");
                    }

                    if (sb.length() > 0){

                        String idList = sb.substring(0,sb.length() - 1);
                        Map<String, String> params2 = new HashMap<String, String>();
                        params2.put("deal_ids",idList);

                        Call<TuanBean> call2 = netService.getDailyDeals3(params2);
                        call2.enqueue(callback2);
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {

            }
        });
    }

    public void getCitied(Callback<CityBean> callback){
        Call<CityBean> call = netService.getCities();
        call.enqueue(callback);
    }

    /**
     * OKHTTP的拦截器
     */
    public class MyOkHttpInterceptor implements Interceptor{

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            //获得请求对象
            Request request = chain.request();

            //请求路径
            //比如：http://baseurl/deal/get_daily_new_id_list?city=xxx&date=xxx
            HttpUrl url = request.url();
            //取出原有路径中的参数
            HashMap<String,String> params = new HashMap<>();
            //原有请求路径中,请求参数的名称
            //例如{city,date}
            Set<String> set = url.queryParameterNames();

            for (String key:set) {
                params.put(key,url.queryParameter(key));
            }

            String sign = HttpUtil.getSign(Constant.APPKEY,Constant.APPSECRET,params);

            //字符串形式的http://baseurl/deal/get_daily_new_id_list?city=xxx&date=xxx
            String urlString = url.toString();
            Log.d("TAG", "原始请求路径------> "+urlString);

            StringBuilder sb = new StringBuilder(urlString);
            if (set.size() == 0){
                //意味着原有请求路径中没有参数
                sb.append("?");
            } else {
                sb.append("&");
            }
            sb.append("appkey=").append(Constant.APPKEY);
            sb.append("&").append("sign=").append(sign);
            //http://baseurl/deal/get_daily_new_id_list?city=xxx&date=xxx&appkey=xxx&sign=xxx
            Log.d("TAG", "新的请求路径------>: "+sb.toString());
            Request newRequest = new Request.Builder().url(sb.toString()).build();


            return chain.proceed(newRequest);
        }
    }
}
