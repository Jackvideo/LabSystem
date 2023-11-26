package com.jack.LabSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import com.jack.LabSystem.util.ResultUtil;

import com.jack.LabSystem.service.SubprojectService;
import com.jack.LabSystem.model.entity.Subproject;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jackvideo
 * @since 2023-11-11 20:23
 */
@RestController
@RequestMapping("/LabSystem/subproject")
public class SubprojectController {

    @Autowired
    private SubprojectService subprojectService;

    //编辑或更新
    @PostMapping("/saveOrUpdate")
    public ResultUtil save(@RequestBody Subproject subproject) {
        return ResultUtil.success(subprojectService.saveOrUpdate(subproject));
        }

    //根据id删除
    @DeleteMapping("/{id}")
    public ResultUtil delete(@PathVariable Integer id) {
        return ResultUtil.success(subprojectService.removeById(id));
        }

    //查询全部
    @GetMapping("/getAll")
    public ResultUtil findAll() {
        return ResultUtil.success(subprojectService.list());
        }

    @GetMapping("/pid={id}")
    public ResultUtil findProject(@PathVariable Integer id){
        return ResultUtil.success(subprojectService.findProject(id));
    }

    //根据项目id和子项目id查询
    @GetMapping("/pid={id1}/id={id2}")
    public ResultUtil findOne(@PathVariable Integer id1 , @PathVariable Integer id2) {
        return ResultUtil.success(subprojectService.findOne(id1,id2));
        }

    //分页查询
    @GetMapping("/page")
    public ResultUtil findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        return ResultUtil.success(subprojectService.page(new Page<>(pageNum, pageSize)));
        }

}

