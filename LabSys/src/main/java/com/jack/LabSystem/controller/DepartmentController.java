package com.jack.LabSystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jack.LabSystem.model.entity.Department;
import com.jack.LabSystem.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import com.jack.LabSystem.service.DepartmentService;
import com.jack.LabSystem.model.entity.Department;

import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jackvideo
 * @since 2023-11-27 23:27
 */
@RestController
@RequestMapping("/LabSystem/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    //查询全部
    @GetMapping("/list")
    public ResultUtil<Map<String,Object>> getDepartmentList(@RequestParam(value = "departmentid",required = false) Integer departmentid,
                                                            @RequestParam(value = "leaderid",required = false) Integer leaderid,
                                                          @RequestParam(value = "departmentname",required = false) String name,
                                                          @RequestParam(value = "address",required = false) String address,
                                                            @RequestParam(value = "type",required = false) String type,
                                                          @RequestParam("pageNo") Long pageNo,
                                                          @RequestParam("pageSize") Long pageSize){
        LambdaQueryWrapper<Department> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(departmentid!=null ,Department::getDepartmentid,departmentid);
        wrapper.eq(leaderid!=null ,Department::getLeaderid,leaderid);
        wrapper.eq(name!=null&&name!="",Department::getDepartmentname,name);
        wrapper.eq(address!=null&&address!="",Department::getAddress,address);
        wrapper.eq(type!=null&&type!="",Department::getType,type);
        Page<Department> page = new Page<>(pageNo,pageSize);
        departmentService.page(page,wrapper);
        Map<String,Object> data=new HashMap<>();
        data.put("total",page.getTotal());
        data.put("rows",page.getRecords());
        return ResultUtil.success(data);
    }
    //新增接口
    @PostMapping
    public ResultUtil addDepartment(@RequestBody Department newDepartment){
        departmentService.save(newDepartment);
        return ResultUtil.success("新增单位成功");
    }
    //修改接口
    @PutMapping("/update")
    public ResultUtil updateDepartment(@RequestBody Department newDepartment){
        departmentService.updateById(newDepartment);
        return ResultUtil.success("修改单位成功");
    }
    //直接物理删除
    @DeleteMapping("/deleteid={id}")
    public ResultUtil deleteDepartment(@PathVariable("id") Integer id){

        departmentService.removeById(id);
        return ResultUtil.success("删除单位成功");
    }

}

