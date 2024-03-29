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

package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import io.renren.common.validator.group.AddGroup;
import io.renren.common.validator.group.UpdateGroup;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * 公司管理
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-06-20 15:23:47
 */
@TableName("sys_dept")
public class SysDeptEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //公司ID
    @TableId
    private Long deptId;
    //上级公司ID，一级公司为0
    private Long parentId;
    //公司名称
    @NotBlank(message = "公司名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String name;
    //上级公司名称
    @TableField(exist = false)
    private String parentName;

    @TableLogic
    private Integer delFlag;
    /**
     * ztree属性
     */
    @TableField(exist = false)
    private Boolean open;
    @TableField(exist = false)
    private List<?> list;


    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getDeptId() {
        return deptId;
    }

    /**
     * 设置：上级公司ID，一级公司为0
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取：上级公司ID，一级公司为0
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 设置：公司名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取：公司名称
     */
    public String getName() {
        return name;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public List<?> getList() {
        return list;
    }
    public void setList(List<?> list) {
        this.list = list;
    }
}
