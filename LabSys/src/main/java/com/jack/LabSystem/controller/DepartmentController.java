package com.jack.LabSystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jack.LabSystem.model.entity.Department;
import com.jack.LabSystem.model.entity.Leader;
import com.jack.LabSystem.service.LeaderService;
import com.jack.LabSystem.util.Authority;
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

    @Autowired
    private LeaderService leaderService;

    //根据id查询
    @GetMapping("/getid={id}")
    public ResultUtil findOne(@PathVariable Integer id) {
        return ResultUtil.success(departmentService.getById(id));
    }

    //查询全部
    @GetMapping("/list")
    public ResultUtil<Map<String,Object>> getDepartmentList(@RequestParam(value = "departmentid",required = false) Integer departmentid,
                                                            @RequestParam(value = "leaderid",required = false) Integer leaderid,
                                                          @RequestParam(value = "departmentname",required = false) String name,
                                                          @RequestParam(value = "address",required = false) String address,
                                                            @RequestParam(value = "type",required = false) String type,
                                                          @RequestParam("pageNo") Long pageNo,
                                                          @RequestParam("pageSize") Long pageSize){
        if(Authority.getAuthority()<1)
            return ResultUtil.fail("用户权限不足");
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
        if(Authority.getAuthority()<2)
            return ResultUtil.fail("用户权限不足");
        //联系人外键约束
        QueryWrapper<Leader> leaderQueryWrapper=new QueryWrapper<>();
        leaderQueryWrapper.eq("leaderid",newDepartment.getLeaderid());
        if(leaderService.getOne(leaderQueryWrapper)==null)
            return ResultUtil.fail("联系人不存在！");
        //唯一性约束
    QueryWrapper<Department> departmentQueryWrapper=new QueryWrapper<>();
    departmentQueryWrapper.eq("departmentname",newDepartment.getDepartmentname());
    Department department=departmentService.getOne(departmentQueryWrapper);
    if(department!=null&&department.getDepartmentid()!=newDepartment.getDepartmentid())
        return ResultUtil.fail("该单位已存在！");
        departmentService.save(newDepartment);
        return ResultUtil.success("新增单位成功");
    }
    //修改接口
    @PutMapping("/update")
    public ResultUtil updateDepartment(@RequestBody Department newDepartment){
        if(Authority.getAuthority()<2)
            return ResultUtil.fail("用户权限不足");
        QueryWrapper<Leader> leaderQueryWrapper=new QueryWrapper<>();
        //联系人外键约束
        leaderQueryWrapper.eq("leaderid",newDepartment.getLeaderid());
        if(leaderService.getOne(leaderQueryWrapper)==null)
            return ResultUtil.fail("联系人不存在！");
        //唯一性约束
        QueryWrapper<Department> departmentQueryWrapper=new QueryWrapper<>();
        departmentQueryWrapper.eq("departmentname",newDepartment.getDepartmentname());
        Department department=departmentService.getOne(departmentQueryWrapper);
        if(department!=null&&department.getDepartmentid()!=newDepartment.getDepartmentid())
            return ResultUtil.fail("该单位已存在！");
        departmentService.updateById(newDepartment);
        return ResultUtil.success("修改单位成功");
    }
    //直接物理删除
    @DeleteMapping("/deleteid={id}")
    public ResultUtil deleteDepartment(@PathVariable("id") Integer id){
        if(Authority.getAuthority()<2)
            return ResultUtil.fail("用户权限不足");
        departmentService.removeById(id);
        return ResultUtil.success("删除单位成功");
    }

}

