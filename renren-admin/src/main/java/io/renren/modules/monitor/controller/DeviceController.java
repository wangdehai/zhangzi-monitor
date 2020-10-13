package io.renren.modules.monitor.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.renren.common.utils.Constant;
import io.renren.common.utils.InfoJson;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.monitor.entity.MonitorIotDeviceEntity;
import io.renren.modules.monitor.entity.ProjectEntity;
import io.renren.modules.monitor.entity.RegionEntity;
import io.renren.modules.monitor.service.MonitorIotDeviceService;
import io.renren.modules.monitor.service.ProjectService;
import io.renren.modules.monitor.service.RegionService;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.entity.SysParkEntity;
import io.renren.modules.sys.service.SysParkService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.monitor.entity.DeviceEntity;
import io.renren.modules.monitor.service.DeviceService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author wangdh
 * @email wang-dehai@baizesoft.com
 * @date 2020-07-11 15:53:14
 */
@RestController
@RequestMapping("monitor/device")
public class DeviceController extends AbstractController {
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private RegionService regionService;
    @Autowired
    private MonitorIotDeviceService iotService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private SysParkService parkService;

    /**
     * 列表
     */
    @RequestMapping("/count")
    public R count(){
        Map<String,Object> map = new HashMap<>(6);
        int projectCount = projectService.selectCount(new EntityWrapper<ProjectEntity>().eq("park_id",getParkId()));
        int regionCount = regionService.selectCount(new EntityWrapper<RegionEntity>().eq("park_id",getParkId()));
        int cameraCount = deviceService.selectCount(new EntityWrapper<DeviceEntity>().eq("park_id",getParkId()));
        int onlineCameraCount = deviceService.selectCount(
                new EntityWrapper<DeviceEntity>()
                        .eq("device_status",1)
                        .eq("park_id",getParkId())
        );
        int offOnlineCameraCount = deviceService.selectCount(
                new EntityWrapper<DeviceEntity>()
                        .eq("device_status",0)
                        .eq("park_id",getParkId())
        );
        int iotDeviceCount = iotService.selectCount(new EntityWrapper<MonitorIotDeviceEntity>().eq("park_id",getParkId()));
        map.put("projectCount",projectCount);//项目数量
        map.put("regionCount",regionCount);//区域数量
        map.put("cameraCount",cameraCount);//摄像头数量
        map.put("onlineCameraCount",onlineCameraCount);//在线摄像头数量
        map.put("offOnlineCameraCount",offOnlineCameraCount);//离线摄像头数量
        map.put("iotDeviceCount",iotDeviceCount);//物联网设备数据点
        return R.ok().put("data", map);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        params.put("park_id",getParkId().toString());
        PageUtils page = deviceService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/getDevList")
    public R getDevList(@RequestParam Map<String, Object> params){
        String projectId = (String) params.get("projectId");
        String regionId = (String) params.get("regionId");
        List<DeviceEntity> list = deviceService.selectList(
                new EntityWrapper<DeviceEntity>()
                        .eq("is_relation",0)
                        .eq(StringUtils.isNotBlank(projectId),"project_id",projectId)
                        .eq(StringUtils.isNotBlank(regionId),"region_id",regionId)
                        .eq("park_id",getParkId())
        );
        return R.ok().put("devList", list);
    }

    /**
     * 大屏展示列表
     */
    @RequestMapping("/showDevList")
    public R showDevList(@RequestParam Map<String, Object> params){
        /*String projectId = (String) params.get("projectId");
        String regionId = (String) params.get("regionId");
        List<DeviceEntity> list = deviceService.selectList(
                new EntityWrapper<DeviceEntity>()
                        .eq(StringUtils.isNotBlank(projectId),"project_id",projectId)
                        .eq(StringUtils.isNotBlank(regionId),"region_id",regionId)
        );
        for(DeviceEntity dev : list){
            if(dev.getRegionId() != null){
                RegionEntity region = regionService.selectOne(new EntityWrapper<RegionEntity>().eq("region_id",dev.getRegionId()));
                if(region != null){
                    dev.setRegionName(region.getRegionName());
                }
            }

        }*/
        params.put("parkId",getParkId());
        List<DeviceEntity> list = deviceService.selectShowDevList(params);
        return R.ok().put("devList", list);
    }

    /**
     * 获取监控url
     */
    @RequestMapping("/getPreviewUrl")
    public R getPreviewUrl(String devId){
        SysParkEntity park = parkService.selectById(getParkId());
        try{
            String cookie = InfoJson.getCookieByLogin(park);
            Map addPreviewChnParams = new HashMap();
            addPreviewChnParams.put("id", devId);
            addPreviewChnParams.put("streamType",1);
            JSONObject rs1 = InfoJson.doPost(park.getWebsite() + Constant.addPreviewChn,addPreviewChnParams,cookie,park);
            if("0".equals(rs1.getString("error_code"))){
                JSONObject result1 = rs1.getJSONObject("result");
                String sessionId = result1.getString("sessionId");
                System.out.println("sessionId:" + sessionId);
                if(StringUtils.isNotBlank(sessionId)){
                    Map previewUrlParams = new HashMap();
                    previewUrlParams.put("id", devId);
                    previewUrlParams.put("sessionId",sessionId);
                    Thread.sleep(1000);
                    JSONObject rs2 = InfoJson.doPost(park.getWebsite() + Constant.getPreviewUrl,previewUrlParams,cookie,park);
                    System.out.println("获取监控结果" + rs2);
                    if("0".equals(rs2.getString("error_code"))){
                        JSONObject result2 = rs2.getJSONObject("result");
                        String url = result2.getString("url");
                        String backupUrl = result2.getString("backupUrl");
                        System.out.println("url:" + url);
                        System.out.println("backupUrl:" + backupUrl);
                        return R.ok().put("url",url);
                    }else{
                        return R.error("获取视频流出错，请重新获取");
                    }
                }
            }
        }catch (Exception ex){
            return R.error("获取视频流出错，请重新获取");
        }
        return R.error("获取视频流出错，请重新获取");
    }
}
