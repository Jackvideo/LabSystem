package com.jack.LabSystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jack.LabSystem.model.entity.Leader;
import com.jack.LabSystem.model.entity.Leader;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import com.jack.LabSystem.util.ResultUtil;

import com.jack.LabSystem.service.LeaderService;
import com.jack.LabSystem.model.entity.Leader;

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
@RequestMapping("/LabSystem/leader")
public class LeaderController {

    @Autowired
    private LeaderService leaderService;
    

    //查询全部
    @GetMapping("/list")
    public ResultUtil<Map<String,Object>> getLeaderList(@RequestParam(value = "leaderid",required = false) Integer leaderid,
                                                             @RequestParam(value = "workphone",required = false) String wphone,
                                                             @RequestParam(value = "mobilephone",required = false) String mphone,
                                                             @RequestParam(value = "email",required = false) String email,
                                                             @RequestParam("pageNo") Long pageNo,
                                                             @RequestParam("pageSize") Long pageSize){
        LambdaQueryWrapper<Leader> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(leaderid!=null ,Leader::getLeaderid,leaderid);
        wrapper.eq(wphone!=null&&wphone!="",Leader::getWorkphone,wphone);
        wrapper.eq(mphone!=null&&mphone!="",Leader::getMobilephone,mphone);
        wrapper.eq(email!=null&&email!="",Leader::getEmail,email);
        Page<Leader> page = new Page<>(pageNo,pageSize);
        leaderService.page(page,wrapper);
        Map<String,Object> data=new HashMap<>();
        data.put("total",page.getTotal());
        data.put("rows",page.getRecords());
        return ResultUtil.success(data);
    }
    //新增接口
    @PostMapping
    public ResultUtil addLeader(@RequestBody Leader newLeader){
            leaderService.save(newLeader);
            return ResultUtil.success("新增负责人成功");
    }
    //修改接口
    @PutMapping("/update")
    public ResultUtil updateLeader(@RequestBody Leader newLeader){
        leaderService.updateById(newLeader);
        return ResultUtil.success("修改负责人成功");
    }
    //直接物理删除
    @DeleteMapping("/deleteid={id}")
    public ResultUtil deleteLeader(@PathVariable("id") Integer id){

        leaderService.removeById(id);
        return ResultUtil.success("删除负责人成功");
    }
}

