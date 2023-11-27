package com.jack.LabSystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jack.LabSystem.model.entity.Contributor;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import com.jack.LabSystem.util.ResultUtil;

import com.jack.LabSystem.service.ContributorService;
import com.jack.LabSystem.model.entity.Contributor;

import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jackvideo
 * @since 2023-11-11 20:29
 */
@RestController
@RequestMapping("/LabSystem/contributor")
public class ContributorController {

    @Autowired
    private ContributorService contributorService;

    @GetMapping("/list")
    public ResultUtil<Map<String,Object>> getContributorList(@RequestParam(value = "Outcomeid",required = false) Integer oid,
                                                        @RequestParam(value = "Researcherid",required = false) Integer rid,
                                                        @RequestParam(value = "ranks",required = false) String rank,
                                                        @RequestParam("pageNo") Long pageNo,
                                                        @RequestParam("pageSize") Long pageSize){
        LambdaQueryWrapper<Contributor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(rid!=null ,Contributor::getResearcherid,rid);
        wrapper.eq(oid!=null ,Contributor::getOutcomeid,oid);
        wrapper.eq(rank!=null&&rank!="",Contributor::getRanks,rank);
        Page<Contributor> page = new Page<>(pageNo,pageSize);
        contributorService.page(page,wrapper);
        Map<String,Object> data=new HashMap<>();
        data.put("total",page.getTotal());
        data.put("rows",page.getRecords());
        return ResultUtil.success(data);
    }

    //新增接口
    @PostMapping
    public ResultUtil addContributor(@RequestBody Contributor contributorer){
        contributorService.save(contributorer);
        return ResultUtil.success("新增贡献记录成功");
    }
    //修改接口
    @PutMapping("/update")
    public ResultUtil updateContributor(@RequestBody Contributor contributorer){
        contributorService.updateById(contributorer);
        return ResultUtil.success("修改贡献记录成功");
    }
    //直接物理删除
    @DeleteMapping("/deleteid={id}")
    public ResultUtil deleteContributor(@PathVariable("id") Integer id){
        contributorService.removeById(id);
        return ResultUtil.success("删除贡献记录成功");
    }
}

