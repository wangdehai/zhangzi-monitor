package io.renren.modules.monitor.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wangdh
 * @email 594340717@qq.com
 * @date 2020-07-26 14:14:53
 */
@TableName("monitor_iot_device")
public class MonitorIotDeviceEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Long iotId;
    /**
     *
     */
    private String devNum;
    /**
     * 测量项
     */
    private String devKey;
    /**
     * 测量值
     */
    private String value;
    /**
     * 区域id
     */
    private String regionId;
    /**
     * 更新时间
     */
    private Date updateTime;

    private Long parkId;

    /**
     * 设置：
     */
    public void setIotId(Long iotId) {
        this.iotId = iotId;
    }

    /**
     * 获取：
     */
    public Long getIotId() {
        return iotId;
    }

    /**
     * 设置：
     */
    public void setDevNum(String devNum) {
        this.devNum = devNum;
    }

    /**
     * 获取：
     */
    public String getDevNum() {
        return devNum;
    }

    public String getDevKey() {
        return devKey;
    }

    public void setDevKey(String devKey) {
        this.devKey = devKey;
    }

    /**
     * 设置：测量值
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 获取：测量值
     */
    public String getValue() {
        return value;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getParkId() {
        return parkId;
    }

    public void setParkId(Long parkId) {
        this.parkId = parkId;
    }
}
