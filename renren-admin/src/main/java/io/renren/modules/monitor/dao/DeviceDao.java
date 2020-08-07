package io.renren.modules.monitor.dao;

import io.renren.modules.monitor.entity.DeviceEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author wangdh
 * @email wang-dehai@baizesoft.com
 * @date 2020-07-11 15:53:14
 */
public interface DeviceDao extends BaseMapper<DeviceEntity> {
    List<DeviceEntity> selectShowDevList(@Param("params") Map<String, Object> params);
}
