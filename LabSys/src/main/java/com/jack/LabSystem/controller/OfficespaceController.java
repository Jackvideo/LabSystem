package com.jack.LabSystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jack.LabSystem.model.entity.Officespace;
import com.jack.LabSystem.model.entity.Researchlab;
import com.jack.LabSystem.service.ResearchlabService;
import com.jack.LabSystem.util.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import com.jack.LabSystem.util.ResultUtil;
import com.jack.LabSystem.service.OfficespaceService;
import com.jack.LabSystem.model.entity.Officespace;
import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jackvideo
 * @since 2023-11-12 10:48
 */
@RestController
@RequestMapping("/LabSystem/officespace")
public class OfficespaceController {

    @Autowired
    private OfficespaceService officespaceService;

    @Autowired
    private ResearchlabService researchlabService;

    //查询全部
    @GetMapping("/getAll")
    public ResultUtil findAll() {
        return ResultUtil.success(officespaceService.list());
    }

    //根据id查询
    @GetMapping("/getid={id}")
    public ResultUtil findOne(@PathVariable Integer id) {
        return ResultUtil.success(officespaceService.getById(id));
    }

    //分页查询
    @GetMapping("/page")
    public ResultUtil findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        return ResultUtil.success(officespaceService.page(new Page<>(pageNum, pageSize)));
    }

    @GetMapping("/list")
    public ResultUtil<Map<String,Object>> getOfficespaceList(@RequestParam(value = "spaceid",required = false) Integer Spaceid,
                                                             @RequestParam(value = "labid",required = false) Integer Labid,
                                                         @RequestParam(value = "spacename",required = false) String name,
                                                         @RequestParam(value = "area",required = false) Double area,
                                                         @RequestParam(value = "address",required = false) String address,
                                                         @RequestParam("pageNo") Long pageNo,
                                                         @RequestParam("pageSize") Long pageSize){
        if(Authority.getAuthority()<1)
            return ResultUtil.fail("用户权限不足");
        LambdaQueryWrapper<Officespace> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Spaceid!=null ,Officespace::getSpaceid,Spaceid);
        wrapper.eq(Labid!=null ,Officespace::getLabid,Labid);
        wrapper.eq(name!=null&&name!="",Officespace::getSpacename,name);
        wrapper.eq(area!=null,Officespace::getArea,area);
        wrapper.eq(address!=null&&address!="",Officespace::getAddress,address);
        Page<Officespace> page = new Page<>(pageNo,pageSize);
        officespaceService.page(page,wrapper);
        Map<String,Object> data=new HashMap<>();
        data.put("total",page.getTotal());
        data.put("rows",page.getRecords());
        return ResultUtil.success(data);
    }

    //新增接口
    @PostMapping
    public ResultUtil addOfficespace(@RequestBody Officespace newOfficespace){if(Authority.getAuthority()<2)
        return ResultUtil.fail("用户权限不足");

        //外键约束，检查新增研究室是否存在
        if(newOfficespace.getLabid()!=null&&researchlabService.getById(newOfficespace.getLabid())==null)
            return ResultUtil.fail("研究室不存在！");
        //重复约束，检查是否已有场地
        Officespace officespace = officespaceService.findByName(newOfficespace.getSpacename());
        if(officespace!=null&&officespace.getSpaceid()!= newOfficespace.getSpaceid()) {
            return ResultUtil.fail("该场地已存在");
        }
        officespaceService.save(newOfficespace);
        return ResultUtil.success("新增场地成功");

    }
    //修改接口
    @PutMapping("/update")
    public ResultUtil updateOfficespace(@RequestBody Officespace newOfficespace){
        if(Authority.getAuthority()<2)
            return ResultUtil.fail("用户权限不足");
        //外键约束，检查新增研究室是否存在
        if(newOfficespace.getLabid()!=null&&researchlabService.getById(newOfficespace.getLabid())==null)
            return ResultUtil.fail("研究室不存在！");
        //重复约束，检查是否已有场地
        Officespace officespace = officespaceService.findByName(newOfficespace.getSpacename());
        if(officespace!=null&&officespace.getSpaceid()!= newOfficespace.getSpaceid()) {
            return ResultUtil.fail("该场地已存在");
        }
        officespaceService.updateById(newOfficespace);
        return ResultUtil.success("修改场地成功");
    }
    //直接物理删除
    @DeleteMapping("/deleteid={id}")
    public ResultUtil deleteOfficespace(@PathVariable("id") Integer id){
        if(Authority.getAuthority()<2)
            return ResultUtil.fail("用户权限不足");
        officespaceService.removeById(id);

        return ResultUtil.success("删除场地成功");
    }

}

