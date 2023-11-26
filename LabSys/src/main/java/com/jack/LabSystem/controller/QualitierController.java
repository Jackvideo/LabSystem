package com.jack.LabSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import com.jack.LabSystem.util.ResultUtil;

import com.jack.LabSystem.service.QualitierService;
import com.jack.LabSystem.model.entity.Qualitier;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jackvideo
 * @since 2023-11-12 10:50
 */
@RestController
@RequestMapping("/LabSystem/qualitier")
public class QualitierController {

    @Autowired
    private QualitierService qualitierService;

    //编辑或更新
    @PostMapping("/saveOrUpdate")
    public ResultUtil save(@RequestBody Qualitier qualitier) {
        return ResultUtil.success(qualitierService.saveOrUpdate(qualitier));
        }

    //根据id删除
    @DeleteMapping("/deleteid={id}")
    public ResultUtil delete(@PathVariable Integer id) {
        return ResultUtil.success(qualitierService.removeById(id));
        }

    //查询全部
    @GetMapping("/getAll")
    public ResultUtil findAll() {
        return ResultUtil.success(qualitierService.list());
        }

    //根据id查询
    @GetMapping("/getid={id}")
    public ResultUtil findOne(@PathVariable Integer id) {
        return ResultUtil.success(qualitierService.list());
        }

    //分页查询
    @GetMapping("/page")
    public ResultUtil findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        return ResultUtil.success(qualitierService.page(new Page<>(pageNum, pageSize)));
        }

}

