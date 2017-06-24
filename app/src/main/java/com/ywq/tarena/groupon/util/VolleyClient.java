package com.ywq.tarena.groupon.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.ywq.tarena.groupon.R;
import com.ywq.tarena.groupon.application.MyApp;
import com.ywq.tarena.groupon.bean.TuanBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tarena on 2017/6/19.
 */

public class VolleyClient {

    //1)声明一个私有的静态的属性
    private static VolleyClient INSTANCE;
    //private static VolleyClient INSTANCE = new VolleyClient();//饿汉式单例

    //2)声明一个公有的静态的获取1)的方法
    //(懒汉式)
    public static VolleyClient getInstance() {
        if (INSTANCE == null) {
            synchronized (VolleyClient.class) {
                if (INSTANCE == null) {
                    INSTANCE = new VolleyClient();
                }
            }
        }

        return INSTANCE;
    }


    RequestQueue queue;
    ImageLoader imageLoader;

    //3)构造器私有化
    private VolleyClient() {
        queue = Volley.newRequestQueue(MyApp.CONTEXT);
        imageLoader = new ImageLoader(queue, new ImageLoader.ImageCache() {

            //least recently use
            LruCache<String,Bitmap> cache = new LruCache<String,Bitmap>((int) (Runtime.getRuntime().maxMemory()/8)){
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getHeight() * value.getRowBytes();
                }
            };

            @Override
            public Bitmap getBitmap(String s) {
                return cache.get(s);
            }

            @Override
            public void putBitmap(String s, Bitmap bitmap) {
                cache.put(s,bitmap);
            }
        });
    }

    private VolleyClient(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    public void test(){
        Map<String,String> params = new HashMap<>();
        params.put("city","北京");
        params.put("category","美食");
        String url = HttpUtil.getURL("http://api.dianping.com/v1/business/find_businesses",params);
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.i("TAG", "通过Volley获取的服务器响应内容：" + s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        queue.add(request);
    }

    /**
     * 通过Volley获取每日新增的团购信息
     *
     * @param city
     * @param listener
     */
    public void getDailyDeals(String city,final Response.Listener<String> listener){

        //1)获取新增团购的ID列表
        final Map<String, String> params = new HashMap<>();
        params.put("city",city);
        String date = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
        params.put("date",date);
        String url = HttpUtil.getURL("http://api.dianping.com/v1/deal/get_daily_new_id_list",params);
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                //利用Jsonlib(JSONObject提取团购ID)
                try {
                    /*{
                        "status": "OK",
                            "count": 309,
                            "id_list": [
                        "1-33946",
                                "1-4531",
                                "1-4571",
                                "1-5336",
                                "1-5353",
                                "......"
                        ]
                    }*/
                    //"1-4531,1-4571,1-5336..."
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("id_list");
                    int size = jsonArray.length();
                    if (size > 40){
                        size = 40;
                    }
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < size; i++){
                        sb.append(jsonArray.get(i).toString()).append(",");
                    }
                    if (sb.length() > 0 ) {
                        String idList = sb.substring(0, sb.length() - 1);


                        //2)获取团购详情
                        Map<String, String> params2 = new HashMap<>();
                        params2.put("deal_ids", idList);
                        Log.d("TAG", "idList: " + idList);
                        String url2 = HttpUtil.getURL("http://api.dianping.com/v1/deal/get_batch_deals_by_id", params2);
                        StringRequest request2 = new StringRequest(url2, listener, null);
                        queue.add(request2);
                    } else {
                        //该城市今日无新增团购
                        listener.onResponse(null);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        queue.add(request);
    }

    /**
     * 显示网络中的一幅图片
     * @param url       图片在网络中的地址
     * @param imageView 显示图片的控件
     */
    public void loadImage(String url, ImageView imageView){

        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView, R.drawable.bucket_no_picture,R.drawable.bucket_no_picture);
        imageLoader.get(url,listener);
    }

    public void getDailyDeals2(String city,final Response.Listener<TuanBean> listener){
        //1)获取新增团购的ID列表
        final Map<String, String> params = new HashMap<>();
        params.put("city",city);
        String date = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
        params.put("date",date);
        String url = HttpUtil.getURL("http://api.dianping.com/v1/deal/get_daily_new_id_list",params);
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                //利用Jsonlib(JSONObject提取团购ID)
                try {
                    /*{
                        "status": "OK",
                            "count": 309,
                            "id_list": [
                        "1-33946",
                                "1-4531",
                                "1-4571",
                                "1-5336",
                                "1-5353",
                                "......"
                        ]
                    }*/
                    //"1-4531,1-4571,1-5336..."
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("id_list");
                    int size = jsonArray.length();
                    if (size > 40){
                        size = 40;
                    }
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < size; i++){
                        sb.append(jsonArray.get(i).toString()).append(",");
                    }
                    if (sb.length() > 0 ) {
                        String idList = sb.substring(0, sb.length() - 1);


                        //2)获取团购详情
                        Map<String, String> params2 = new HashMap<>();
                        params2.put("deal_ids", idList);
                        Log.d("TAG", "idList: " + idList);
                        String url2 = HttpUtil.getURL("http://api.dianping.com/v1/deal/get_batch_deals_by_id", params2);
                        TuanBeanRequest request2 = new TuanBeanRequest(url2, listener);
                        queue.add(request2);
                    } else {
                        //该城市今日无新增团购
                        listener.onResponse(null);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        queue.add(request);
    }

    /**
     * 自定义请求对象
     */
    public class TuanBeanRequest extends Request<TuanBean>{

        Response.Listener<TuanBean> listener;

        public TuanBeanRequest( String url, Response.Listener<TuanBean> listener) {
            super(Method.GET, url, null);
            this.listener = listener;
        }

        @Override
        protected Response<TuanBean> parseNetworkResponse(NetworkResponse networkResponse) {

            String resp = new String(networkResponse.data);

            Gson gson = new Gson();

            TuanBean tuanBean = gson.fromJson(resp,TuanBean.class);

            //自己组装一个Volley的Request对象作为方法的返回值

            Response<TuanBean> result = Response.success(tuanBean, HttpHeaderParser.parseCacheHeaders(networkResponse));

            return result;
        }

        @Override
        protected void deliverResponse(TuanBean tuanBean) {
            listener.onResponse(tuanBean);
        }
    }

    public void getCities(Response.Listener<String> listener){
        Map<String, String> params = new HashMap<>();
        String url = HttpUtil.getURL("http://api.dianping.com/v1/metadata/get_cities_with_businesses",params);
        StringRequest request = new StringRequest(url,listener,null);
        queue.add(request);
    }

}
