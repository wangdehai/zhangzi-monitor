package io.renren.modules.monitor.component;

import com.alibaba.fastjson.JSON;
import io.renren.modules.monitor.entity.WeatherEntity;
import io.renren.modules.monitor.entity.WeatherItemEntity;
import io.renren.modules.monitor.service.WeatherItemService;
import io.renren.modules.monitor.service.WeatherService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

/**
 * @Auther: wdh
 * @Date: 2020-07-20 09:40
 * @Description:
 */
@Component("weatherTimer")
public class weatherTimer {

    public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

    //配置您申请的KEY
    public static final String APPKEY ="364bbbe062a8b7465c4096dd37276e53";
    //长子县id
    public static final String CITYID ="581";

    @Autowired
    private WeatherService weatherService;
    @Autowired
    private WeatherItemService weatherItemService;

    public void weatherUpdate() {
        WeatherEntity weatherEntity = getWeather();
        if(weatherEntity != null){
            weatherService.delete(null);
            weatherService.insert(weatherEntity);
            weatherItemService.delete(null);
            weatherItemService.insertBatch(weatherEntity.getWeatherItemList());
        }
    }

    public static WeatherEntity getWeather(){
        Date date = new Date();
        WeatherEntity weatherEntity = null;
        JSONObject res = getRequest1();
        if(res.getInt("error_code")==0){
            JSONObject result = res.getJSONObject("result");
            weatherEntity = JSON.parseObject(JSON.toJSONString(result.getJSONObject("realtime")), WeatherEntity.class);
            weatherEntity.setUpdateTime(date);
            List<WeatherItemEntity> itmeList = new ArrayList<>();
            JSONArray itmes = result.getJSONArray("future");
            if(itmes != null && itmes.size() > 0){
                for(Object o : itmes){
                    WeatherItemEntity item = JSON.parseObject(JSON.toJSONString(o), WeatherItemEntity.class);
                    item.setUpdateTime(date);
                    JSONObject wid = JSONObject.fromObject(o).getJSONObject("wid");
                    item.setDay(wid.getString("day"));
                    item.setNight(wid.getString("night"));
                    itmeList.add(item);
                }
            }
            weatherEntity.setWeatherItemList(itmeList);
            return weatherEntity;
        }else{
            return null;
        }
    }
    
    public static JSONObject getRequest1(){
        String result =null;
        String url ="http://apis.juhe.cn/simpleWeather/query";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("city",CITYID);
        params.put("key",APPKEY);//应用APPKEY(应用详细页查询)
        JSONObject object = null;
        try {
            result =net(url, params, "GET");
            object = JSONObject.fromObject(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    /**
     *
     * @param strUrl 请求地址
     * @param params 请求参数
     * @param method 请求方法
     * @return  网络请求字符串
     * @throws Exception
     */
    public static String net(String strUrl, Map params,String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if(method==null || method.equals("GET")){
                strUrl = strUrl+"?"+urlencode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if(method==null || method.equals("GET")){
                conn.setRequestMethod("GET");
            }else{
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params!= null && method.equals("POST")) {
                try {
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                    out.writeBytes(urlencode(params));
                } catch (Exception e) {
                    System.out.println("发生错误：" + e);
                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }

    //将map型转为请求参数型
    public static String urlencode(Map<String,Object>data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
