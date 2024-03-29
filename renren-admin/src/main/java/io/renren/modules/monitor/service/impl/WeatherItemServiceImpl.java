package io.renren.modules.monitor.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.monitor.dao.WeatherItemDao;
import io.renren.modules.monitor.entity.WeatherItemEntity;
import io.renren.modules.monitor.service.WeatherItemService;


@Service("weatherItemService")
public class WeatherItemServiceImpl extends ServiceImpl<WeatherItemDao, WeatherItemEntity> implements WeatherItemService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<WeatherItemEntity> page = this.selectPage(
                new Query<WeatherItemEntity>(params).getPage(),
                new EntityWrapper<WeatherItemEntity>()
        );

        return new PageUtils(page);
    }

}
