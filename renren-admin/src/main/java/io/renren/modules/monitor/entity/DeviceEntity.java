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
 * @date 2020-07-11 15:53:14
 */
@TableName("monitor_device")
public class DeviceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 设备id
	 */
	private String devId;
	/**
	 * ip地址
	 */
	private String ip;
	/**
	 * 设备名称
	 */
	private String deviceName;
	/**
	 * 设备状态
	 */
	private Integer deviceStatus;
	/**
	 * 项目id
	 */
	private String projectId;
	/**
	 * 区域id
	 */
	private String regionId;
	/**
	 * 设备类型
	 */
	private String deviceType;
	/**
	 * 设备型号
	 */
	private String deviceModel;
	/**
	 * MAC地址
	 */
	private String ethernet;
	/**
	 * 是否关联（0：未，1：已）
	 */
	private Integer isRelation;
	/**
	 * 区域名称
	 */
	@TableField(exist = false)
	private String regionName;
	private Long parkId;
	/**
	 * 设置：设备id
	 */
	public void setDevId(String devId) {
		this.devId = devId;
	}
	/**
	 * 获取：设备id
	 */
	public String getDevId() {
		return devId;
	}
	/**
	 * 设置：ip地址
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	 * 获取：ip地址
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * 设置：设备名称
	 */
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	/**
	 * 获取：设备名称
	 */
	public String getDeviceName() {
		return deviceName;
	}
	/**
	 * 设置：设备状态
	 */
	public void setDeviceStatus(Integer deviceStatus) {
		this.deviceStatus = deviceStatus;
	}
	/**
	 * 获取：设备状态
	 */
	public Integer getDeviceStatus() {
		return deviceStatus;
	}

	public Integer getIsRelation() {
		return isRelation;
	}

	public void setIsRelation(Integer isRelation) {
		this.isRelation = isRelation;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public String getEthernet() {
		return ethernet;
	}

	public void setEthernet(String ethernet) {
		this.ethernet = ethernet;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public Long getParkId() {
		return parkId;
	}

	public void setParkId(Long parkId) {
		this.parkId = parkId;
	}
}
