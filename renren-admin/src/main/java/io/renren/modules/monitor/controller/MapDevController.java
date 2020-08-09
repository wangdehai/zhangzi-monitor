package io.renren.modules.monitor.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.monitor.entity.DeviceEntity;
import io.renren.modules.monitor.entity.MapEntity;
import io.renren.modules.monitor.service.DeviceService;
import io.renren.modules.monitor.service.MapService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.monitor.entity.MapDevEntity;
import io.renren.modules.monitor.service.MapDevService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author wangdh
 * @email wang-dehai@baizesoft.com
 * @date 2020-07-11 16:15:01
 */
@RestController
@RequestMapping("monitor/mapdev")
public class MapDevController {
    @Autowired
    private MapDevService montiorMapDevService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private MapService mapService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(Long mapId){
        List<MapDevEntity> list = montiorMapDevService.selectList(
                new EntityWrapper<MapDevEntity>().eq("map_id",mapId).isNull("is_main")
        );
        return R.ok().put("list", list);
    }

    /**
     * 大屏列表
     */
    @RequestMapping("/showList")
    public R showList(Long mapId){
        List<MapDevEntity> list = montiorMapDevService.selectList(
                new EntityWrapper<MapDevEntity>().eq("map_id",mapId).isNotNull("dev_id").isNull("is_main")
        );
        return R.ok().put("list", list);
    }

    /**
     * 新增
     */
    @RequestMapping("/add")
    public R add(@RequestBody MapDevEntity mapDevEntity){
        montiorMapDevService.insert(mapDevEntity);
        return R.ok().put("mapDevId",mapDevEntity.getMapDevId());
    }

    /**
     * 编辑保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody MapDevEntity mapDevEntity){
        if(mapDevEntity.getDevId() != null){
            DeviceEntity deviceEntity = deviceService.selectOne(
                    new EntityWrapper<DeviceEntity>()
                            .eq("dev_id",mapDevEntity.getDevId())
            );
            deviceEntity.setIsRelation(1);
            deviceService.update(deviceEntity,new EntityWrapper<DeviceEntity>().eq("dev_id",deviceEntity.getDevId()));
        }
        montiorMapDevService.insertOrUpdate(mapDevEntity);
        return R.ok();
    }

    /**
     * 删除标记点
     */
    @RequestMapping("/delete")
    public R delete(Long mapDevId){

        MapDevEntity mapDevEntity = montiorMapDevService.selectById(mapDevId);
        if(mapDevEntity.getDevId() != null){
            DeviceEntity deviceEntity = deviceService.selectOne(
                    new EntityWrapper<DeviceEntity>()
                            .eq("dev_id",mapDevEntity.getDevId())
            );
            deviceEntity.setIsRelation(0);
            deviceService.update(deviceEntity,new EntityWrapper<DeviceEntity>().eq("dev_id",deviceEntity.getDevId()));
        }
        montiorMapDevService.deleteById(mapDevId);
        return R.ok();
    }




    /**
     * 主图列表
     */
    @RequestMapping("/mainList")
    public R mainList(){
        List<MapDevEntity> list = montiorMapDevService.selectList(
                new EntityWrapper<MapDevEntity>().eq("is_main",1)
        );
        return R.ok().put("list", list);
    }

    /**
     * 主图大屏列表
     */
    @RequestMapping("/showMainList")
    public R showMainList(){
        List<MapDevEntity> list = montiorMapDevService.selectList(
                new EntityWrapper<MapDevEntity>().eq("is_main",1).isNotNull("map_id")
        );
        if(list != null && list.size() > 0){
            for(MapDevEntity mapDevEntity : list){
                MapEntity map = mapService.selectById(mapDevEntity.getMapId());
                if(map != null){
                    mapDevEntity.setRegionId(map.getRegionId());
                }
            }
        }
        return R.ok().put("list", list);
    }

    /**
     * 新增主图
     */
    @RequestMapping("/addMain")
    public R addMain(@RequestBody MapDevEntity mapDevEntity){
        mapDevEntity.setIsMain(1);
        montiorMapDevService.insert(mapDevEntity);
        return R.ok().put("mapDevId",mapDevEntity.getMapDevId());
    }

    /**
     * 编辑保存主图
     */
    @RequestMapping("/saveMain")
    public R saveMain(@RequestBody MapDevEntity mapDevEntity){
        montiorMapDevService.insertOrUpdate(mapDevEntity);
        return R.ok();
    }

    /**
     * 删除主图标记点
     */
    @RequestMapping("/deleteMain")
    public R deleteMain(Long mapDevId){
        montiorMapDevService.deleteById(mapDevId);
        return R.ok();
    }

}
