package io.renren.common.netty;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.renren.common.utils.SpringUtil;
import io.renren.modules.monitor.entity.MonitorIotDeviceEntity;
import io.renren.modules.monitor.service.MonitorIotDeviceService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther: wdh
 * @Date: 2020-07-17 19:31
 * @Description:
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private static MonitorIotDeviceService monitorIotDeviceService;
    static {
        monitorIotDeviceService = SpringUtil.getBean(MonitorIotDeviceService.class);
    }
    /**
     * 客户端连接会触发
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Channel active......");
    }

    /**
     * 客户端发消息会触发
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("服务器收到消息: " + msg.toString());
        String params = msg.toString();
        if(StringUtils.isNotBlank(params)){


            List<MonitorIotDeviceEntity> iotList = new ArrayList<>();
            String devNum = params.substring(6,18);
            if("171CB73F66A9".equals(devNum)){
                //园区
                //温度
                String tem = new BigDecimal(Integer.valueOf(params.substring(32,40),16).shortValue()).divide(new BigDecimal(10)) + "℃";
                System.out.println("温度:" + tem);
                MonitorIotDeviceEntity monitorIotDevice1 = monitorIotDeviceService.selectOne(
                        new EntityWrapper<MonitorIotDeviceEntity>()
                                .eq("dev_num",devNum)
                                .eq("dev_key","温度")
                );
                if(monitorIotDevice1 == null){
                    monitorIotDevice1 = new MonitorIotDeviceEntity();
                }
                monitorIotDevice1.setDevNum(devNum);
                monitorIotDevice1.setDevKey("温度");
                monitorIotDevice1.setValue(tem);
                monitorIotDevice1.setRegionId("1");
                iotList.add(monitorIotDevice1);
                //湿度
                String hum = new BigDecimal(Integer.valueOf(params.substring(40,48),16)).divide(new BigDecimal(10)) + "%RH";
                System.out.println("湿度:" + hum);
                MonitorIotDeviceEntity monitorIotDevice2 = monitorIotDeviceService.selectOne(
                        new EntityWrapper<MonitorIotDeviceEntity>()
                                .eq("dev_num",devNum)
                                .eq("dev_key","湿度")
                );
                if(monitorIotDevice2 == null){
                    monitorIotDevice2 = new MonitorIotDeviceEntity();
                }
                monitorIotDevice2.setDevNum(devNum);
                monitorIotDevice2.setDevKey("湿度");
                monitorIotDevice2.setValue(hum);
                monitorIotDevice2.setRegionId("1");
                iotList.add(monitorIotDevice2);
                //大气压
                String pre = new BigDecimal(Integer.valueOf(params.substring(48,56),16)).divide(new BigDecimal(100)) + "mbar";
                System.out.println("大气压:" + pre);
                MonitorIotDeviceEntity monitorIotDevice3 = monitorIotDeviceService.selectOne(
                        new EntityWrapper<MonitorIotDeviceEntity>()
                                .eq("dev_num",devNum)
                                .eq("dev_key","大气压")
                );
                if(monitorIotDevice3 == null){
                    monitorIotDevice3 = new MonitorIotDeviceEntity();
                }
                monitorIotDevice3.setDevNum(devNum);
                monitorIotDevice3.setDevKey("大气压");
                monitorIotDevice3.setValue(pre);
                monitorIotDevice3.setRegionId("1");
                iotList.add(monitorIotDevice3);
                //风速
                String ws = new BigDecimal(Integer.valueOf(params.substring(56,64),16)).divide(new BigDecimal(10)) + "m/s";
                System.out.println("风速:" + ws);
                MonitorIotDeviceEntity monitorIotDevice4 = monitorIotDeviceService.selectOne(
                        new EntityWrapper<MonitorIotDeviceEntity>()
                                .eq("dev_num",devNum)
                                .eq("dev_key","风速")
                );
                if(monitorIotDevice4 == null){
                    monitorIotDevice4 = new MonitorIotDeviceEntity();
                }
                monitorIotDevice4.setDevNum(devNum);
                monitorIotDevice4.setDevKey("风速");
                monitorIotDevice4.setValue(ws);
                monitorIotDevice4.setRegionId("1");
                iotList.add(monitorIotDevice4);
                //风向
                String wd = new BigDecimal(Integer.valueOf(params.substring(64,72),16)) + "度";
                System.out.println("风向:" + wd);
                MonitorIotDeviceEntity monitorIotDevice5 = monitorIotDeviceService.selectOne(
                        new EntityWrapper<MonitorIotDeviceEntity>()
                                .eq("dev_num",devNum)
                                .eq("dev_key","风向")
                );
                if(monitorIotDevice5 == null){
                    monitorIotDevice5 = new MonitorIotDeviceEntity();
                }
                monitorIotDevice5.setDevNum(devNum);
                monitorIotDevice5.setDevKey("风向");
                monitorIotDevice5.setValue(wd);
                monitorIotDevice5.setRegionId("1");
                iotList.add(monitorIotDevice5);
                //雨量
                String rain = new BigDecimal(Integer.valueOf(params.substring(72,80),16)) + "mm";
                System.out.println("雨量:" + rain);
                MonitorIotDeviceEntity monitorIotDevice6 = monitorIotDeviceService.selectOne(
                        new EntityWrapper<MonitorIotDeviceEntity>()
                                .eq("dev_num",devNum)
                                .eq("dev_key","雨量")
                );
                if(monitorIotDevice6 == null){
                    monitorIotDevice6 = new MonitorIotDeviceEntity();
                }
                monitorIotDevice6.setDevNum(devNum);
                monitorIotDevice6.setDevKey("雨量");
                monitorIotDevice6.setValue(rain);
                monitorIotDevice6.setRegionId("1");
                iotList.add(monitorIotDevice6);
            }else if("171CB73FF7D0".equals(devNum)){
                //牛棚
                //温度
                String tem = new BigDecimal(Integer.valueOf(params.substring(32,40),16).shortValue()).divide(new BigDecimal(10)) + "℃";
                System.out.println("温度:" + tem);
                MonitorIotDeviceEntity monitorIotDevice1 = monitorIotDeviceService.selectOne(
                        new EntityWrapper<MonitorIotDeviceEntity>()
                                .eq("dev_num",devNum)
                                .eq("dev_key","温度")
                );
                if(monitorIotDevice1 == null){
                    monitorIotDevice1 = new MonitorIotDeviceEntity();
                }
                monitorIotDevice1.setDevNum(devNum);
                monitorIotDevice1.setDevKey("温度");
                monitorIotDevice1.setValue(tem);
                monitorIotDevice1.setRegionId("2");
                iotList.add(monitorIotDevice1);
                //湿度
                String hum = new BigDecimal(Integer.valueOf(params.substring(40,48),16)).divide(new BigDecimal(10)) + "%RH";
                System.out.println("湿度:" + hum);
                MonitorIotDeviceEntity monitorIotDevice2 = monitorIotDeviceService.selectOne(
                        new EntityWrapper<MonitorIotDeviceEntity>()
                                .eq("dev_num",devNum)
                                .eq("dev_key","湿度")
                );
                if(monitorIotDevice2 == null){
                    monitorIotDevice2 = new MonitorIotDeviceEntity();
                }
                monitorIotDevice2.setDevNum(devNum);
                monitorIotDevice2.setDevKey("湿度");
                monitorIotDevice2.setValue(hum);
                monitorIotDevice2.setRegionId("2");
                iotList.add(monitorIotDevice2);
                //光照度
                String light = new BigDecimal(Integer.valueOf(params.substring(48,56),16)) + "Lux";
                System.out.println("光照度:" + light);
                MonitorIotDeviceEntity monitorIotDevice3 = monitorIotDeviceService.selectOne(
                        new EntityWrapper<MonitorIotDeviceEntity>()
                                .eq("dev_num",devNum)
                                .eq("dev_key","光照度")
                );
                if(monitorIotDevice3 == null){
                    monitorIotDevice3 = new MonitorIotDeviceEntity();
                }
                monitorIotDevice3.setDevNum(devNum);
                monitorIotDevice3.setDevKey("光照度");
                monitorIotDevice3.setValue(light);
                monitorIotDevice3.setRegionId("2");
                iotList.add(monitorIotDevice3);
                //噪声
                String noise = new BigDecimal(Integer.valueOf(params.substring(56,64),16)).divide(new BigDecimal(10)) + "db";
                System.out.println("噪声:" + noise);
                MonitorIotDeviceEntity monitorIotDevice4 = monitorIotDeviceService.selectOne(
                        new EntityWrapper<MonitorIotDeviceEntity>()
                                .eq("dev_num",devNum)
                                .eq("dev_key","噪声")
                );
                if(monitorIotDevice4 == null){
                    monitorIotDevice4 = new MonitorIotDeviceEntity();
                }
                monitorIotDevice4.setDevNum(devNum);
                monitorIotDevice4.setDevKey("噪声");
                monitorIotDevice4.setValue(noise);
                monitorIotDevice4.setRegionId("2");
                iotList.add(monitorIotDevice4);
                //二氧化碳
                String cd = new BigDecimal(Integer.valueOf(params.substring(64,72),16)) + "ppm";
                System.out.println("二氧化碳:" + cd);
                MonitorIotDeviceEntity monitorIotDevice5 = monitorIotDeviceService.selectOne(
                        new EntityWrapper<MonitorIotDeviceEntity>()
                                .eq("dev_num",devNum)
                                .eq("dev_key","二氧化碳")
                );
                if(monitorIotDevice5 == null){
                    monitorIotDevice5 = new MonitorIotDeviceEntity();
                }
                monitorIotDevice5.setDevNum(devNum);
                monitorIotDevice5.setDevKey("二氧化碳");
                monitorIotDevice5.setValue(cd);
                monitorIotDevice5.setRegionId("2");
                iotList.add(monitorIotDevice5);
                //硫化氢
                String hs = new BigDecimal(Integer.valueOf(params.substring(72,80),16)).divide(new BigDecimal(10)) + "ppm";
                System.out.println("硫化氢:" + hs);
                MonitorIotDeviceEntity monitorIotDevice6 = monitorIotDeviceService.selectOne(
                        new EntityWrapper<MonitorIotDeviceEntity>()
                                .eq("dev_num",devNum)
                                .eq("dev_key","硫化氢")
                );
                if(monitorIotDevice6 == null){
                    monitorIotDevice6 = new MonitorIotDeviceEntity();
                }
                monitorIotDevice6.setDevNum(devNum);
                monitorIotDevice6.setDevKey("硫化氢");
                monitorIotDevice6.setValue(hs);
                monitorIotDevice6.setRegionId("2");
                iotList.add(monitorIotDevice6);
                //氨气
                String amm = new BigDecimal(Integer.valueOf(params.substring(80,88),16)).divide(new BigDecimal(100)) + "ppm";
                System.out.println("氨气:" + amm);
                MonitorIotDeviceEntity monitorIotDevice7 = monitorIotDeviceService.selectOne(
                        new EntityWrapper<MonitorIotDeviceEntity>()
                                .eq("dev_num",devNum)
                                .eq("dev_key","氨气")
                );
                if(monitorIotDevice7 == null){
                    monitorIotDevice7 = new MonitorIotDeviceEntity();
                }
                monitorIotDevice7.setDevNum(devNum);
                monitorIotDevice7.setDevKey("氨气");
                monitorIotDevice7.setValue(amm);
                monitorIotDevice7.setRegionId("2");
                iotList.add(monitorIotDevice7);
            }else if("171CB73F778B".equals(devNum)){
                //蔬菜园区
                //温度
                String tem = new BigDecimal(Integer.valueOf(params.substring(32,40),16).shortValue()).divide(new BigDecimal(10)) + "℃";
                System.out.println("温度:" + tem);
                MonitorIotDeviceEntity monitorIotDevice1 = monitorIotDeviceService.selectOne(
                        new EntityWrapper<MonitorIotDeviceEntity>()
                                .eq("dev_num",devNum)
                                .eq("dev_key","温度")
                );
                if(monitorIotDevice1 == null){
                    monitorIotDevice1 = new MonitorIotDeviceEntity();
                }
                monitorIotDevice1.setDevNum(devNum);
                monitorIotDevice1.setDevKey("温度");
                monitorIotDevice1.setValue(tem);
                monitorIotDevice1.setRegionId("3");
                iotList.add(monitorIotDevice1);
                //湿度
                String hum = new BigDecimal(Integer.valueOf(params.substring(40,48),16)).divide(new BigDecimal(10)) + "%RH";
                System.out.println("湿度:" + hum);
                MonitorIotDeviceEntity monitorIotDevice2 = monitorIotDeviceService.selectOne(
                        new EntityWrapper<MonitorIotDeviceEntity>()
                                .eq("dev_num",devNum)
                                .eq("dev_key","湿度")
                );
                if(monitorIotDevice2 == null){
                    monitorIotDevice2 = new MonitorIotDeviceEntity();
                }
                monitorIotDevice2.setDevNum(devNum);
                monitorIotDevice2.setDevKey("湿度");
                monitorIotDevice2.setValue(hum);
                monitorIotDevice2.setRegionId("3");
                iotList.add(monitorIotDevice2);
                //光照度
                String light = new BigDecimal(Integer.valueOf(params.substring(48,56),16)) + "Lux";
                System.out.println("光照度:" + light);
                MonitorIotDeviceEntity monitorIotDevice3 = monitorIotDeviceService.selectOne(
                        new EntityWrapper<MonitorIotDeviceEntity>()
                                .eq("dev_num",devNum)
                                .eq("dev_key","光照度")
                );
                if(monitorIotDevice3 == null){
                    monitorIotDevice3 = new MonitorIotDeviceEntity();
                }
                monitorIotDevice3.setDevNum(devNum);
                monitorIotDevice3.setDevKey("光照度");
                monitorIotDevice3.setValue(light);
                monitorIotDevice3.setRegionId("3");
                iotList.add(monitorIotDevice3);
                //土壤温度
                String st = new BigDecimal(Integer.valueOf(params.substring(56,64),16).shortValue()).divide(new BigDecimal(10)) + "℃";
                System.out.println("土壤温度:" + st);
                MonitorIotDeviceEntity monitorIotDevice4 = monitorIotDeviceService.selectOne(
                        new EntityWrapper<MonitorIotDeviceEntity>()
                                .eq("dev_num",devNum)
                                .eq("dev_key","土壤温度")
                );
                if(monitorIotDevice4 == null){
                    monitorIotDevice4 = new MonitorIotDeviceEntity();
                }
                monitorIotDevice4.setDevNum(devNum);
                monitorIotDevice4.setDevKey("土壤温度");
                monitorIotDevice4.setValue(st);
                monitorIotDevice4.setRegionId("3");
                iotList.add(monitorIotDevice4);
                //土壤湿度
                String sh = new BigDecimal(Integer.valueOf(params.substring(64,72),16)).divide(new BigDecimal(10)) + "%RH";
                System.out.println("土壤湿度:" + sh);
                MonitorIotDeviceEntity monitorIotDevice5 = monitorIotDeviceService.selectOne(
                        new EntityWrapper<MonitorIotDeviceEntity>()
                                .eq("dev_num",devNum)
                                .eq("dev_key","土壤湿度")
                );
                if(monitorIotDevice5 == null){
                    monitorIotDevice5 = new MonitorIotDeviceEntity();
                }
                monitorIotDevice5.setDevNum(devNum);
                monitorIotDevice5.setDevKey("土壤湿度");
                monitorIotDevice5.setValue(sh);
                monitorIotDevice5.setRegionId("3");
                iotList.add(monitorIotDevice5);
                //土壤氮
                String sn = new BigDecimal(Integer.valueOf(params.substring(72,80),16)) + "mg/kg";
                System.out.println("土壤氮:" + sn);
                MonitorIotDeviceEntity monitorIotDevice6 = monitorIotDeviceService.selectOne(
                        new EntityWrapper<MonitorIotDeviceEntity>()
                                .eq("dev_num",devNum)
                                .eq("dev_key","土壤氮")
                );
                if(monitorIotDevice6 == null){
                    monitorIotDevice6 = new MonitorIotDeviceEntity();
                }
                monitorIotDevice6.setDevNum(devNum);
                monitorIotDevice6.setDevKey("土壤氮");
                monitorIotDevice6.setValue(sn);
                monitorIotDevice6.setRegionId("3");
                iotList.add(monitorIotDevice6);
                //土壤磷
                String sl = new BigDecimal(Integer.valueOf(params.substring(80,88),16)) + "mg/kg";
                System.out.println("土壤磷:" + sl);
                MonitorIotDeviceEntity monitorIotDevice7 = monitorIotDeviceService.selectOne(
                        new EntityWrapper<MonitorIotDeviceEntity>()
                                .eq("dev_num",devNum)
                                .eq("dev_key","土壤磷")
                );
                if(monitorIotDevice7 == null){
                    monitorIotDevice7 = new MonitorIotDeviceEntity();
                }
                monitorIotDevice7.setDevNum(devNum);
                monitorIotDevice7.setDevKey("土壤磷");
                monitorIotDevice7.setValue(sl);
                monitorIotDevice7.setRegionId("3");
                iotList.add(monitorIotDevice7);
                //土壤钾
                String sk = new BigDecimal(Integer.valueOf(params.substring(88,96),16)) + "mg/kg";
                System.out.println("土壤钾:" + sk);
                MonitorIotDeviceEntity monitorIotDevice8 = monitorIotDeviceService.selectOne(
                        new EntityWrapper<MonitorIotDeviceEntity>()
                                .eq("dev_num",devNum)
                                .eq("dev_key","土壤钾")
                );
                if(monitorIotDevice8 == null){
                    monitorIotDevice8 = new MonitorIotDeviceEntity();
                }
                monitorIotDevice8.setDevNum(devNum);
                monitorIotDevice8.setDevKey("土壤钾");
                monitorIotDevice8.setValue(sk);
                monitorIotDevice8.setRegionId("3");
                iotList.add(monitorIotDevice8);
                //土壤电导率
                String con = new BigDecimal(Integer.valueOf(params.substring(96,104),16)).divide(new BigDecimal(10)) + "us/cm";
                System.out.println("土壤电导率:" + con);
                MonitorIotDeviceEntity monitorIotDevice9 = monitorIotDeviceService.selectOne(
                        new EntityWrapper<MonitorIotDeviceEntity>()
                                .eq("dev_num",devNum)
                                .eq("dev_key","土壤电导率")
                );
                if(monitorIotDevice9 == null){
                    monitorIotDevice9 = new MonitorIotDeviceEntity();
                }
                monitorIotDevice9.setDevNum(devNum);
                monitorIotDevice9.setDevKey("土壤电导率");
                monitorIotDevice9.setValue(con);
                monitorIotDevice9.setRegionId("3");
                iotList.add(monitorIotDevice9);
                //土壤电ph
                String ph = new BigDecimal(Integer.valueOf(params.substring(104,112),16)).divide(new BigDecimal(100)) + "PH";
                System.out.println("土壤电ph:" + ph);
                MonitorIotDeviceEntity monitorIotDevice10 = monitorIotDeviceService.selectOne(
                        new EntityWrapper<MonitorIotDeviceEntity>()
                                .eq("dev_num",devNum)
                                .eq("dev_key","土壤电ph")
                );
                if(monitorIotDevice10 == null){
                    monitorIotDevice10 = new MonitorIotDeviceEntity();
                }
                monitorIotDevice10.setDevNum(devNum);
                monitorIotDevice10.setDevKey("土壤电ph");
                monitorIotDevice10.setValue(ph);
                monitorIotDevice10.setRegionId("3");
                iotList.add(monitorIotDevice10);
            }
            if(iotList != null && iotList.size() > 0){
                for(MonitorIotDeviceEntity iotDev : iotList){
                    iotDev.setUpdateTime(new Date());
                }
                monitorIotDeviceService.insertOrUpdateBatch(iotList);
            }
        }
        ctx.flush();
    }

    /**
     * 发生异常触发
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    
}
