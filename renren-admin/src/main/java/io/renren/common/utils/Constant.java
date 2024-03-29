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

package io.renren.common.utils;

/**
 * 常量
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2016-11-15
 */
public class Constant {
	/** 超级管理员ID */
	public static final int SUPER_ADMIN = 1;
    /** 数据权限过滤 */
	public static final String SQL_FILTER = "sql_filter";


//    public static final String username =  "admin";
//    public static final String password =  "zhny123";
    public static final String userAgent =  "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.116 Safari/537.36";
//    public static final String Referer =  "https://zzzhny.tpddns.cn:8888/";
    /** 登录 */
    public static final String loginUrl = "tums/account/v1/login";
    /** 获取用户根项目 */
    public static final String getRootProjects = "tums/resource/v1/getRootProjects";
    /** 获取子项目 */
    public static final String getProjectChildren = "tums/resource/v1/getProjectChildren";
    /** 获取用户根区域 */
    public static final String getRootRegion = "tums/resource/v1/getRootRegions";
    /** 获取子区域 */
    public static final String getRegionChildren = "tums/resource/v1/getRegionChildren";
    /** 获取设备列表 */
    public static final String getDeviceList = "tums/deviceManager/v1/getDeviceList";
    /** 获取设备详情 */
    public static final String getDeviceInfo = "tums/deviceManager/v1/getDeviceDetails";
    /** 添加预览通道 */
    public static final String addPreviewChn = "tums/preview/v1/addPreviewChn";
    /** 获取监控url */
    public static final String getPreviewUrl = "tums/preview/v1/getPreviewUrl";

	/**
	 * 菜单类型
	 */
    public enum MenuType {
        /**
         * 目录
         */
    	CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * TAB
         */
        TAB(2),
        /**
         * 按钮
         */
        BUTTON(3);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    
    /**
     * 定时任务状态
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
    	NORMAL(0),
        /**
         * 暂停
         */
    	PAUSE(1);

        private int value;

        ScheduleStatus(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return value;
        }
    }

    /**
     * 云服务商
     */
    public enum CloudService {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3);

        private int value;

        CloudService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

}
