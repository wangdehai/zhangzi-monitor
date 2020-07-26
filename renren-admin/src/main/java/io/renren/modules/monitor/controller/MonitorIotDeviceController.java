package io.renren.modules.monitor.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.renren.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.monitor.entity.MonitorIotDeviceEntity;
import io.renren.modules.monitor.service.MonitorIotDeviceService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author wangdh
 * @email 594340717@qq.com
 * @date 2020-07-26 14:14:53
 */
@RestController
@RequestMapping("monitor/monitoriotdevice")
public class MonitorIotDeviceController {
    @Autowired
    private MonitorIotDeviceService monitorIotDeviceService;

    /**
     * 大屏列表
     */
    @RequestMapping("/showList")
    public R showList(String regionId){
        List<MonitorIotDeviceEntity> iotList = monitorIotDeviceService.selectList(
                new EntityWrapper<MonitorIotDeviceEntity>()
                        .eq("region_id",regionId)
        );
        return R.ok().put("iotList", iotList);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("monitor:monitoriotdevice:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = monitorIotDeviceService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{iotId}")
    @RequiresPermissions("monitor:monitoriotdevice:info")
    public R info(@PathVariable("iotId") Long iotId){
        MonitorIotDeviceEntity monitorIotDevice = monitorIotDeviceService.selectById(iotId);

        return R.ok().put("monitorIotDevice", monitorIotDevice);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("monitor:monitoriotdevice:save")
    public R save(@RequestBody MonitorIotDeviceEntity monitorIotDevice){
        monitorIotDeviceService.insert(monitorIotDevice);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("monitor:monitoriotdevice:update")
    public R update(@RequestBody MonitorIotDeviceEntity monitorIotDevice){
        ValidatorUtils.validateEntity(monitorIotDevice);
        monitorIotDeviceService.updateAllColumnById(monitorIotDevice);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("monitor:monitoriotdevice:delete")
    public R delete(@RequestBody Long[] iotIds){
        monitorIotDeviceService.deleteBatchIds(Arrays.asList(iotIds));

        return R.ok();
    }

}
