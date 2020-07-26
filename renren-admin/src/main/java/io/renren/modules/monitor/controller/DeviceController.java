package io.renren.modules.monitor.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.monitor.entity.RegionEntity;
import io.renren.modules.monitor.service.RegionService;
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
public class DeviceController {
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private RegionService regionService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
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
        );
        return R.ok().put("devList", list);
    }

    /**
     * 大屏展示列表
     */
    @RequestMapping("/showDevList")
    public R showDevList(@RequestParam Map<String, Object> params){
        String projectId = (String) params.get("projectId");
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

        }
        return R.ok().put("devList", list);
    }

    /**
     * 获取监控url
     */
    @RequestMapping("/getPreviewUrl")
    public R getPreviewUrl(){
        return R.ok();
    }
}
