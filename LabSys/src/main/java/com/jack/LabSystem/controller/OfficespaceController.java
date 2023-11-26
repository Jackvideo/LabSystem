package com.jack.LabSystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jack.LabSystem.model.entity.Officespace;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import com.jack.LabSystem.util.ResultUtil;
import com.jack.LabSystem.service.OfficespaceService;
import com.jack.LabSystem.model.entity.Officespace;
import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jackvideo
 * @since 2023-11-12 10:48
 */
@RestController
@RequestMapping("/LabSystem/officespace")
public class OfficespaceController {

    @Autowired
    private OfficespaceService officespaceService;

    //查询全部
    @GetMapping("/getAll")
    public ResultUtil findAll() {
        return ResultUtil.success(officespaceService.list());
    }

    //根据id查询
    @GetMapping("/getid={id}")
    public ResultUtil findOne(@PathVariable Integer id) {
        return ResultUtil.success(officespaceService.getById(id));
    }

    //分页查询
    @GetMapping("/page")
    public ResultUtil findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        return ResultUtil.success(officespaceService.page(new Page<>(pageNum, pageSize)));
    }

    @GetMapping("/list")
    public ResultUtil<Map<String,Object>> getOfficespaceList(@RequestParam(value = "spaceid",required = false) Integer Spaceid,
                                                             @RequestParam(value = "labid",required = false) Integer Labid,
                                                         @RequestParam(value = "spacename",required = false) String name,
                                                         @RequestParam(value = "area",required = false) Double area,
                                                         @RequestParam(value = "address",required = false) String address,
                                                         @RequestParam("pageNo") Long pageNo,
                                                         @RequestParam("pageSize") Long pageSize){
        LambdaQueryWrapper<Officespace> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Spaceid!=null ,Officespace::getSpaceid,Spaceid);
        wrapper.eq(Labid!=null ,Officespace::getLabid,Labid);
        wrapper.eq(name!=null&&name!="",Officespace::getSpacename,name);
        wrapper.eq(area!=null,Officespace::getArea,area);
        wrapper.eq(address!=null&&address!="",Officespace::getAddress,address);
        Page<Officespace> page = new Page<>(pageNo,pageSize);
        officespaceService.page(page,wrapper);
        Map<String,Object> data=new HashMap<>();
        data.put("total",page.getTotal());
        data.put("rows",page.getRecords());
        return ResultUtil.success(data);
    }

    //新增接口
    @PostMapping
    public ResultUtil addOfficespace(@RequestBody Officespace newOfficespace){
        if(officespaceService.findByName(newOfficespace.getSpacename())==null) {
            officespaceService.save(newOfficespace);
            return ResultUtil.success("新增场地成功");
        }else
            return ResultUtil.fail("该场地已存在");
    }
    //修改接口
    @PutMapping("/update")
    public ResultUtil updateOfficespace(@RequestBody Officespace newOfficespace){
        officespaceService.updateById(newOfficespace);
        return ResultUtil.success("修改场地成功");
    }
    //直接物理删除
    @DeleteMapping("/deleteid={id}")
    public ResultUtil deleteOfficespace(@PathVariable("id") Integer id){

        officespaceService.removeById(id);
        return ResultUtil.success("删除场地成功");
    }

}

