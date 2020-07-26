package io.renren.modules.monitor.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.monitor.dao.MapDevDao;
import io.renren.modules.monitor.entity.MapDevEntity;
import io.renren.modules.monitor.service.MapDevService;


@Service("montiorMapDevService")
public class MapDevServiceImpl extends ServiceImpl<MapDevDao, MapDevEntity> implements MapDevService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<MapDevEntity> page = this.selectPage(
                new Query<MapDevEntity>(params).getPage(),
                new EntityWrapper<MapDevEntity>()
        );

        return new PageUtils(page);
    }

}
