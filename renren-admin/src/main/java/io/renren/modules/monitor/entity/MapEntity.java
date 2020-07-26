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
 * @date 2020-07-11 16:15:01
 */
@TableName("monitor_map")
public class MapEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long mapId;
	/**
	 * 地图名称
	 */
	private String mapName;
	/**
	 * 项目id
	 */
	private String projectId;
	/**
	 * 区域id
	 */
	private String regionId;
	/**
	 * 地图路径
	 */
	private String mapUrl;

	/**
	 * 设置：
	 */
	public void setMapId(Long mapId) {
		this.mapId = mapId;
	}
	/**
	 * 获取：
	 */
	public Long getMapId() {
		return mapId;
	}
	/**
	 * 设置：地图名称
	 */
	public void setMapName(String mapName) {
		this.mapName = mapName;
	}
	/**
	 * 获取：地图名称
	 */
	public String getMapName() {
		return mapName;
	}
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
	 * 设置：地图路径
	 */
	public void setMapUrl(String mapUrl) {
		this.mapUrl = mapUrl;
	}
	/**
	 * 获取：地图路径
	 */
	public String getMapUrl() {
		return mapUrl;
	}
}
