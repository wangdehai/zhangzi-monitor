/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.renren.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.Constant;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.sys.dao.SysDeptDao;
import io.renren.modules.sys.entity.SysDeptEntity;
import io.renren.modules.sys.service.SysDeptService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;


@Service("sysDeptService")
public class SysDeptServiceImpl extends ServiceImpl<SysDeptDao, SysDeptEntity> implements SysDeptService {


    @Override
    public PageUtils queryPage(Map<String, Object> params, Long deptId) {
        String vDeptId = (String) params.get("deptId");
        EntityWrapper<SysDeptEntity> wrapper = new EntityWrapper<SysDeptEntity>();
        wrapper.eq(StringUtils.isNotBlank(vDeptId), "dept_id", vDeptId)
                .addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String) params.get(Constant.SQL_FILTER));
        Page<SysDeptEntity> page = this.selectPage(
                new Query<SysDeptEntity>(params).getPage(),
                wrapper
        );
        for (SysDeptEntity sysDeptEntity : page.getRecords()) {
            SysDeptEntity parentDeptEntity = this.selectById(sysDeptEntity.getParentId());
            if (parentDeptEntity != null) {
                sysDeptEntity.setParentName(parentDeptEntity.getName());
            }
        }
        return new PageUtils(page);
    }

    @Override
    public List<SysDeptEntity> queryList(Map<String, Object> params, Long deptId) {
        List<SysDeptEntity> deptList = this.selectList(null);
        return deptList;
    }

    @Override
    public List<Long> queryDetpIdList(Long parentId) {
        return baseMapper.queryDetpIdList(parentId);
    }

    @Override
    public List<Long> getSubDeptIdList(Long deptId) {
        //公司及子公司ID列表
        List<Long> deptIdList = new ArrayList<>();

        //获取子公司ID
        List<Long> subIdList = queryDetpIdList(deptId);
        getDeptTreeList(subIdList, deptIdList);

        return deptIdList;
    }

    /**
     * 递归
     */
    private void getDeptTreeList(List<Long> subIdList, List<Long> deptIdList) {
        for (Long deptId : subIdList) {
            List<Long> list = queryDetpIdList(deptId);
            if (list.size() > 0) {
                getDeptTreeList(list, deptIdList);
            }

            deptIdList.add(deptId);
        }
    }
}
