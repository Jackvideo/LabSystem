package com.jack.LabSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import com.jack.LabSystem.util.ResultUtil;

import com.jack.LabSystem.service.PartnerService;
import com.jack.LabSystem.model.entity.Partner;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jackvideo
 * @since 2023-11-12 10:50
 */
@RestController
@RequestMapping("/LabSystem/partner")
public class PartnerController {

    @Autowired
    private PartnerService partnerService;

    //编辑或更新
    @PostMapping("/saveOrUpdate")
    public ResultUtil save(@RequestBody Partner partner) {
        return ResultUtil.success(partnerService.saveOrUpdate(partner));
        }

    //根据id删除
    @DeleteMapping("/deleteid={id}")
    public ResultUtil delete(@PathVariable Integer id) {
        return ResultUtil.success(partnerService.removeById(id));
        }

    //查询全部
    @GetMapping("/getAll")
    public ResultUtil findAll() {
        return ResultUtil.success(partnerService.list());
        }

    //根据id查询
    @GetMapping("/getid={id}")
    public ResultUtil findOne(@PathVariable Integer id) {
        return ResultUtil.success(partnerService.list());
        }

    //分页查询
    @GetMapping("/page")
    public ResultUtil findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        return ResultUtil.success(partnerService.page(new Page<>(pageNum, pageSize)));
        }

}

