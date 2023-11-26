package com.jack.LabSystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jack.LabSystem.model.entity.Outcome;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import com.jack.LabSystem.util.ResultUtil;

import com.jack.LabSystem.service.ProjectService;
import com.jack.LabSystem.model.entity.Project;

import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jackvideo
 * @since 2023-11-11 20:19
 */
@RestController
@RequestMapping("/LabSystem/project")
@CrossOrigin
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    //查询全部
    @GetMapping("/getAll")
    public ResultUtil findAll() {
        return ResultUtil.success(projectService.list());
    }

    //根据id查询
    @GetMapping("/getid={id}")
    public ResultUtil findOne(@PathVariable Integer id) {
        return ResultUtil.success(projectService.getById(id));
    }

    //分页查询
    @GetMapping("/page")
    public ResultUtil findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        return ResultUtil.success(projectService.page(new Page<>(pageNum, pageSize)));
    }

    @GetMapping("/list")
    public ResultUtil<Map<String,Object>> getProjectList(@RequestParam(value = "projectid",required = false) Integer projectid,
                                                     @RequestParam(value = "leaderid",required = false) Integer leaderid,
                                                     @RequestParam(value = "projectname",required = false) String name,
                                                     @RequestParam(value = "content",required = false) String content,
                                                     @RequestParam(value = "totalfund",required = false) String totalfund,
                                                     @RequestParam(value = "startdate",required = false) String startdate,
                                                     @RequestParam(value = "enddate",required = false) String enddate,
                                                     @RequestParam("pageNo") Long pageNo,
                                                     @RequestParam("pageSize") Long pageSize){
        LambdaQueryWrapper<Project> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(projectid!=null ,Project::getProjectid,projectid);
        wrapper.eq(leaderid!=null ,Project::getLeaderid,leaderid);
        wrapper.eq(name!=null&&name!="",Project::getProjectname,name);
        wrapper.eq(content!=null&&content!="",Project::getContent,content);
        wrapper.eq(startdate!=null&&startdate!="",Project::getStartdate,startdate);
        wrapper.eq(enddate!=null&&enddate!="",Project::getEnddate,enddate);
        Page<Project> page = new Page<>(pageNo,pageSize);
        projectService.page(page,wrapper);
        Map<String,Object> data=new HashMap<>();
        data.put("total",page.getTotal());
        data.put("rows",page.getRecords());
        return ResultUtil.success(data);
    }

    //新增接口
    @PostMapping
    public ResultUtil addProject(@RequestBody Project newProject){
        if(projectService.findByName(newProject.getProjectname())==null) {
            projectService.save(newProject);
            return ResultUtil.success("新增项目成功");
        }else
            return ResultUtil.fail("该项目已存在");
    }
    //修改接口
    @PutMapping("/update")
    public ResultUtil updateProject(@RequestBody Project newProject){
        projectService.updateById(newProject);
        return ResultUtil.success("修改项目成功");
    }
    //直接物理删除
    @DeleteMapping("/deleteid={id}")
    public ResultUtil deleteProject(@PathVariable("id") Integer id){

        projectService.removeById(id);
        return ResultUtil.success("删除项目成功");
    }

}

