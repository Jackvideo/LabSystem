package com.jack.LabSystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jack.LabSystem.model.entity.Project;
import com.jack.LabSystem.model.entity.Researchlab;
import com.jack.LabSystem.service.ProjectService;
import com.jack.LabSystem.util.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import com.jack.LabSystem.util.ResultUtil;

import com.jack.LabSystem.service.OutcomeService;
import com.jack.LabSystem.model.entity.Outcome;

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
@RequestMapping("/LabSystem/outcome")
@CrossOrigin
public class OutcomeController {

    @Autowired
    private OutcomeService outcomeService;

    @Autowired
    private ProjectService projectService;

    //查询全部
    @GetMapping("/getAll")
    public ResultUtil findAll() {
        return ResultUtil.success(outcomeService.list());
    }

    //根据id查询
    @GetMapping("/getid={id}")
    public ResultUtil findOne(@PathVariable Integer id) {
        return ResultUtil.success(outcomeService.getById(id));
    }

    //分页查询
    @GetMapping("/page")
    public ResultUtil findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        return ResultUtil.success(outcomeService.page(new Page<>(pageNum, pageSize)));
    }

    @GetMapping("/list")
    public ResultUtil<Map<String,Object>> getOutcomeList(@RequestParam(value = "projectid",required = false) Integer projectid,
                                                     @RequestParam(value = "outcomeid",required = false) Integer outcomeid,
                                                     @RequestParam(value = "outcomename",required = false) String name,
                                                     @RequestParam(value = "achievedate",required = false) String date,
                                                     @RequestParam(value = "type",required = false) String type,
                                                     @RequestParam("pageNo") Long pageNo,
                                                     @RequestParam("pageSize") Long pageSize){
        if(Authority.getAuthority()<1)
            return ResultUtil.fail("用户权限不足");
        LambdaQueryWrapper<Outcome> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(projectid!=null ,Outcome::getProjectid,projectid);
        wrapper.eq(outcomeid!=null ,Outcome::getOutcomeid,outcomeid);
        wrapper.eq(name!=null&&name!="",Outcome::getOutcomename,name);
        wrapper.eq(date!=null&&date!="",Outcome::getAchievedate,date);
        wrapper.eq(type!=null&&type!="",Outcome::getType,type);
        Page<Outcome> page = new Page<>(pageNo,pageSize);
        outcomeService.page(page,wrapper);
        Map<String,Object> data=new HashMap<>();
        data.put("total",page.getTotal());
        data.put("rows",page.getRecords());
        return ResultUtil.success(data);
    }

    //新增接口
    @PostMapping
    public ResultUtil addOutcome(@RequestBody Outcome newOutcome){
        if(Authority.getAuthority()<2)
            return ResultUtil.fail("用户权限不足");
        //外键约束，检查新增结果项目是否存在
        if(projectService.getById(newOutcome.getProjectid())==null)
            return ResultUtil.fail("项目不存在！");
        //重复约束，检查是否已存在项目结果
        Outcome outcome = outcomeService.findByName(newOutcome.getOutcomename());
        if(outcome!=null&&outcome.getOutcomeid()!= newOutcome.getOutcomeid()) {
            return ResultUtil.fail("项目结果已存在");
        }
        outcomeService.save(newOutcome);
        return ResultUtil.success("新增项目结果成功");

    }
    //修改接口
    @PutMapping("/update")
    public ResultUtil updateOutcome(@RequestBody Outcome newOutcome){
        if(Authority.getAuthority()<2)
            return ResultUtil.fail("用户权限不足");
        //外键约束，检查新增结果项目是否存在
        if(projectService.getById(newOutcome.getProjectid())==null)
            return ResultUtil.fail("项目不存在！");
        //重复约束，检查是否已存在项目结果
        Outcome outcome = outcomeService.findByName(newOutcome.getOutcomename());
        if(outcome!=null&&outcome.getOutcomeid()!= newOutcome.getOutcomeid()) {
            return ResultUtil.fail("项目结果已存在");
        }
        outcomeService.updateById(newOutcome);
        return ResultUtil.success("修改项目结果成功");
    }
    //直接物理删除
    @DeleteMapping("/deleteid={id}")
    public ResultUtil deleteOutcome(@PathVariable("id") Integer id){
        if(Authority.getAuthority()<2)
            return ResultUtil.fail("用户权限不足");
        outcomeService.removeById(id);
        return ResultUtil.success("删除项目结果成功");
    }

}

