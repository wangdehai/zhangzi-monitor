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

package io.renren.modules.sys.service;


import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.SysDeptEntity;

import java.util.List;
import java.util.Map;

/**
 * 公司管理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-06-20 15:23:47
 */
public interface SysDeptService extends IService<SysDeptEntity> {


	PageUtils queryPage(Map<String, Object> params, Long deptId);

	List<SysDeptEntity> queryList(Map<String, Object> map, Long deptId);

	/**
	 * 查询子公司ID列表
	 * @param parentId  上级公司ID
	 */
	List<Long> queryDetpIdList(Long parentId);

	/**
	 * 获取子公司ID，用于数据过滤
	 */
	List<Long> getSubDeptIdList(Long deptId);


}
