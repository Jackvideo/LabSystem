package com.jack.LabSystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jack.LabSystem.model.entity.Department;
import com.jack.LabSystem.model.entity.Project;
import com.jack.LabSystem.service.ProjectService;
import com.jack.LabSystem.service.DepartmentService;
import com.jack.LabSystem.util.Authority;
import com.jack.LabSystem.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import com.jack.LabSystem.service.PdrelationService;
import com.jack.LabSystem.model.entity.Pdrelation;

import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jackvideo
 * @since 2023-12-05 10:24
 */
@RestController
@RequestMapping("/LabSystem/pdrelation")
public class PdrelationController {

    @Autowired
    private PdrelationService pdrelationService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private DepartmentService departmentService;


    //根据id查询
    @GetMapping("/getid={id}")
    public ResultUtil findOne(@PathVariable("id") Integer id) {
        return ResultUtil.success(pdrelationService.getById(id));
    }

    //查询全部
    @GetMapping("/list")
    public ResultUtil<Map<String,Object>> getContactrelationList(@RequestParam(value = "projectid",required = false) Integer pid,
                                                                 @RequestParam(value = "projectname",required = false) String name,
                                                                 @RequestParam(value = "partner",required = false) String partner,
                                                                 @RequestParam(value = "principle",required = false) String principle,
                                                                 @RequestParam(value = "qualifier",required = false) String qualifier,
                                                                 @RequestParam("pageNo") Long pageNo,
                                                                 @RequestParam("pageSize") Long pageSize){
        if(Authority.getAuthority()<1)
            return ResultUtil.fail("用户权限不足");
        LambdaQueryWrapper<Pdrelation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(pid!=null ,Pdrelation::getPartner,pid);
        wrapper.eq(name!=null&&name!="",Pdrelation::getProjectname,name);
        wrapper.eq(partner!=null&&partner!="",Pdrelation::getPartner,partner);
        wrapper.eq(principle!=null&&principle!="",Pdrelation::getPrinciple,principle);
        wrapper.eq(qualifier!=null&&qualifier!="",Pdrelation::getQualifier,qualifier);
        Page<Pdrelation> page = new Page<>(pageNo,pageSize);
        pdrelationService.page(page,wrapper);
        Map<String,Object> data=new HashMap<>();
        data.put("total",page.getTotal());
        data.put("rows",page.getRecords());
        return ResultUtil.success(data);
    }
    //新增接口
    @PostMapping
    public ResultUtil addContactrelation(@RequestBody Pdrelation newRelation){
        if(Authority.getAuthority()<2)
            return ResultUtil.fail("用户权限不足");
        //添加外键约束，判断新增联系关系的单位和项目是否已存在
        QueryWrapper<Project> projectwrapper=new QueryWrapper<>();
        QueryWrapper<Department> partnerwrapper=new QueryWrapper<>();
        QueryWrapper<Department> principlewrapper=new QueryWrapper<>();
        QueryWrapper<Department> qualifierwrapper=new QueryWrapper<>();
        projectwrapper.eq("projectid",newRelation.getProjectid());
        projectwrapper.eq("projectname",newRelation.getProjectname());
        //项目外键约束
        if(projectService.getOne(projectwrapper)==null)
            return ResultUtil.fail("项目信息错误！");
        partnerwrapper.eq("departmentname",newRelation.getPartner());
        principlewrapper.eq("departmentname",newRelation.getPrinciple());
        qualifierwrapper.eq("departmentname",newRelation.getQualifier());
        String partner=newRelation.getPartner();
        String principle=newRelation.getPrinciple();
        String qualifier= newRelation.getQualifier();
        //允许空，但如果非空，则必须判断外键约束
        if((partner!=null&&departmentService.getOne(partnerwrapper)==null)||(principle!=null&&departmentService.getOne(principlewrapper)==null)||(qualifier!=null&&departmentService.getOne(qualifierwrapper)==null))
            return ResultUtil.fail("单位信息错误！");
        pdrelationService.save(newRelation);
        return ResultUtil.success("新增联系关系成功");
    }
    //修改接口
    @PutMapping("/update")
    public ResultUtil updateContactrelation(@RequestBody Pdrelation newRelation){
        if(Authority.getAuthority()<2)
            return ResultUtil.fail("用户权限不足");
        //添加外键约束，判断新增联系关系的单位和项目是否已存在
        QueryWrapper<Project> projectwrapper=new QueryWrapper<>();
        QueryWrapper<Department> partnerwrapper=new QueryWrapper<>();
        QueryWrapper<Department> principlewrapper=new QueryWrapper<>();
        QueryWrapper<Department> qualifierwrapper=new QueryWrapper<>();
        projectwrapper.eq("projectid",newRelation.getProjectid());
        projectwrapper.eq("projectname",newRelation.getProjectname());
        //项目外键约束
        if(projectService.getOne(projectwrapper)==null)
            return ResultUtil.fail("项目信息错误！");
        partnerwrapper.eq("departmentname",newRelation.getPartner());
        principlewrapper.eq("departmentname",newRelation.getPrinciple());
        qualifierwrapper.eq("departmentname",newRelation.getQualifier());
        String partner=newRelation.getPartner();
        String principle=newRelation.getPrinciple();
        String qualifier= newRelation.getQualifier();
        //允许空，但如果非空，则必须判断外键约束
        if((partner!=null&&departmentService.getOne(partnerwrapper)==null)||(principle!=null&&departmentService.getOne(principlewrapper)==null)||(qualifier!=null&&departmentService.getOne(qualifierwrapper)==null))
            return ResultUtil.fail("单位信息错误！");
        //监测方和委托方的唯一性约束
        if(principle!=null&&pdrelationService.hasPrinciple(newRelation.getProjectid())==true)
            return ResultUtil.fail("该项目已存在唯一委托方");
        if(qualifier!=null&&pdrelationService.hasQualifier(newRelation.getProjectid())==true)
            return ResultUtil.fail("该项目已存在唯一监测方");
        pdrelationService.updateById(pdrelationService.getById(newRelation.getProjectid()));
        return ResultUtil.success("修改联系关系成功");
    }
    //直接物理删除
    @DeleteMapping("/deleteid={id}")
    public ResultUtil deleteContactrelation(@PathVariable("id") Integer id){
        if(Authority.getAuthority()<2)
            return ResultUtil.fail("用户权限不足");
        pdrelationService.removeById(id);
        return ResultUtil.success("删除联系关系成功");
    }
}

