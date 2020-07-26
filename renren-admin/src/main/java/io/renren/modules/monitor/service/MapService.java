package io.renren.modules.monitor.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.monitor.entity.MapEntity;

import java.util.Map;

/**
 * 
 *
 * @author wangdh
 * @email wang-dehai@baizesoft.com
 * @date 2020-07-11 16:15:01
 */
public interface MapService extends IService<MapEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

