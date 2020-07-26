package io.renren.modules.monitor.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.monitor.entity.ProjectEntity;
import io.renren.modules.monitor.entity.RegionEntity;

import java.util.Map;

/**
 * 
 *
 * @author wangdh
 * @email wang-dehai@baizesoft.com
 * @date 2020-07-11 15:28:43
 */
public interface RegionService extends IService<RegionEntity> {

    PageUtils queryPage(Map<String, Object> params);
    void synchronize(RegionEntity regionEntity);
}

