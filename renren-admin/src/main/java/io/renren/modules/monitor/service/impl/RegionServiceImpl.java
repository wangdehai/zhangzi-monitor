package io.renren.modules.monitor.service.impl;

import io.renren.modules.monitor.entity.RegionEntity;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.monitor.dao.RegionDao;
import io.renren.modules.monitor.service.RegionService;


@Service("regionService")
public class RegionServiceImpl extends ServiceImpl<RegionDao, RegionEntity> implements RegionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<RegionEntity> page = this.selectPage(
                new Query<RegionEntity>(params).getPage(),
                new EntityWrapper<RegionEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void synchronize(RegionEntity regionEntity) {
        int count = this.selectCount(new EntityWrapper<RegionEntity>().eq("region_id",regionEntity.getRegionId()));
        if(count > 0){
            this.update(regionEntity,new EntityWrapper<RegionEntity>().eq("region_id",regionEntity.getRegionId()));
        }else{
            this.insert(regionEntity);
        }
    }

}
