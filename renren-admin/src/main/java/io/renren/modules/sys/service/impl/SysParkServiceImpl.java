package io.renren.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.SysParkDao;
import io.renren.modules.sys.entity.SysParkEntity;
import io.renren.modules.sys.service.SysParkService;


@Service("sysParkService")
public class SysParkServiceImpl extends ServiceImpl<SysParkDao, SysParkEntity> implements SysParkService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<SysParkEntity> page = this.selectPage(
                new Query<SysParkEntity>(params).getPage(),
                new EntityWrapper<SysParkEntity>()
        );

        return new PageUtils(page);
    }

}
