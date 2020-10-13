package io.renren.common.utils;

import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.entity.SysParkEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysParkService;
import net.sf.json.JSONObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class InfoJson extends AbstractController {




    public static String getCookieByLogin(SysParkEntity park){
        Map loginParmas = new HashMap();

        loginParmas.put("username", park.getUsername());
        loginParmas.put("password", park.getPassword());
        String payload = JSONObject.fromObject(loginParmas).toString();
        System.out.println("url:" + park.getWebsite());
        System.out.println("params:" + payload);
        StringBuffer jsonString;
        String cookieVal;
        try {
            URL url = new URL(park.getWebsite() + Constant.loginUrl);
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
            connection.setRequestProperty("Referer",park.getWebsite());
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

    public static JSONObject doPost(String path, Map params, String cookie, SysParkEntity park){
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
            connection.setRequestProperty("Referer",park.getWebsite());
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
