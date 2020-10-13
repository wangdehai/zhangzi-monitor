package io.renren.modules.monitor.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.renren.common.utils.Constant;
import io.renren.common.utils.InfoJson;
import io.renren.modules.monitor.entity.DeviceEntity;
import io.renren.modules.monitor.entity.RegionEntity;
import io.renren.modules.monitor.service.DeviceService;
import io.renren.modules.monitor.service.RegionService;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.entity.SysParkEntity;
import io.renren.modules.sys.service.SysParkService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.monitor.entity.ProjectEntity;
import io.renren.modules.monitor.service.ProjectService;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author wangdh
 * @email wang-dehai@baizesoft.com
 * @date 2020-07-11 14:29:43
 */
@RestController
@RequestMapping("monitor/project")
public class ProjectController extends AbstractController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private RegionService regionService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private SysParkService parkService;

    /**
     * 根列表
     */
    @RequestMapping("/list")
    public R list(){
        List<ProjectEntity> list = projectService.selectList(new EntityWrapper<ProjectEntity>().eq("park_id",getParkId()));
        return R.ok().put("list", list);
    }

    /**
     * 根列表
     */
    @RequestMapping("/getRootProjects")
    public R getRootProjects(){
        List<ProjectEntity> list = projectService.selectList(
                new EntityWrapper<ProjectEntity>()
                        .eq("project_id","0")
                        .eq("park_id",getParkId())
        );
        return R.ok().put("projectList", list);
    }
    /**
     * 子列表
     */
    @RequestMapping("/getProjectChildren")
    public R getProjectChildren(@RequestParam String projectId){
        List<ProjectEntity> list = projectService.selectList(
                new EntityWrapper<ProjectEntity>()
                        .eq("parent_id",projectId)
                        .eq("park_id",getParkId())
        );
        return R.ok().put("childList", list);
    }

    public String getCookie() {
        SysParkEntity park = parkService.selectById(getParkId());
        return  InfoJson.getCookieByLogin(park);
    }

    /**
     * 同步信息
     */
    @RequestMapping("/synchronize")
    public R synchronize(){
        String cookie = getCookie();
        SysParkEntity park = parkService.selectById(getParkId());
        try{
            //同步项目
            Map rootProjectParams = new HashMap();
            JSONObject rs1 = InfoJson.doPost(park.getWebsite() + Constant.getRootProjects,rootProjectParams,cookie,park);
            if("0".equals(rs1.getString("error_code"))){
                JSONArray projectList = rs1.getJSONArray("result");
                if(projectList != null && projectList.size() > 0){
                    for(Object o : projectList){
                        ProjectEntity projectEntity = JSON.parseObject(JSON.toJSONString(o),ProjectEntity.class);
                        projectEntity.setParkId(park.getParkId());
                        projectService.synchronize(projectEntity);
                        if(projectEntity.getHasChildren() == 1){
                            List<ProjectEntity> childList = new ArrayList<>();
                            //同步子项目
                            childList = getChildProduct(childList,projectEntity,cookie,park);
                            for(ProjectEntity childProject : childList){
                                childProject.setParkId(park.getParkId());
                                projectService.synchronize(childProject);
                            }
                        }
                    }
                }
            }
            //同步区域
            List<ProjectEntity> projectList = projectService.selectList(null);
            if(projectList != null && projectList.size() > 0){
                for (ProjectEntity project : projectList){
                    //同步区域
                    Map rootRegionParams = new HashMap();
                    rootRegionParams.put("projectId",project.getProjectId());
                    rootRegionParams.put("sysType",0);
                    JSONObject rs2 = InfoJson.doPost(park.getWebsite() + Constant.getRootRegion,rootRegionParams,cookie,park);
                    if("0".equals(rs2.getString("error_code"))){
                        JSONArray regionList = rs2.getJSONArray("result");
                        if(regionList != null && regionList.size() > 0){
                            for(Object o : regionList){
                                RegionEntity regionEntity = JSON.parseObject(JSON.toJSONString(o),RegionEntity.class);
                                regionEntity.setParkId(park.getParkId());
                                regionService.synchronize(regionEntity);
                                if(regionEntity.getHasChildren() == 1){
                                    List<RegionEntity> childList = new ArrayList<>();
                                    //同步子项目
                                    childList = getChildRegion(childList,regionEntity,cookie,park);
                                    for(RegionEntity childRegion : childList){
                                        childRegion.setParkId(park.getParkId());
                                        regionService.synchronize(childRegion);
                                    }
                                }
                            }
                        }
                    }
                    //同步设备 1:项目下的设备 2：区域下的设备
                    Map deviceParams = new HashMap();
                    deviceParams.put("start", 1);
                    deviceParams.put("limit",100);
                    Map filterAnd = new HashMap();
                    filterAnd.put("deviceCatagory","video");
                    filterAnd.put("projectId",project.getProjectId());
                    deviceParams.put("filterAnd",filterAnd);
                    JSONObject rs3 = InfoJson.doPost(park.getWebsite() + Constant.getDeviceList,deviceParams,cookie,park);
                    if("0".equals(rs3.getString("error_code"))){
                        JSONArray devList = rs3.getJSONObject("result").getJSONArray("list");
                        if(devList != null && devList.size() > 0){
                            for(Object o : devList){
                                DeviceEntity deviceEntity = JSON.parseObject(JSON.toJSONString(o),DeviceEntity.class);
                                Map deviceInfo = new HashMap();
                                deviceInfo.put("devId", deviceEntity.getDevId());
                                JSONObject rs4 = InfoJson.doPost(park.getWebsite() + Constant.getDeviceInfo,deviceInfo,cookie,park);
                                if("0".equals(rs4.getString("error_code"))){
                                    JSONObject dev = rs4.getJSONObject("result");
                                    deviceEntity = JSON.parseObject(JSON.toJSONString(dev),DeviceEntity.class);
                                    deviceEntity.setParkId(park.getParkId());
                                    deviceService.synchronize(deviceEntity);
                                }
                            }
                        }
                    }
                    List<RegionEntity> regionList = regionService.selectList(null);
                    if(regionList != null && regionList.size() > 0){
                        for(RegionEntity regionEntity : regionList){
                            Map deviceParams1 = new HashMap();
                            deviceParams1.put("start", 1);
                            deviceParams1.put("limit",100);
                            Map filterAnd1 = new HashMap();
                            filterAnd1.put("deviceCatagory","videoPoint");
                            filterAnd1.put("projectId",regionEntity.getParentId());
                            filterAnd1.put("regionId",regionEntity.getRegionId());
                            deviceParams1.put("filterAnd",filterAnd1);
                            JSONObject rs5 = InfoJson.doPost(park.getWebsite() + Constant.getDeviceList,deviceParams1,cookie,park);
                            if("0".equals(rs5.getString("error_code"))){
                                JSONArray devList = rs5.getJSONObject("result").getJSONArray("list");
                                if(devList != null && devList.size() > 0){
                                    for(Object o : devList){
                                        DeviceEntity deviceEntity = JSON.parseObject(JSON.toJSONString(o),DeviceEntity.class);
                                        Map deviceInfo = new HashMap();
                                        deviceInfo.put("devId", deviceEntity.getDevId());
                                        JSONObject rs6 = InfoJson.doPost(park.getWebsite() + Constant.getDeviceInfo,deviceInfo,cookie,park);
                                        if("0".equals(rs6.getString("error_code"))){
                                            JSONObject dev = rs6.getJSONObject("result");
                                            deviceEntity = JSON.parseObject(JSON.toJSONString(dev),DeviceEntity.class);
                                            deviceEntity.setParkId(park.getParkId());
                                            deviceService.synchronize(deviceEntity);
                                        }
                                    }
                                }
                            }
                        }
                    }

                }

            }
        }catch (Exception ex){
            return R.error();
        }
        return R.ok();
    }

    /**
     * 递归获取子项目
     * @param list
     * @param projectEntity
     * @return
     */
    private List<ProjectEntity> getChildProduct(List<ProjectEntity> list, ProjectEntity projectEntity, String cookie, SysParkEntity park){
        //同步子项目
        Map childProjectParams = new HashMap();
        childProjectParams.put("projectId", projectEntity.getProjectId());
        JSONObject rs2 = InfoJson.doPost(park.getWebsite() + Constant.getProjectChildren,childProjectParams,cookie,park);
        if("0".equals(rs2.getString("error_code"))){
            JSONArray projectList = rs2.getJSONArray("result");
            if(projectList != null && projectList.size() > 0){
                for(Object o : projectList){
                    ProjectEntity project = JSON.parseObject(JSON.toJSONString(o),ProjectEntity.class);
                    list.add(project);
                    if(project.getHasChildren() == 1){
                        list = getChildProduct(list,project,cookie,park);
                    }
                }
            }
        }
        return list;
    }

    /**
     * 递归获取子区域
     * @param list
     * @param regionEntity
     * @return
     */
    private List<RegionEntity> getChildRegion(List<RegionEntity> list, RegionEntity regionEntity, String cookie, SysParkEntity park){
        //同步子项目
        Map childRegionParams = new HashMap();
        childRegionParams.put("regionId", regionEntity.getRegionId());
        JSONObject rs2 = InfoJson.doPost(park.getWebsite() + Constant.getRegionChildren,childRegionParams,cookie,park);
        if("0".equals(rs2.getString("error_code"))){
            JSONArray regionList = rs2.getJSONArray("result");
            if(regionList != null && regionList.size() > 0){
                for(Object o : regionList){
                    RegionEntity region = JSON.parseObject(JSON.toJSONString(o),RegionEntity.class);
                    list.add(region);
                    if(region.getHasChildren() == 1){
                        list = getChildRegion(list,region,cookie,park);
                    }
                }
            }
        }
        return list;
    }
}
