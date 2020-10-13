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
 * @date 2020-07-20 14:58:50
 */
@TableName("monitor_weather_item")
public class WeatherItemEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long weatherItmeId;
	/**
	 * 日期
	 */
	private String date;
	/**
	 * 温度，最低温/最高温
	 */
	private String temperature;
	/**
	 * 天气情况
	 */
	private String weather;
	/**
	 * 风向
	 */
	private String direct;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 白天
	 */
	private String day;
	/**
	 * 夜间
	 */
	private String night;

	private Long parkId;

	/**
	 * 设置：
	 */
	public void setWeatherItmeId(Long weatherItmeId) {
		this.weatherItmeId = weatherItmeId;
	}
	/**
	 * 获取：
	 */
	public Long getWeatherItmeId() {
		return weatherItmeId;
	}
	/**
	 * 设置：日期
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * 获取：日期
	 */
	public String getDate() {
		return date;
	}
	/**
	 * 设置：温度，最低温/最高温
	 */
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	/**
	 * 获取：温度，最低温/最高温
	 */
	public String getTemperature() {
		return temperature;
	}
	/**
	 * 设置：天气情况
	 */
	public void setWeather(String weather) {
		this.weather = weather;
	}
	/**
	 * 获取：天气情况
	 */
	public String getWeather() {
		return weather;
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

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getNight() {
		return night;
	}

	public void setNight(String night) {
		this.night = night;
	}

	public Long getParkId() {
		return parkId;
	}

	public void setParkId(Long parkId) {
		this.parkId = parkId;
	}
}
