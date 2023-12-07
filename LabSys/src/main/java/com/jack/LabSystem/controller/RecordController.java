package com.jack.LabSystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jack.LabSystem.model.entity.Project;
import com.jack.LabSystem.model.entity.Record;
import com.jack.LabSystem.service.ProjectService;
import com.jack.LabSystem.service.ResearcherService;
import com.jack.LabSystem.util.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import com.jack.LabSystem.util.ResultUtil;

import com.jack.LabSystem.service.RecordService;
import com.jack.LabSystem.model.entity.Record;

import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jackvideo
 * @since 2023-11-12 10:46
 */
@RestController
@RequestMapping("/LabSystem/record")
public class RecordController {

    @Autowired
    private RecordService recordService;
    @Autowired
    private ResearcherService researcherService;
    @Autowired
    private ProjectService projectService;

    //根据id查询
    @GetMapping("/getpid={pid}&rid={rid}")
    public ResultUtil findOne(@PathVariable("pid") Integer pid,@PathVariable("rid") Integer rid) {
        QueryWrapper<Record> wrapper=new QueryWrapper<>();
        wrapper.eq("projectid",pid);
        wrapper.eq("researcherid",rid);
        return ResultUtil.success(recordService.getOne(wrapper));
    }

    @GetMapping("/list")
    public ResultUtil<Map<String,Object>> getRecordList(@RequestParam(value = "researcherid",required = false) Integer rid,
                                                     @RequestParam(value = "projectid",required = false) Integer pid,
                                                     @RequestParam(value = "participationdate",required = false) String date,
                                                     @RequestParam(value = "workload",required = false) String workload,
                                                     @RequestParam(value = "allocatedfund", required = false) String fund,
                                                     @RequestParam("pageNo") Long pageNo,
                                                     @RequestParam("pageSize") Long pageSize){
        if(Authority.getAuthority()<1)
            return ResultUtil.fail("用户权限不足");
        LambdaQueryWrapper<Record> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(rid!=null ,Record::getResearcherid,rid);
        wrapper.eq(pid!=null ,Record::getProjectid,pid);
        wrapper.eq(date!=null&&date!="",Record::getParticipationdate,date);
        wrapper.eq(workload!=null&&workload!="",Record::getWorkload,workload);
        wrapper.eq(fund!=null&&fund!="",Record::getAllocatedfund,fund);
        Page<Record> page = new Page<>(pageNo,pageSize);
        recordService.page(page,wrapper);
        Map<String,Object> data=new HashMap<>();
        data.put("total",page.getTotal());
        data.put("rows",page.getRecords());
        return ResultUtil.success(data);
    }

    //新增接口
    @PostMapping
    public ResultUtil addRecord(@RequestBody Record recorder){
        if(Authority.getAuthority()<2)
            return ResultUtil.fail("用户权限不足");
        //外键约束
        if(researcherService.getById(recorder.getResearcherid())==null||projectService.getById(recorder.getProjectid())==null)
            return  ResultUtil.fail("信息不存在！违反外键约束");
            recordService.save(recorder);
            return ResultUtil.success("新增工作记录成功");
    }
    //修改接口
    @PutMapping("/update")
    public ResultUtil updateRecord(@RequestBody Record recorder){
        if(Authority.getAuthority()<2)
            return ResultUtil.fail("用户权限不足");
        //外键约束
        if(researcherService.getById(recorder.getResearcherid())==null||projectService.getById(recorder.getProjectid())==null)
            return  ResultUtil.fail("信息不存在！违反外键约束");
        QueryWrapper<Record> wrapper=new QueryWrapper<>();
        wrapper.eq("recordid",recorder.getRecordid());
        recordService.update(recorder,wrapper);
        return ResultUtil.success("修改工作记录成功");
    }
    //直接物理删除
    @DeleteMapping("/deletepid={pid}&rid={rid}")
    public ResultUtil deleteRecord(@PathVariable("pid") Integer pid,@PathVariable("rid") Integer rid){
        if(Authority.getAuthority()<2)
            return ResultUtil.fail("用户权限不足");
        QueryWrapper<Record> wrapper=new QueryWrapper<>();
        wrapper.eq("projectid",pid);
        wrapper.eq("researcherid",rid);
        recordService.remove(wrapper);
        return ResultUtil.success("删除工作记录成功");
    }
}

