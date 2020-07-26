package io.renren.modules.monitor.controller;

import java.util.Arrays;
import java.util.Map;

import io.renren.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.monitor.entity.WeatherItemEntity;
import io.renren.modules.monitor.service.WeatherItemService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author wangdh
 * @email wang-dehai@baizesoft.com
 * @date 2020-07-20 14:58:50
 */
@RestController
@RequestMapping("monitor/weatheritem")
public class WeatherItemController {
    @Autowired
    private WeatherItemService weatherItemService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("monitor:weatheritem:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = weatherItemService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{weatherItmeId}")
    @RequiresPermissions("monitor:weatheritem:info")
    public R info(@PathVariable("weatherItmeId") Long weatherItmeId){
        WeatherItemEntity weatherItem = weatherItemService.selectById(weatherItmeId);

        return R.ok().put("weatherItem", weatherItem);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("monitor:weatheritem:save")
    public R save(@RequestBody WeatherItemEntity weatherItem){
        weatherItemService.insert(weatherItem);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("monitor:weatheritem:update")
    public R update(@RequestBody WeatherItemEntity weatherItem){
        ValidatorUtils.validateEntity(weatherItem);
        weatherItemService.updateAllColumnById(weatherItem);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("monitor:weatheritem:delete")
    public R delete(@RequestBody Long[] weatherItmeIds){
        weatherItemService.deleteBatchIds(Arrays.asList(weatherItmeIds));

        return R.ok();
    }

}
