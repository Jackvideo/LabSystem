package com.jack.LabSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import com.jack.LabSystem.util.ResultUtil;

import com.jack.LabSystem.service.ContactrelationService;
import com.jack.LabSystem.model.entity.Contactrelation;


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

    //编辑或更新
    @PostMapping("/saveOrUpdate")
    public ResultUtil save(@RequestBody Contactrelation contactrelation) {
        return ResultUtil.success(contactrelationService.saveOrUpdate(contactrelation));
        }


    //查询全部
    @GetMapping("/getAll")
    public ResultUtil findAll() {
        return ResultUtil.success(contactrelationService.list());
        }


    //分页查询
    @GetMapping("/page")
    public ResultUtil findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        return ResultUtil.success(contactrelationService.page(new Page<>(pageNum, pageSize)));
        }

}

