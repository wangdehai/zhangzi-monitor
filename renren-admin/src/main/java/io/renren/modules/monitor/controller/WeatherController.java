package io.renren.modules.monitor.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.monitor.entity.WeatherItemEntity;
import io.renren.modules.monitor.service.WeatherItemService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.monitor.entity.WeatherEntity;
import io.renren.modules.monitor.service.WeatherService;
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
@RequestMapping("monitor/weather")
public class WeatherController {
    @Autowired
    private WeatherService weatherService;
    @Autowired
    private WeatherItemService weatherItemService;

    /**
     * 列表
     */
    @RequestMapping("/getWeather")
    public R getWeather(){
        WeatherEntity weatherEntity = weatherService.selectOne(null);
        List<WeatherItemEntity> itemEntityList = weatherItemService.selectList(null);
        weatherEntity.setWeatherItemList(itemEntityList);
        return R.ok().put("weather", weatherEntity);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("monitor:weather:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = weatherService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{weatherId}")
    @RequiresPermissions("monitor:weather:info")
    public R info(@PathVariable("weatherId") Long weatherId){
        WeatherEntity weather = weatherService.selectById(weatherId);

        return R.ok().put("weather", weather);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("monitor:weather:save")
    public R save(@RequestBody WeatherEntity weather){
        weatherService.insert(weather);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("monitor:weather:update")
    public R update(@RequestBody WeatherEntity weather){
        ValidatorUtils.validateEntity(weather);
        weatherService.updateAllColumnById(weather);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("monitor:weather:delete")
    public R delete(@RequestBody Long[] weatherIds){
        weatherService.deleteBatchIds(Arrays.asList(weatherIds));

        return R.ok();
    }

}
