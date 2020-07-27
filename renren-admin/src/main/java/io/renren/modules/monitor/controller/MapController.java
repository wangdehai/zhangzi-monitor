package io.renren.modules.monitor.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.monitor.entity.DeviceEntity;
import io.renren.modules.monitor.entity.MapDevEntity;
import io.renren.modules.monitor.service.DeviceService;
import io.renren.modules.monitor.service.MapDevService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.renren.modules.monitor.entity.MapEntity;
import io.renren.modules.monitor.service.MapService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import org.springframework.web.multipart.MultipartFile;


/**
 * 
 *
 * @author wangdh
 * @email wang-dehai@baizesoft.com
 * @date 2020-07-11 16:15:01
 */
@RestController
@RequestMapping("monitor/map")
public class MapController {
    @Autowired
    private MapService mapService;
    @Autowired
    private MapDevService montiorMapDevService;
    @Autowired
    private DeviceService deviceService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        String projectId = (String) params.get("projectId");
        String regionId = (String) params.get("regionId");
        List<MapEntity> list = mapService.selectList(
                new EntityWrapper<MapEntity>()
                        .eq(StringUtils.isNotBlank(projectId),"project_id",projectId)
                        .eq(StringUtils.isNotBlank(regionId),"region_id",regionId)
        );
        return R.ok().put("list", list);
    }

    /**
     * 检测图片是否存在
     */
    @RequestMapping("/checkMap")
    public R checkMap(Long regionId){
        List<MapEntity> mapList = mapService.selectList(new EntityWrapper<MapEntity>().eq("region_id",regionId));
        if(mapList != null && mapList.size() > 0){
            return R.error("该区域已存在图片");
        }
        return R.ok();
    }

    /**
     * 添加图片
     * @param file
     * @param projectId
     * @param regionId
     * @return
     * @throws Exception
     */
    @RequestMapping("/uploadMap")
    @ResponseBody
    public R uploadMap(@RequestParam(value = "file") MultipartFile file,
                    @RequestParam(value = "projectId") String projectId,
                    @RequestParam(value = "regionId") String regionId) throws Exception {
        //判断文件是否为空
        try {
            if (file.isEmpty()) {
                return R.error("上传文件不能为空");
            }
            // 上传文件夹获取文件名
            String fileName = file.getOriginalFilename();
            // 获取上传文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            if (!suffixName.equalsIgnoreCase(".jpg") && !suffixName.equalsIgnoreCase(".png")) {
                return R.error(1, "文件格式不正确，请上传图片格式的文件");
            }
            // 构建上传文件的存放 "文件夹" 路径
            String fileDirPath = new String("renren-admin/src/main/resources/statics/img");
            File fileDir = new File(fileDirPath);
            if(!fileDir.exists()){
                // 递归生成文件夹
                fileDir.mkdirs();
            }
            String currentTime = String.valueOf(System.currentTimeMillis());
            String filePath = fileDir.getAbsolutePath() + File.separator + currentTime + suffixName;
//            String filePath1 = "/usr/linshi/images/" + System.currentTimeMillis() + suffixName;
            File dest = new File(filePath);
            if (!dest.getParentFile().exists()) {
                // 检测是否存在目录不存在创建一个文件
                dest.getParentFile().mkdirs();
            }
            //上传图片到本地
            file.transferTo(dest);
            MapEntity mapEntity = new MapEntity();
            mapEntity.setMapName(currentTime);
            mapEntity.setMapUrl(currentTime + suffixName);
            mapEntity.setProjectId(projectId);
            mapEntity.setRegionId(regionId);
            mapService.insert(mapEntity);
            return R.ok("上传成功").put("src",currentTime + suffixName);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.error(1, "上传失败");
    }

    /**
     * 删除图片
     */
    @RequestMapping("/delete")
    public R delete(Long mapId){
        mapService.deleteById(mapId);
        List<MapDevEntity> mapDevList = montiorMapDevService.selectList(
                new EntityWrapper<MapDevEntity>()
                        .eq("map_id",mapId)
        );
        if(mapDevList != null && mapDevList.size() > 0){
            for(MapDevEntity devMap : mapDevList){
                if(devMap.getDevId() != null){
                    DeviceEntity deviceEntity = deviceService.selectOne(new EntityWrapper<DeviceEntity>().eq("dev_id",devMap.getDevId()));
                    deviceEntity.setIsRelation(0);
                    deviceService.update(deviceEntity,new EntityWrapper<DeviceEntity>().eq("dev_id",deviceEntity.getDevId()));
                }
            }

            montiorMapDevService.delete(
                    new EntityWrapper<MapDevEntity>()
                            .eq("map_id",mapId)
            );
        }
        return R.ok();
    }

}
