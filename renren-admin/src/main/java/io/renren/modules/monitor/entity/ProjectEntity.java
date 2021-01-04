package io.renren.modules.monitor.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author wangdh
 * @email wang-dehai@baizesoft.com
 * @date 2020-07-11 14:29:43
 */
@TableName("monitor_project")
public class ProjectEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 项目id
	 */
	private String projectId;
	/**
	 * 项目名称
	 */
	private String projectName;
	/**
	 * 项目层级内顺序
	 */
	private Integer order;
	/**
	 * 项目层级
	 */
	private Integer projectLevel;
	/**
	 * 父项目id
	 */
	private String parentId;
	/**
	 * 是否还有子项目，0为无，1为有
	 */
	private Integer hasChildren;

//	private Long parkId;

	/**
	 * 设置：项目id
	 */
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	/**
	 * 获取：项目id
	 */
	public String getProjectId() {
		return projectId;
	}
	/**
	 * 设置：项目名称
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	/**
	 * 获取：项目名称
	 */
	public String getProjectName() {
		return projectName;
	}
	/**
	 * 设置：项目层级内顺序
	 */
	public void setOrder(Integer order) {
		this.order = order;
	}
	/**
	 * 获取：项目层级内顺序
	 */
	public Integer getOrder() {
		return order;
	}
	/**
	 * 设置：项目层级
	 */
	public void setProjectLevel(Integer projectLevel) {
		this.projectLevel = projectLevel;
	}
	/**
	 * 获取：项目层级
	 */
	public Integer getProjectLevel() {
		return projectLevel;
	}
	/**
	 * 设置：父项目id
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	/**
	 * 获取：父项目id
	 */
	public String getParentId() {
		return parentId;
	}
	/**
	 * 设置：是否还有子项目，0为无，1为有
	 */
	public void setHasChildren(Integer hasChildren) {
		this.hasChildren = hasChildren;
	}
	/**
	 * 获取：是否还有子项目，0为无，1为有
	 */
	public Integer getHasChildren() {
		return hasChildren;
	}

/*	public Long getParkId() {
		return parkId;
	}

	public void setParkId(Long parkId) {
		this.parkId = parkId;
	}*/
}
