package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

    import java.io.Serializable;
import java.util.Date;

/**
 * 
 *
 * @author wangdh
 * @email 594340717@qq.com
 * @date 2020-10-09 15:17:07
 */
@TableName("sys_park")
public class SysParkEntity implements Serializable {
    private static final long serialVersionUID = 1L;

            /**
         * 
         */
                @TableId
            private Long parkId;
            /**
         * 园区名称
         */
            private String parkName;
            /**
         * tp用户名
         */
            private String username;
            /**
         * tp密码
         */
            private String password;
            /**
         * tp域名
         */
            private String website;
    
            /**
         * 设置：
         */
        public void setParkId(Long parkId) {
            this.parkId = parkId;
        }

        /**
         * 获取：
         */
        public Long getParkId() {
            return parkId;
        }
            /**
         * 设置：园区名称
         */
        public void setParkName(String parkName) {
            this.parkName = parkName;
        }

        /**
         * 获取：园区名称
         */
        public String getParkName() {
            return parkName;
        }
            /**
         * 设置：tp用户名
         */
        public void setUsername(String username) {
            this.username = username;
        }

        /**
         * 获取：tp用户名
         */
        public String getUsername() {
            return username;
        }
            /**
         * 设置：tp密码
         */
        public void setPassword(String password) {
            this.password = password;
        }

        /**
         * 获取：tp密码
         */
        public String getPassword() {
            return password;
        }
            /**
         * 设置：tp域名
         */
        public void setWebsite(String website) {
            this.website = website;
        }

        /**
         * 获取：tp域名
         */
        public String getWebsite() {
            return website;
        }
    }
