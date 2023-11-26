package com.jack.LabSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import com.jack.LabSystem.util.ResultUtil;

import com.jack.LabSystem.service.ContributorService;
import com.jack.LabSystem.model.entity.Contributor;


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

    //编辑或更新
    @PostMapping("/saveOrUpdate")
    public ResultUtil save(@RequestBody Contributor contributor) {
        return ResultUtil.success(contributorService.saveOrUpdate(contributor));
        }

    //根据id删除
    @DeleteMapping("/deleteid={id}")
    public ResultUtil delete(@PathVariable Integer id) {
        return ResultUtil.success(contributorService.removeById(id));
        }

    //查询全部
    @GetMapping("/getAll")
    public ResultUtil findAll() {
        return ResultUtil.success(contributorService.list());
        }

    //根据id查询
    @GetMapping("/getid={id}")
    public ResultUtil findOne(@PathVariable Integer id) {
        return ResultUtil.success(contributorService.list());
        }

    //分页查询
    @GetMapping("/page")
    public ResultUtil findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        return ResultUtil.success(contributorService.page(new Page<>(pageNum, pageSize)));
        }

}

