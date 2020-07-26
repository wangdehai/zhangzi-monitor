package io.renren.modules.monitor.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.monitor.entity.DeviceEntity;
import io.renren.modules.monitor.entity.RegionEntity;

import java.util.Map;

/**
 * 
 *
 * @author wangdh
 * @email wang-dehai@baizesoft.com
 * @date 2020-07-11 15:53:14
 */
public interface DeviceService extends IService<DeviceEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void synchronize(DeviceEntity devEntity);
}

