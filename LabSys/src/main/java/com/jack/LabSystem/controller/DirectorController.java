package com.jack.LabSystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jack.LabSystem.model.entity.Director;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import com.jack.LabSystem.util.ResultUtil;

import com.jack.LabSystem.service.DirectorService;
import com.jack.LabSystem.model.entity.Director;

import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jackvideo
 * @since 2023-11-12 10:47
 */
@RestController
@RequestMapping("/LabSystem/director")
public class DirectorController {

    @Autowired
    private DirectorService directorService;

    //查询全部
    @GetMapping("/list")
    public ResultUtil<Map<String,Object>> getDirectorList(@RequestParam(value = "directorid",required = false) Integer directorid,
                                                          @RequestParam(value = "Labid",required = false) Integer labid,
                                                        @RequestParam(value = "startdate",required = false) String sdate,
                                                        @RequestParam(value = "term",required = false) String term,
                                                        @RequestParam("pageNo") Long pageNo,
                                                        @RequestParam("pageSize") Long pageSize){
        LambdaQueryWrapper<Director> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(directorid!=null ,Director::getDirectorid,directorid);
        wrapper.eq(labid!=null ,Director::getLabid,labid);
        wrapper.eq(sdate!=null&&sdate!="",Director::getStartdate,sdate);
        wrapper.eq(term!=null&&term!="",Director::getTerm,term);
        Page<Director> page = new Page<>(pageNo,pageSize);
        directorService.page(page,wrapper);
        Map<String,Object> data=new HashMap<>();
        data.put("total",page.getTotal());
        data.put("rows",page.getRecords());
        return ResultUtil.success(data);
    }
    //新增接口
    @PostMapping
    public ResultUtil addDirector(@RequestBody Director newDirector){
        directorService.save(newDirector);
        return ResultUtil.success("新增研究室主任成功");
    }
    //修改接口
    @PutMapping("/update")
    public ResultUtil updateDirector(@RequestBody Director newDirector){
        directorService.updateById(newDirector);
        return ResultUtil.success("修改主任信息成功");
    }
    //直接物理删除
    @DeleteMapping("/deleteid={id}")
    public ResultUtil deleteDirector(@PathVariable("id") Integer id){

        directorService.removeById(id);
        return ResultUtil.success("删除研究室主任成功");
    }

}

