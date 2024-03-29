package io.renren.modules.monitor.service.impl;

import io.renren.modules.monitor.entity.RegionEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.monitor.dao.DeviceDao;
import io.renren.modules.monitor.entity.DeviceEntity;
import io.renren.modules.monitor.service.DeviceService;


@Service("deviceService")
public class DeviceServiceImpl extends ServiceImpl<DeviceDao, DeviceEntity> implements DeviceService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String projectId = (String) params.get("projectId");
        String regionId = (String) params.get("regionId");
        String parkId = (String) params.get("park_id");
        Boolean isMain = (Boolean) params.get("is_main");
        Page<DeviceEntity> page = this.selectPage(
                new Query<DeviceEntity>(params).getPage(),
                new EntityWrapper<DeviceEntity>()
                        .eq(StringUtils.isNotBlank(projectId),"project_id",projectId)
                        .eq(StringUtils.isNotBlank(regionId),"region_id",regionId)
                        .eq(!isMain,"park_id",parkId)
        );

        return new PageUtils(page);
    }

    @Override
    public void synchronize(DeviceEntity devEntity) {
        int count = this.selectCount(new EntityWrapper<DeviceEntity>().eq("dev_id",devEntity.getDevId()).eq("park_id",devEntity.getParkId()));
        if(count > 0){
            this.update(devEntity,new EntityWrapper<DeviceEntity>().eq("dev_id",devEntity.getDevId()).eq("park_id",devEntity.getParkId()));
        }else{
            devEntity.setIsRelation(0);
            this.insert(devEntity);
        }
    }

    @Override
    public List<DeviceEntity> selectShowDevList(Map<String, Object> params) {
        return this.baseMapper.selectShowDevList(params);
    }
}
