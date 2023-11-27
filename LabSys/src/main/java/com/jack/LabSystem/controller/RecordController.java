package com.jack.LabSystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jack.LabSystem.model.entity.Record;
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
@RequestMapping("/RecordSystem/record")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @GetMapping("/list")
    public ResultUtil<Map<String,Object>> getRecordList(@RequestParam(value = "Researcherid",required = false) Integer rid,
                                                     @RequestParam(value = "Projectid",required = false) Integer pid,
                                                     @RequestParam(value = "participationdate",required = false) String date,
                                                     @RequestParam(value = "workload",required = false) String workload,
                                                     @RequestParam(value = "allocatedfund", required = false) String fund,
                                                     @RequestParam("pageNo") Long pageNo,
                                                     @RequestParam("pageSize") Long pageSize){
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
            recordService.save(recorder);
            return ResultUtil.success("新增工作记录成功");
    }
    //修改接口
    @PutMapping("/update")
    public ResultUtil updateRecord(@RequestBody Record recorder){
        recordService.updateById(recorder);
        return ResultUtil.success("修改工作记录成功");
    }
    //直接物理删除
    @DeleteMapping("/deleteid={id}")
    public ResultUtil deleteRecord(@PathVariable("id") Integer id){
        recordService.removeById(id);
        return ResultUtil.success("删除工作记录成功");
    }
}

