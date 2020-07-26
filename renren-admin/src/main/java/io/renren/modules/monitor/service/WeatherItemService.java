package io.renren.modules.monitor.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.monitor.entity.WeatherItemEntity;

import java.util.Map;

/**
 * 
 *
 * @author wangdh
 * @email wang-dehai@baizesoft.com
 * @date 2020-07-20 14:58:50
 */
public interface WeatherItemService extends IService<WeatherItemEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

