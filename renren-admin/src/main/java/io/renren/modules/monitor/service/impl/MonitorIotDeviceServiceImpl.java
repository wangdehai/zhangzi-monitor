package io.renren.modules.monitor.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.monitor.dao.MonitorIotDeviceDao;
import io.renren.modules.monitor.entity.MonitorIotDeviceEntity;
import io.renren.modules.monitor.service.MonitorIotDeviceService;


@Service("monitorIotDeviceService")
public class MonitorIotDeviceServiceImpl extends ServiceImpl<MonitorIotDeviceDao, MonitorIotDeviceEntity> implements MonitorIotDeviceService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<MonitorIotDeviceEntity> page = this.selectPage(
                new Query<MonitorIotDeviceEntity>(params).getPage(),
                new EntityWrapper<MonitorIotDeviceEntity>()
        );

        return new PageUtils(page);
    }

}
