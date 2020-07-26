package io.renren.modules.monitor.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.monitor.entity.MonitorIotDeviceEntity;

import java.util.Map;

/**
 * 
 *
 * @author wangdh
 * @email 594340717@qq.com
 * @date 2020-07-26 14:14:53
 */
public interface MonitorIotDeviceService extends IService<MonitorIotDeviceEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

