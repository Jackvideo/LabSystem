package com.jack.LabSystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jack.LabSystem.model.entity.Secretary;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import com.jack.LabSystem.util.ResultUtil;

import com.jack.LabSystem.service.SecretaryService;
import com.jack.LabSystem.model.entity.Secretary;

import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jackvideo
 * @since 2023-11-12 10:49
 */
@RestController
@RequestMapping("/LabSystem/secretary")
public class SecretaryController {

    @Autowired
    private SecretaryService secretaryService;

    //查询全部
    @GetMapping("/list")
    public ResultUtil<Map<String,Object>> getSecretaryList(@RequestParam(value = "secretaryid",required = false) Integer secretaryid,
                                                          @RequestParam(value = "hiretime",required = false) String time,
                                                          @RequestParam(value = "responsibility",required = false) String res,
                                                          @RequestParam("pageNo") Long pageNo,
                                                          @RequestParam("pageSize") Long pageSize){
        LambdaQueryWrapper<Secretary> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(secretaryid!=null ,Secretary::getSecretaryid,secretaryid);
        wrapper.eq(time!=null&&time!="",Secretary::getHiretime,time);
        wrapper.eq(res!=null&&res!="",Secretary::getResponsibility,res);
        Page<Secretary> page = new Page<>(pageNo,pageSize);
        secretaryService.page(page,wrapper);
        Map<String,Object> data=new HashMap<>();
        data.put("total",page.getTotal());
        data.put("rows",page.getRecords());
        return ResultUtil.success(data);
    }
    //新增接口
    @PostMapping
    public ResultUtil addSecretary(@RequestBody Secretary newSecretary){
        secretaryService.save(newSecretary);
        return ResultUtil.success("新增秘书成功");
    }
    //修改接口
    @PutMapping("/update")
    public ResultUtil updateSecretary(@RequestBody Secretary newSecretary){
        secretaryService.updateById(newSecretary);
        return ResultUtil.success("修改秘书成功");
    }
    //直接物理删除
    @DeleteMapping("/deleteid={id}")
    public ResultUtil deleteSecretary(@PathVariable("id") Integer id){

        secretaryService.removeById(id);
        return ResultUtil.success("删除秘书成功");
    }

}

