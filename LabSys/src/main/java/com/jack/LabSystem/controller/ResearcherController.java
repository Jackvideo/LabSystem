package com.jack.LabSystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jack.LabSystem.model.entity.Researchlab;
import com.jack.LabSystem.service.ResearchlabService;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import com.jack.LabSystem.util.ResultUtil;

import com.jack.LabSystem.service.ResearcherService;
import com.jack.LabSystem.model.entity.Researcher;

import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jackvideo
 * @since 2023-11-11 20:18
 */
@RestController
@RequestMapping("/LabSystem/researcher")
@CrossOrigin
public class ResearcherController {

    @Autowired
    private ResearcherService researcherService;

    @Autowired
    private ResearchlabService researchlabService;

    //查询全部
    @GetMapping("/getAll")
    public ResultUtil findAll() {
        return ResultUtil.success(researcherService.list());
    }

    //根据id查询
    @GetMapping("/getid={id}")
    public ResultUtil findOne(@PathVariable Integer id) {
        return ResultUtil.success(researcherService.getById(id));
    }

    //分页查询
    @GetMapping("/page")
    public ResultUtil findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        return ResultUtil.success(researcherService.page(new Page<>(pageNum, pageSize)));
    }

    @GetMapping("/list")
    public ResultUtil<Map<String,Object>> getLabList(@RequestParam(value = "researcherid",required = false) Integer rid,
                                                     @RequestParam(value = "labid",required = false) Integer labid,
                                                     @RequestParam(value = "researchername",required = false) String name,
                                                     @RequestParam(value = "gender",required = false) String gender,
                                                     @RequestParam(value = "level", required = false) String level,
                                                     @RequestParam(value = "age",required = false)  String age,
                                                     @RequestParam(value = "researcharea",required = false) String area,
                                                     @RequestParam("pageNo") Long pageNo,
                                                     @RequestParam("pageSize") Long pageSize){
        LambdaQueryWrapper<Researcher> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(rid!=null ,Researcher::getResearcherid,rid);
        wrapper.eq(labid!=null ,Researcher::getLabid,labid);
        wrapper.eq(name!=null&&name!="",Researcher::getResearchername,name);
        wrapper.eq(gender!=null&&gender!="",Researcher::getGender,gender);
        wrapper.eq(level!=null&&level!="",Researcher::getLevel,level);
        wrapper.eq(age!=null&&age!="",Researcher::getAge,age);
        wrapper.eq(area!=null&&area!="",Researcher::getResearcharea,area);
        Page<Researcher> page = new Page<>(pageNo,pageSize);
        researcherService.page(page,wrapper);
        Map<String,Object> data=new HashMap<>();
        data.put("total",page.getTotal());
        data.put("rows",page.getRecords());
        return ResultUtil.success(data);
    }

    //新增接口
    @PostMapping
    public ResultUtil addLab(@RequestBody Researcher laber){
        //外键约束，检查新增研究室是否存在
        if(laber.getLabid()!=null&&researchlabService.getById(laber.getLabid())==null)
            return ResultUtil.fail("研究室不存在！");
        if(researcherService.findByName(laber.getResearchername())==null) {
            researcherService.save(laber);
            return ResultUtil.success("新增人员成功");
        }else
            return ResultUtil.fail("已存在该人员");
    }
    //修改接口
    @PutMapping("/update")
    public ResultUtil updateLab(@RequestBody Researcher laber){
        //外键约束，检查新增研究室是否存在
        if(laber.getLabid()!=null&&researchlabService.getById(laber.getLabid())==null)
            return ResultUtil.fail("研究室不存在！");
        researcherService.updateById(laber);
        return ResultUtil.success("修改人员成功");
    }
    //直接物理删除
    @DeleteMapping("/deleteid={id}")
    public ResultUtil deleteLab(@PathVariable("id") Integer id){
        researcherService.removeById(id);
        return ResultUtil.success("删除人员成功");
    }

}

