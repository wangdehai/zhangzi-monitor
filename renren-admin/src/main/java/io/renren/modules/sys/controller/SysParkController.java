package io.renren.modules.sys.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.sys.entity.SysParkEntity;
import io.renren.modules.sys.service.SysParkService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author wangdh
 * @email 594340717@qq.com
 * @date 2020-10-09 15:17:07
 */
@RestController
@RequestMapping("sys/syspark")
public class SysParkController extends AbstractController{
    @Autowired
    private SysParkService sysParkService;
    @Autowired
    private SysUserService userService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysParkService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/getList")
    public List<SysParkEntity> getList(){
        List<SysParkEntity> parkList = sysParkService.selectList(null);
        return parkList;
    }


    @RequestMapping("/getParkId")
    public String slectParkId(){
        String parkId = getParkId().toString();
        return parkId;
    }

    @RequestMapping("/swapParkId")
    public void swapParkId(String parkId){
        SysUserEntity user = getUser();
        user.setParkId(Long.valueOf(parkId));
        userService.updateById(user);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{parkId}")
    public R info(@PathVariable("parkId") Long parkId){
        SysParkEntity sysPark = sysParkService.selectById(parkId);

        return R.ok().put("sysPark", sysPark);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody SysParkEntity sysPark){
        sysParkService.insert(sysPark);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody SysParkEntity sysPark){
        ValidatorUtils.validateEntity(sysPark);
        sysParkService.updateAllColumnById(sysPark);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:syspark:delete")
    public R delete(@RequestBody Long[] parkIds){
        sysParkService.deleteBatchIds(Arrays.asList(parkIds));

        return R.ok();
    }

}
