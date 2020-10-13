package io.renren.modules.monitor.entity;

import com.baomidou.mybatisplus.annotations.TableField;
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
@TableName("montior_map_dev")
public class MapDevEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long mapDevId;
	/**
	 * 
	 */
	private Long mapId;
	/**
	 * 地图名称
	 */
	private String mapName;
	/**
	 * 
	 */
	private String devId;
	/**
	 * 
	 */
	private String deviceName;
	/**
	 *
	 */
	private String signName;
	/**
	 * 
	 */
	private String x;
	/**
	 * 
	 */
	private String y;
	/**
	 * 是否主图
	 */
	private Integer isMain;

	/**
	 * 区域id
	 */
	@TableField(exist = false)
	private String regionId;

	private Long parkId;

	private Long linkParkId;
	/**
	 * 设置：
	 */
	public void setMapDevId(Long mapDevId) {
		this.mapDevId = mapDevId;
	}
	/**
	 * 获取：
	 */
	public Long getMapDevId() {
		return mapDevId;
	}
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
	 * 设置：
	 */
	public void setDevId(String devId) {
		this.devId = devId;
	}
	/**
	 * 获取：
	 */
	public String getDevId() {
		return devId;
	}
	/**
	 * 设置：
	 */
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	/**
	 * 获取：
	 */
	public String getDeviceName() {
		return deviceName;
	}
	/**
	 * 设置：
	 */
	public void setX(String x) {
		this.x = x;
	}
	/**
	 * 获取：
	 */
	public String getX() {
		return x;
	}
	/**
	 * 设置：
	 */
	public void setY(String y) {
		this.y = y;
	}
	/**
	 * 获取：
	 */
	public String getY() {
		return y;
	}

	public String getSignName() {
		return signName;
	}

	public void setSignName(String signName) {
		this.signName = signName;
	}

	public Integer getIsMain() {
		return isMain;
	}

	public void setIsMain(Integer isMain) {
		this.isMain = isMain;
	}

	public String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public Long getParkId() {
		return parkId;
	}

	public void setParkId(Long parkId) {
		this.parkId = parkId;
	}

	public Long getLinkParkId() {
		return linkParkId;
	}

	public void setLinkParkId(Long linkParkId) {
		this.linkParkId = linkParkId;
	}
}
