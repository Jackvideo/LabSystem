package com.jack.LabSystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jack.LabSystem.model.entity.Subproject;
import com.jack.LabSystem.util.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import com.jack.LabSystem.util.ResultUtil;

import com.jack.LabSystem.service.SubprojectService;
import com.jack.LabSystem.model.entity.Subproject;

import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jackvideo
 * @since 2023-11-11 20:23
 */
@RestController
@RequestMapping("/LabSystem/subproject")
public class SubprojectController {

    @Autowired
    private SubprojectService subprojectService;

    //根据id查询
    @GetMapping("/getpid={pid}sid={sid}")
    public ResultUtil findOne(@PathVariable("pid") Integer pid,@PathVariable("sid") Integer sid) {
        QueryWrapper<Subproject> wrapper=new QueryWrapper<>();
        wrapper.eq("Projectid",pid);
        wrapper.eq("Subprojectid",sid);
        return ResultUtil.success(subprojectService.getOne(wrapper));
    }

    //查询全部
    @GetMapping("/list")
    public ResultUtil<Map<String,Object>> getSubprojectList(@RequestParam(value = "projectid",required = false) Integer projectid,
                                                            @RequestParam(value = "subprojectid",required = false) Integer subprojectid,
                                                            @RequestParam(value = "leaderid",required = false) Integer leaderid,
                                                           @RequestParam(value = "deadline",required = false) String ddl,
                                                           @RequestParam(value = "budget",required = false) Double budget,
                                                            @RequestParam(value = "technicalindex",required = false) String index,
                                                           @RequestParam("pageNo") Long pageNo,
                                                           @RequestParam("pageSize") Long pageSize){
        if(Authority.getAuthority()<1)
            return ResultUtil.fail("用户权限不足");
        LambdaQueryWrapper<Subproject> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(subprojectid!=null ,Subproject::getSubprojectid,subprojectid);
        wrapper.eq(projectid!=null ,Subproject::getProjectid,projectid);
        wrapper.eq(leaderid!=null ,Subproject::getLeaderid,leaderid);
        wrapper.eq(ddl!=null&&ddl!="",Subproject::getDeadline,ddl);
        wrapper.eq(budget!=null,Subproject::getBudget,budget);
        wrapper.eq(index!=null&&index!="",Subproject::getTechnicalindex,index);
        Page<Subproject> page = new Page<>(pageNo,pageSize);
        subprojectService.page(page,wrapper);
        Map<String,Object> data=new HashMap<>();
        data.put("total",page.getTotal());
        data.put("rows",page.getRecords());
        return ResultUtil.success(data);
    }
    //新增接口
    @PostMapping
    public ResultUtil addSubproject(@RequestBody Subproject newSubproject){
        if(Authority.getAuthority()<2)
            return ResultUtil.fail("用户权限不足");
        QueryWrapper<Subproject> wrapper=new QueryWrapper<>();
        wrapper.eq("Projectid",newSubproject.getProjectid());
        wrapper.eq("Subprojectid",newSubproject.getSubprojectid());
        Subproject subproject=subprojectService.getOne(wrapper);
        if(subproject!=null&&subproject.getRecordid()!= newSubproject.getRecordid())
            return ResultUtil.fail("子项目已存在");
        subprojectService.save(newSubproject);
        return ResultUtil.success("新增子项目成功");
    }
    //修改接口
    @PutMapping("/update")
    public ResultUtil updateSubproject(@RequestBody Subproject newSubproject){
        if(Authority.getAuthority()<2)
            return ResultUtil.fail("用户权限不足");
        QueryWrapper<Subproject> wrapper=new QueryWrapper<>();
        wrapper.eq("projectid",newSubproject.getProjectid());
        wrapper.eq("subprojectid",newSubproject.getSubprojectid());
        Subproject subproject=subprojectService.getOne(wrapper);
        if(subproject!=null&&subproject.getRecordid()!= newSubproject.getRecordid())
            return ResultUtil.fail("子项目已存在");
        QueryWrapper<Subproject> wrapper2=new QueryWrapper<>();
        wrapper2.eq("recordid",newSubproject.getRecordid());
        subprojectService.update(newSubproject,wrapper2);
        return ResultUtil.success("修改子项目成功");
    }
    //直接物理删除
    @DeleteMapping("/deletepid={pid}sid={sid}")
    public ResultUtil deleteSubproject(@PathVariable("pid") Integer pid,@PathVariable("sid") Integer sid){
        if(Authority.getAuthority()<2)
            return ResultUtil.fail("用户权限不足");
        QueryWrapper<Subproject> wrapper=new QueryWrapper<>();
        wrapper.eq("Projectid",pid);
        wrapper.eq("Subprojectid",sid);
        subprojectService.remove(wrapper);
        return ResultUtil.success("删除子项目成功");
    }

}

