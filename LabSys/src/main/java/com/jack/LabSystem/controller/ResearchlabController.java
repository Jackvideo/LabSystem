package com.jack.LabSystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jack.LabSystem.util.Authority;
import com.jack.LabSystem.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import com.jack.LabSystem.service.ResearchlabService;
import com.jack.LabSystem.model.entity.Researchlab;

import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jackvideo
 * @since 2023-11-11 18:50
 */
@RestController
@RequestMapping("/LabSystem/researchlab")
@CrossOrigin
public class ResearchlabController {

    @Autowired
    private ResearchlabService researchlabService;

    //查询全部
    @GetMapping("/getAll")
    public ResultUtil findAll() {
        return ResultUtil.success(researchlabService.list());
        }

    //根据id查询
    @GetMapping("/getid={id}")
    public ResultUtil findOne(@PathVariable Integer id) {
        return ResultUtil.success(researchlabService.getById(id));
        }

    //分页查询
    @GetMapping("/page")
    public ResultUtil findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        return ResultUtil.success(researchlabService.page(new Page<>(pageNum, pageSize)));
        }

    @GetMapping("/list")
    public ResultUtil<Map<String,Object>> getLabList(@RequestParam(value = "labid",required = false) Integer id,
                                                 @RequestParam(value = "labname",required = false) String labname,
                                                 @RequestParam(value = "researcharea",required = false) String area,
                                                 @RequestParam("pageNo") Long pageNo,
                                                 @RequestParam("pageSize") Long pageSize){
        if(Authority.getAuthority()<1)
            return ResultUtil.fail("用户权限不足");
        LambdaQueryWrapper<Researchlab> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(id!=null ,Researchlab::getLabid,id);
        wrapper.eq(labname!=null&&labname!="",Researchlab::getLabname,labname);
        wrapper.eq(area!=null&&area!="",Researchlab::getResearcharea,area);
        Page<Researchlab> page = new Page<>(pageNo,pageSize);
        researchlabService.page(page,wrapper);
         Map<String,Object> data=new HashMap<>();
         data.put("total",page.getTotal());
         data.put("rows",page.getRecords());
         return ResultUtil.success(data);
     }

     //新增接口
     @PostMapping
    public ResultUtil addLab(@RequestBody Researchlab newlab){
         if(Authority.getAuthority()<2)
             return ResultUtil.fail("用户权限不足");
         //唯一性约束
         Researchlab lab=researchlabService.findByName(newlab.getLabname());
        if(lab!=null&&lab.getLabid()!=newlab.getLabid()) {
            return ResultUtil.fail("研究室已存在");
        }
        researchlabService.save(newlab);
         return ResultUtil.success("新增研究室成功");

     }
     //修改接口
    @PutMapping("/update")
    public ResultUtil updateLab(@RequestBody Researchlab newlab){
        if(Authority.getAuthority()<2)
            return ResultUtil.fail("用户权限不足");
        //唯一性约束
        Researchlab lab=researchlabService.findByName(newlab.getLabname());
        if(lab!=null&&lab.getLabid()!=newlab.getLabid())
            return ResultUtil.fail("研究室已存在");
        researchlabService.updateById(newlab);
        return ResultUtil.success("修改研究室成功");
    }
    //直接物理删除
    @DeleteMapping("/deleteid={id}")
    public ResultUtil deleteLab(@PathVariable("id") Integer id){
        if(Authority.getAuthority()<2)
            return ResultUtil.fail("用户权限不足");
        researchlabService.removeById(id);
        return ResultUtil.success("删除研究室成功");
    }
}

