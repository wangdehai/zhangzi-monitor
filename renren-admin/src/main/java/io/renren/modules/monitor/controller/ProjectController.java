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
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private RegionService regionService;
    @Autowired
    private DeviceService deviceService;

    /**
     * 根列表
     */
    @RequestMapping("/list")
    public R list(){
        List<ProjectEntity> list = projectService.selectList(null);
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
        );
        return R.ok().put("childList", list);
    }

    public String getCookie() {
        return  InfoJson.getCookieByLogin(Constant.loginUrl);
    }

    /**
     * 同步信息
     */
    @RequestMapping("/synchronize")
    public R synchronize(){
        String cookie = getCookie();
        try{
            //同步项目
            Map rootProjectParams = new HashMap();
            JSONObject rs1 = InfoJson.doPost(Constant.getRootProjects,rootProjectParams,cookie);
            if("0".equals(rs1.getString("error_code"))){
                JSONArray projectList = rs1.getJSONArray("result");
                if(projectList != null && projectList.size() > 0){
                    for(Object o : projectList){
                        ProjectEntity projectEntity = JSON.parseObject(JSON.toJSONString(o),ProjectEntity.class);
                        projectService.synchronize(projectEntity);
                        if(projectEntity.getHasChildren() == 1){
                            List<ProjectEntity> childList = new ArrayList<>();
                            //同步子项目
                            childList = getChildProduct(childList,projectEntity,cookie);
                            for(ProjectEntity childProject : childList){
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
                    JSONObject rs2 = InfoJson.doPost(Constant.getRootRegion,rootRegionParams,cookie);
                    if("0".equals(rs2.getString("error_code"))){
                        JSONArray regionList = rs2.getJSONArray("result");
                        if(regionList != null && regionList.size() > 0){
                            for(Object o : regionList){
                                RegionEntity regionEntity = JSON.parseObject(JSON.toJSONString(o),RegionEntity.class);
                                regionService.synchronize(regionEntity);
                                if(regionEntity.getHasChildren() == 1){
                                    List<RegionEntity> childList = new ArrayList<>();
                                    //同步子项目
                                    childList = getChildRegion(childList,regionEntity,cookie);
                                    for(RegionEntity childRegion : childList){
                                        regionService.synchronize(childRegion);
                                    }
                                }
                            }
                        }
                    }
                    //同步设备
                    Map deviceParams = new HashMap();
                    deviceParams.put("start", 1);
                    deviceParams.put("limit",100);
                    Map filterAnd = new HashMap();
                    filterAnd.put("deviceCatagory","video");
                    filterAnd.put("projectId","1");
                    deviceParams.put("filterAnd",filterAnd);
                    JSONObject rs3 = InfoJson.doPost(Constant.getDeviceList,deviceParams,cookie);
                    if("0".equals(rs3.getString("error_code"))){
                        JSONArray devList = rs3.getJSONObject("result").getJSONArray("list");
                        if(devList != null && devList.size() > 0){
                            for(Object o : devList){
                                DeviceEntity deviceEntity = JSON.parseObject(JSON.toJSONString(o),DeviceEntity.class);
                                Map deviceInfo = new HashMap();
                                deviceInfo.put("devId", deviceEntity.getDevId());
                                JSONObject rs4 = InfoJson.doPost(Constant.getDeviceInfo,deviceInfo,cookie);
                                if("0".equals(rs4.getString("error_code"))){
                                    JSONObject dev = rs4.getJSONObject("result");
                                    deviceEntity = JSON.parseObject(JSON.toJSONString(dev),DeviceEntity.class);
                                    deviceService.synchronize(deviceEntity);
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
    private List<ProjectEntity> getChildProduct(List<ProjectEntity> list, ProjectEntity projectEntity, String cookie){
        //同步子项目
        Map childProjectParams = new HashMap();
        childProjectParams.put("projectId", projectEntity.getProjectId());
        JSONObject rs2 = InfoJson.doPost(Constant.getProjectChildren,childProjectParams,cookie);
        if("0".equals(rs2.getString("error_code"))){
            JSONArray projectList = rs2.getJSONArray("result");
            if(projectList != null && projectList.size() > 0){
                for(Object o : projectList){
                    ProjectEntity project = JSON.parseObject(JSON.toJSONString(o),ProjectEntity.class);
                    list.add(project);
                    if(project.getHasChildren() == 1){
                        list = getChildProduct(list,project,cookie);
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
    private List<RegionEntity> getChildRegion(List<RegionEntity> list, RegionEntity regionEntity, String cookie){
        //同步子项目
        Map childRegionParams = new HashMap();
        childRegionParams.put("regionId", regionEntity.getRegionId());
        JSONObject rs2 = InfoJson.doPost(Constant.getRegionChildren,childRegionParams,cookie);
        if("0".equals(rs2.getString("error_code"))){
            JSONArray regionList = rs2.getJSONArray("result");
            if(regionList != null && regionList.size() > 0){
                for(Object o : regionList){
                    RegionEntity region = JSON.parseObject(JSON.toJSONString(o),RegionEntity.class);
                    list.add(region);
                    if(region.getHasChildren() == 1){
                        list = getChildRegion(list,region,cookie);
                    }
                }
            }
        }
        return list;
    }
}
