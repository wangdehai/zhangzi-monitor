package io.renren.modules.monitor.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.monitor.entity.RegionEntity;
import io.renren.modules.monitor.service.RegionService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author wangdh
 * @email wang-dehai@baizesoft.com
 * @date 2020-07-11 15:28:43
 */
@RestController
@RequestMapping("monitor/region")
public class RegionController extends AbstractController {
    @Autowired
    private RegionService regionService;
    @Autowired
    private SysUserService userService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam(required = false) String projectId){
        List<RegionEntity> list = regionService.selectList(new EntityWrapper<RegionEntity>()
                .eq(StringUtils.isNotBlank(projectId),"project_id", projectId)
                .eq(!getIsMain(),"park_id",getParkId())
        );
        return R.ok().put("list", list);
    }
}
