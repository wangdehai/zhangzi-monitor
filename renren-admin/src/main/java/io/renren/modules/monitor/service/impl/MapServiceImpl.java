package io.renren.modules.monitor.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.monitor.dao.MapDao;
import io.renren.modules.monitor.entity.MapEntity;
import io.renren.modules.monitor.service.MapService;


@Service("mapService")
public class MapServiceImpl extends ServiceImpl<MapDao, MapEntity> implements MapService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<MapEntity> page = this.selectPage(
                new Query<MapEntity>(params).getPage(),
                new EntityWrapper<MapEntity>()
        );

        return new PageUtils(page);
    }

}
