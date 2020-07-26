package io.renren.modules.monitor.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.monitor.dao.WeatherDao;
import io.renren.modules.monitor.entity.WeatherEntity;
import io.renren.modules.monitor.service.WeatherService;


@Service("weatherService")
public class WeatherServiceImpl extends ServiceImpl<WeatherDao, WeatherEntity> implements WeatherService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<WeatherEntity> page = this.selectPage(
                new Query<WeatherEntity>(params).getPage(),
                new EntityWrapper<WeatherEntity>()
        );

        return new PageUtils(page);
    }

}
