package io.renren.modules.monitor.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * 
 * @author wangdh
 * @email wang-dehai@baizesoft.com
 * @date 2020-07-20 14:58:50
 */
@TableName("monitor_weather")
public class WeatherEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long weatherId;
	/**
	 * 温度
	 */
	private String temperature;
	/**
	 * 湿度
	 */
	private String humidity;
	/**
	 * 天气情况
	 */
	private String info;
	/**
	 * 天气标识id
	 */
	private String wid;
	/**
	 * 风向
	 */
	private String direct;
	/**
	 * 风力
	 */
	private String power;
	/**
	 * 空气质量指数
	 */
	private String aqi;
	/**
	 * 更新时间
	 */
	private Date updateTime;

	private Long parkId;

	@TableField(exist = false)
	private List<WeatherItemEntity> weatherItemList;

	/**
	 * 设置：
	 */
	public void setWeatherId(Long weatherId) {
		this.weatherId = weatherId;
	}
	/**
	 * 获取：
	 */
	public Long getWeatherId() {
		return weatherId;
	}
	/**
	 * 设置：温度
	 */
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	/**
	 * 获取：温度
	 */
	public String getTemperature() {
		return temperature;
	}
	/**
	 * 设置：湿度
	 */
	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}
	/**
	 * 获取：湿度
	 */
	public String getHumidity() {
		return humidity;
	}
	/**
	 * 设置：天气情况
	 */
	public void setInfo(String info) {
		this.info = info;
	}
	/**
	 * 获取：天气情况
	 */
	public String getInfo() {
		return info;
	}
	/**
	 * 设置：天气标识id
	 */
	public void setWid(String wid) {
		this.wid = wid;
	}
	/**
	 * 获取：天气标识id
	 */
	public String getWid() {
		return wid;
	}
	/**
	 * 设置：风向
	 */
	public void setDirect(String direct) {
		this.direct = direct;
	}
	/**
	 * 获取：风向
	 */
	public String getDirect() {
		return direct;
	}
	/**
	 * 设置：风力
	 */
	public void setPower(String power) {
		this.power = power;
	}
	/**
	 * 获取：风力
	 */
	public String getPower() {
		return power;
	}
	/**
	 * 设置：空气质量指数
	 */
	public void setAqi(String aqi) {
		this.aqi = aqi;
	}
	/**
	 * 获取：空气质量指数
	 */
	public String getAqi() {
		return aqi;
	}
	/**
	 * 设置：更新时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：更新时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	public List<WeatherItemEntity> getWeatherItemList() {
		return weatherItemList;
	}

	public void setWeatherItemList(List<WeatherItemEntity> weatherItemList) {
		this.weatherItemList = weatherItemList;
	}

	public Long getParkId() {
		return parkId;
	}

	public void setParkId(Long parkId) {
		this.parkId = parkId;
	}
}
