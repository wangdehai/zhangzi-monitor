package io.renren.modules.monitor.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.monitor.entity.ProjectEntity;

import java.util.Map;

/**
 * 
 *
 * @author wangdh
 * @email wang-dehai@baizesoft.com
 * @date 2020-07-11 14:29:43
 */
public interface ProjectService extends IService<ProjectEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void synchronize(ProjectEntity projectEntity);
}

