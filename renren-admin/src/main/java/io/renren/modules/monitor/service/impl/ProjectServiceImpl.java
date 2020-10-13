package io.renren.modules.monitor.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.monitor.dao.ProjectDao;
import io.renren.modules.monitor.entity.ProjectEntity;
import io.renren.modules.monitor.service.ProjectService;


@Service("projectService")
public class ProjectServiceImpl extends ServiceImpl<ProjectDao, ProjectEntity> implements ProjectService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ProjectEntity> page = this.selectPage(
                new Query<ProjectEntity>(params).getPage(),
                new EntityWrapper<ProjectEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void synchronize(ProjectEntity projectEntity) {
        int count = this.selectCount(new EntityWrapper<ProjectEntity>().eq("project_id",projectEntity.getProjectId()).eq("park_id",projectEntity.getParkId()));
        if(count > 0){
            this.update(projectEntity,new EntityWrapper<ProjectEntity>().eq("project_id",projectEntity.getProjectId()).eq("park_id",projectEntity.getParkId()));
        }else{
            this.insert(projectEntity);
        }
    }

}
