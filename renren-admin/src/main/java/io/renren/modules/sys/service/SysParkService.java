package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.SysParkEntity;

import java.util.Map;

/**
 * 
 *
 * @author wangdh
 * @email 594340717@qq.com
 * @date 2020-10-09 15:17:07
 */
public interface SysParkService extends IService<SysParkEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

