package io.renren.common.utils;

import net.sf.json.JSONObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class InfoJson {




    public static String getCookieByLogin(String path){
        Map loginParmas = new HashMap();
        loginParmas.put("username", Constant.username);
        loginParmas.put("password",Constant.password);
        String payload = JSONObject.fromObject(loginParmas).toString();
        System.out.println("url:" + path);
        System.out.println("params:" + payload);
        StringBuffer jsonString;
        String cookieVal;
        try {
            URL url = new URL(path);
            if("https".equalsIgnoreCase(url.getProtocol())){
                SslUtils.ignoreSsl();
            }
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);//连接超时 单位毫秒
            connection.setRequestProperty("User-Agent", Constant.userAgent);
            connection.setRequestProperty("Charsert", "UTF-8");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");//设置参数类型是json格式
            connection.setRequestProperty("Referer",Constant.Referer);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            writer.write(payload);
            writer.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            jsonString = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                jsonString.append(line);
            }
            cookieVal = connection.getHeaderField("Set-Cookie");
            br.close();
            connection.disconnect();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return cookieVal;
    }

    public static JSONObject doPost(String path, Map params, String cookie){
        String payload = JSONObject.fromObject(params).toString();
        System.out.println("url:" + path);
        System.out.println("params:" + payload);
        StringBuffer jsonString;
        try {
            URL url = new URL(path);
            if("https".equalsIgnoreCase(url.getProtocol())){
                SslUtils.ignoreSsl();
            }
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);//连接超时 单位毫秒
            connection.setRequestProperty("User-Agent", Constant.userAgent);
            connection.setRequestProperty("Charsert", "UTF-8");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");//设置参数类型是json格式
            connection.setRequestProperty("Referer",Constant.Referer);
            connection.setRequestProperty("Cookie",cookie);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            writer.write(payload);
            writer.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            jsonString = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                jsonString.append(line);
            }
            br.close();
            connection.disconnect();
        } catch (Exception e) {
            System.out.println("错误" + e);
            throw new RuntimeException(e.getMessage());
        }
        return JSONObject.fromObject(jsonString.toString());
    }



}
