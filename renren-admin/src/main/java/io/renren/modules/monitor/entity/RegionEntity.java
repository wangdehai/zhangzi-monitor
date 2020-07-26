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
 * @date 2020-07-11 15:28:43
 */
@TableName("monitor_region")
public class RegionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 区域id
	 */
	private String regionId;
	/**
	 * 区域名称
	 */
	private String regionName;
	/**
	 * 项目层级内顺序
	 */
	private Integer order;
	/**
	 * 区域层级
	 */
	private Integer regionLevel;
	/**
	 * 区域类型
	 */
	private Integer sysType;
	/**
	 * 父区域id
	 */
	private String parentId;
	/**
	 * 区域所属项目id
	 */
	private String projectId;
	/**
	 * 是否还有子区域，0为无，1为有
	 */
	private Integer hasChildren;
	/**
	 * 流媒体服务器id
	 */
	private Integer mediaServerId;
	/**
	 * 流媒体服务器名称
	 */
	private String mediaServerName;
	/**
	 * 流媒体服务器Ip地址
	 */
	private String mediaServerIpAddr;

	/**
	 * 设置：区域id
	 */
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	/**
	 * 获取：区域id
	 */
	public String getRegionId() {
		return regionId;
	}
	/**
	 * 设置：区域名称
	 */
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	/**
	 * 获取：区域名称
	 */
	public String getRegionName() {
		return regionName;
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
	 * 设置：区域层级
	 */
	public void setRegionLevel(Integer regionLevel) {
		this.regionLevel = regionLevel;
	}
	/**
	 * 获取：区域层级
	 */
	public Integer getRegionLevel() {
		return regionLevel;
	}
	/**
	 * 设置：区域类型
	 */
	public void setSysType(Integer sysType) {
		this.sysType = sysType;
	}
	/**
	 * 获取：区域类型
	 */
	public Integer getSysType() {
		return sysType;
	}
	/**
	 * 设置：父区域id
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	/**
	 * 获取：父区域id
	 */
	public String getParentId() {
		return parentId;
	}
	/**
	 * 设置：区域所属项目id
	 */
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	/**
	 * 获取：区域所属项目id
	 */
	public String getProjectId() {
		return projectId;
	}
	/**
	 * 设置：是否还有子区域，0为无，1为有
	 */
	public void setHasChildren(Integer hasChildren) {
		this.hasChildren = hasChildren;
	}
	/**
	 * 获取：是否还有子区域，0为无，1为有
	 */
	public Integer getHasChildren() {
		return hasChildren;
	}
	/**
	 * 设置：流媒体服务器id
	 */
	public void setMediaServerId(Integer mediaServerId) {
		this.mediaServerId = mediaServerId;
	}
	/**
	 * 获取：流媒体服务器id
	 */
	public Integer getMediaServerId() {
		return mediaServerId;
	}
	/**
	 * 设置：流媒体服务器名称
	 */
	public void setMediaServerName(String mediaServerName) {
		this.mediaServerName = mediaServerName;
	}
	/**
	 * 获取：流媒体服务器名称
	 */
	public String getMediaServerName() {
		return mediaServerName;
	}
	/**
	 * 设置：流媒体服务器Ip地址
	 */
	public void setMediaServerIpAddr(String mediaServerIpAddr) {
		this.mediaServerIpAddr = mediaServerIpAddr;
	}
	/**
	 * 获取：流媒体服务器Ip地址
	 */
	public String getMediaServerIpAddr() {
		return mediaServerIpAddr;
	}
}
