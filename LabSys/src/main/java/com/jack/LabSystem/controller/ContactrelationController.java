package com.jack.LabSystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jack.LabSystem.model.entity.*;
import com.jack.LabSystem.service.ContactService;
import com.jack.LabSystem.service.DepartmentService;
import com.jack.LabSystem.util.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import com.jack.LabSystem.util.ResultUtil;

import com.jack.LabSystem.service.ContactrelationService;
import com.jack.LabSystem.model.entity.Contactrelation;

import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jackvideo
 * @since 2023-11-11 20:30
 */
@RestController
@RequestMapping("/LabSystem/contactrelation")
public class ContactrelationController {

    @Autowired
    private ContactrelationService contactrelationService;
    @Autowired
    private ContactService contactService;
    @Autowired
    private DepartmentService departmentService;


    //根据id查询
    @GetMapping("/getcid={cid}did={did}")
    public ResultUtil findOne(@PathVariable("cid") Integer cid,@PathVariable("did") Integer did) {
        QueryWrapper<Contactrelation> wrapper=new QueryWrapper<>();
        wrapper.eq("departmentid",did);
        wrapper.eq("contactid",cid);
        return ResultUtil.success(contactrelationService.getOne(wrapper));
    }

    //查询全部
    @GetMapping("/list")
    public ResultUtil<Map<String,Object>> getContactrelationList(@RequestParam(value = "contactid",required = false) Integer cid,
                                                          @RequestParam(value = "departmentid",required = false) Integer did,
                                                          @RequestParam(value = "departmentname",required = false) String name,
                                                          @RequestParam("pageNo") Long pageNo,
                                                          @RequestParam("pageSize") Long pageSize){
        if(Authority.getAuthority()<1)
            return ResultUtil.fail("用户权限不足");
        LambdaQueryWrapper<Contactrelation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(cid!=null ,Contactrelation::getContactid,cid);
        wrapper.eq(did!=null ,Contactrelation::getDepartmentid,did);
        wrapper.eq(name!=null&&name!="",Contactrelation::getDepartmentname,name );
        Page<Contactrelation> page = new Page<>(pageNo,pageSize);
        contactrelationService.page(page,wrapper);
        Map<String,Object> data=new HashMap<>();
        data.put("total",page.getTotal());
        data.put("rows",page.getRecords());
        return ResultUtil.success(data);
    }
    //新增接口
    @PostMapping
    public ResultUtil addContactrelation(@RequestBody Contactrelation newRelation){
        if(Authority.getAuthority()<2)
            return ResultUtil.fail("用户权限不足");
        //添加外键约束，判断新增联系关系的单位和联系人是否已存在
        QueryWrapper<Department> departmentwrapper=new QueryWrapper<>();
        QueryWrapper<Contact> contactwrapper=new QueryWrapper<>();
        departmentwrapper.eq("departmentid",newRelation.getDepartmentid());
        departmentwrapper.eq("departmentname",newRelation.getDepartmentname());
        contactwrapper.eq("contactid",newRelation.getContactid());
        if(departmentService.getOne(departmentwrapper)==null||contactService.getOne(contactwrapper)==null)
        {
            return ResultUtil.fail("联系关系不存在！");
        }
        contactrelationService.save(newRelation);
        return ResultUtil.success("新增联系关系成功");
    }
    //修改接口
    @PutMapping("/update")
    public ResultUtil updateContactrelation(@RequestBody Contactrelation newRelation){
        if(Authority.getAuthority()<2)
            return ResultUtil.fail("用户权限不足");
        QueryWrapper<Department> departmentwrapper=new QueryWrapper<>();
        QueryWrapper<Contact> contactwrapper=new QueryWrapper<>();
        departmentwrapper.eq("departmentid",newRelation.getDepartmentid());
        contactwrapper.eq("contactid",newRelation.getContactid());
        if(departmentService.getOne(departmentwrapper)==null||contactService.getOne(contactwrapper)==null)
        {
            return ResultUtil.fail("联系关系不存在！");
        }
        QueryWrapper<Contactrelation> wrapper=new QueryWrapper<>();
        wrapper.eq("recordid",newRelation.getRecordid());
        contactrelationService.update(newRelation,wrapper);
        return ResultUtil.success("修改联系关系成功");
    }
    //直接物理删除
    @DeleteMapping("/deletecid={cid}did={did}")
    public ResultUtil deleteContactrelation(@PathVariable("did") Integer did,@PathVariable("cid") Integer cid){
        if(Authority.getAuthority()<2)
            return ResultUtil.fail("用户权限不足");
        QueryWrapper<Contactrelation> wrapper=new QueryWrapper<>();
        wrapper.eq("departmentid",did);
        wrapper.eq("contactid",cid);
        contactrelationService.remove(wrapper);
        return ResultUtil.success("删除联系关系成功");
    }
}

