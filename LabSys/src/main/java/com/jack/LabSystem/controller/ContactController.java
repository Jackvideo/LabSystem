package com.jack.LabSystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jack.LabSystem.model.entity.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import com.jack.LabSystem.util.ResultUtil;

import com.jack.LabSystem.service.ContactService;
import com.jack.LabSystem.model.entity.Contact;

import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jackvideo
 * @since 2023-11-12 10:51
 */
@RestController
@RequestMapping("/LabSystem/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    //根据id查询
    @GetMapping("/getid={id}")
    public ResultUtil findOne(@PathVariable Integer id) {
        return ResultUtil.success(contactService.getById(id));
    }

    //查询全部
    @GetMapping("/list")
    public ResultUtil<Map<String,Object>> getContactList(@RequestParam(value = "contactid",required = false) Integer contactid,
                                                        @RequestParam(value = "workphone",required = false) String wphone,
                                                        @RequestParam(value = "mobilephone",required = false) String mphone,
                                                        @RequestParam(value = "email",required = false) String email,
                                                        @RequestParam("pageNo") Long pageNo,
                                                        @RequestParam("pageSize") Long pageSize){
        LambdaQueryWrapper<Contact> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(contactid!=null ,Contact::getContactid,contactid);
        wrapper.eq(wphone!=null&&wphone!="",Contact::getWorkphone,wphone);
        wrapper.eq(mphone!=null&&mphone!="",Contact::getMobilephone,mphone);
        wrapper.eq(email!=null&&email!="",Contact::getEmail,email);
        Page<Contact> page = new Page<>(pageNo,pageSize);
        contactService.page(page,wrapper);
        Map<String,Object> data=new HashMap<>();
        data.put("total",page.getTotal());
        data.put("rows",page.getRecords());
        return ResultUtil.success(data);
    }
    //新增接口
    @PostMapping
    public ResultUtil addContact(@RequestBody Contact newContact){
        contactService.save(newContact);
        return ResultUtil.success("新增联系人成功");
    }
    //修改接口
    @PutMapping("/update")
    public ResultUtil updateContact(@RequestBody Contact newContact){
        contactService.updateById(newContact);
        return ResultUtil.success("修改联系人成功");
    }
    //直接物理删除
    @DeleteMapping("/deleteid={id}")
    public ResultUtil deleteContact(@PathVariable("id") Integer id){

        contactService.removeById(id);
        return ResultUtil.success("删除联系人成功");
    }
}

