package com.ywq.tarena.groupon.util;

import android.content.pm.LauncherApps;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Response;
import com.squareup.picasso.Picasso;
import com.ywq.tarena.groupon.R;
import com.ywq.tarena.groupon.application.MyApp;
import com.ywq.tarena.groupon.bean.CityBean;
import com.ywq.tarena.groupon.bean.TuanBean;
import com.ywq.tarena.groupon.config.Constant;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Callback;

/**
 * 网络访问工具类
 * <p>
 * 符合大众点评服务器要求的地址：
 * http://网址部分？参数1=值&参数2=值2...
 * <p>
 * 请求地址中签名的生成：
 * 利用Appkey,AppSecret,以及其他访问参数
 * 首先将Appkey,AppSecret,以及其他访问参数拼接成一个字符串
 * 将拼接好的字符串进行转码(转码算法为SHA1算法)
 * 转码后就得到了签名
 * <p>
 * Created by tarena on 2017/6/19.
 */

public class HttpUtil {


    public static final String TAG = "TAG";

    /**
     * 获得满足大众点评服务器要求的请求路径
     *
     * @return
     */
    public static String getURL(String url, Map<String, String> params) {
        String result = "";

        String sign = getSign(Constant.APPKEY, Constant.APPSECRET, params);

        String query = getQuery(Constant.APPKEY, sign, params);

        result = url + "?" + query;

        return result;
    }

    /**
     * 获取请求地址中的签名
     *
     * @param appkey
     * @param appsecret
     * @param params
     * @return
     */
    public static String getSign(String appkey, String appsecret, Map<String, String> params) {
        StringBuilder stringBuilder = new StringBuilder();

        // 对参数名进行字典排序
        String[] keyArray = params.keySet().toArray(new String[0]);
        Arrays.sort(keyArray);
        // 拼接有序的参数名-值串
        stringBuilder.append(appkey);
        for (String key : keyArray) {
            stringBuilder.append(key).append(params.get(key));
        }
        String codes = stringBuilder.append(appsecret).toString();
        //纯JAVA环境中，利用Codec对字符串进行SHA1转码采用如下方式
        //String sign = org.apache.commons.codec.digest.DigestUtils.shaHex(codes).toUpperCase();
        String sign = new String(Hex.encodeHex(DigestUtils.sha(codes))).toUpperCase();

        return sign;
    }

    /**
     * 获取请求地址中的参数部分
     *
     * @param appkey
     * @param sign
     * @param params
     * @return
     */
    public static String getQuery(String appkey, String sign, Map<String, String> params) {
        try {
            // 添加签名
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("appkey=").append(appkey).append("&sign=").append(sign);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                stringBuilder.append('&').append(entry.getKey()).append('=').append(URLEncoder.encode(entry.getValue(), "utf-8"));
            }
            String queryString = stringBuilder.toString();

            return queryString;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            //扔异常
            throw new RuntimeException("使用了不正确的字符集名称");
        }
    }

    public static void testHttpURLConnection() {

        //获取大众点评要求的请求地址
        Map<String, String> params = new HashMap<>();
        params.put("city", "北京");
        params.put("category", "美食");
        final String url = getURL("http://api.dianping.com/v1/business/find_businesses", params);
        Log.i(TAG, "生成网络请求地址是: " + url);

        new Thread() {
            @Override
            public void run() {
                try {
                    super.run();
                    URL u = new URL(url);
                    HttpURLConnection connection = (HttpURLConnection) u.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);//该方法可写可不写，因为默认是true
                    connection.connect();

                    InputStream is = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf-8"));

                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null){
                        sb.append(line);
                    }
                    reader.close();
                    String response = sb.toString();

                    Log.i(TAG, "HttpURLConnection获得的服务器响应内容 ：" + response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public static void testVolley(){
        VolleyClient.getInstance().test();
    }

    public static void testRetrofit(){
        /*//1)创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://api.dianping.com/v1/").addConverterFactory(ScalarsConverterFactory.create()).build();
        //2)创建接口的实现类对象
        NetService service = retrofit.create(NetService.class);

        Map<String,String> params = new HashMap<>();
        params.put("city","北京");
        params.put("category","美食");
        String sign = getSign(Constant.APPKEY,Constant.APPSECRET,params);
        //3)获得请求对象
        Call<String> call = service.test(Constant.APPKEY, sign, params);
        //3)将请求对象添加到请求队列中
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String string = response.body();
                Log.d(TAG, "通过retrofit获得的响应：" + string);
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {

            }
        });*/
        RetrofitClient.getInstance().test();
    }

    public static void getDailyDealsByVolley(String city, Response.Listener<String> listener){
        VolleyClient.getInstance().getDailyDeals(city,listener);
    }

    public static void getDailyDealsByVolley2(String city, Response.Listener<TuanBean> listener){
        VolleyClient.getInstance().getDailyDeals2(city,listener);
    }

    public static void getDailyDealsByRetrofit(String city, Callback<String> callback){
        RetrofitClient.getInstance().getDailyDeals(city,callback);
    }

    public static void getDailyDealsByRetrofit2(String city, Callback<TuanBean> callback){
        RetrofitClient.getInstance().getDailyDeals2(city,callback);
    }

    public static void getDailyDealsByRetrofit3(String city, Callback<TuanBean> callback){
        RetrofitClient.getInstance().getDailyDeals3(city,callback);
    }

    public static void loadImage(String url, ImageView imageView){
        VolleyClient.getInstance().loadImage(url,imageView);
    }

    public static void displayImage(String url,ImageView imageView){
        Picasso.with(MyApp.CONTEXT).load(url).placeholder(R.drawable.bucket_no_picture).error(R.drawable.bucket_no_picture).into(imageView);
    }

    public static void getCitiesByVolley(Response.Listener<String> listener){
        VolleyClient.getInstance().getCities(listener);
    }

    public static void getCitiesByRetrofitClient(Callback<CityBean> callback){
        RetrofitClient.getInstance().getCitied(callback);
    }

}
