package com.jack.LabSystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jack.LabSystem.model.entity.Subproject;
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
    @GetMapping("/getid={id}")
    public ResultUtil findOne(@PathVariable Integer id) {
        return ResultUtil.success(subprojectService.getById(id));
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
        subprojectService.save(newSubproject);
        return ResultUtil.success("新增子项目成功");
    }
    //修改接口
    @PutMapping("/update")
    public ResultUtil updateSubproject(@RequestBody Subproject newSubproject){
        subprojectService.updateById(newSubproject);
        return ResultUtil.success("修改子项目成功");
    }
    //直接物理删除
    @DeleteMapping("/deleteid={id}")
    public ResultUtil deleteSubproject(@PathVariable("id") Integer id){

        subprojectService.removeById(id);
        return ResultUtil.success("删除子项目成功");
    }

}

