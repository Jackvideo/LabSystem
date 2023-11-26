package com.jack.LabSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import com.jack.LabSystem.util.ResultUtil;

import com.jack.LabSystem.service.PrincipleService;
import com.jack.LabSystem.model.entity.Principle;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jackvideo
 * @since 2023-11-12 10:50
 */
@RestController
@RequestMapping("/LabSystem/principle")
public class PrincipleController {

    @Autowired
    private PrincipleService principleService;

    //编辑或更新
    @PostMapping("/saveOrUpdate")
    public ResultUtil save(@RequestBody Principle principle) {
        return ResultUtil.success(principleService.saveOrUpdate(principle));
        }

    //根据id删除
    @DeleteMapping("/deleteid={id}")
    public ResultUtil delete(@PathVariable Integer id) {
        return ResultUtil.success(principleService.removeById(id));
        }

    //查询全部
    @GetMapping("/getAll")
    public ResultUtil findAll() {
        return ResultUtil.success(principleService.list());
        }

    //根据id查询
    @GetMapping("/getid={id}")
    public ResultUtil findOne(@PathVariable Integer id) {
        return ResultUtil.success(principleService.list());
        }

    //分页查询
    @GetMapping("/page")
    public ResultUtil findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        return ResultUtil.success(principleService.page(new Page<>(pageNum, pageSize)));
        }

}

