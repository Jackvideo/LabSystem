package com.jack.LabSystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jack.LabSystem.model.entity.*;
import com.jack.LabSystem.service.OutcomeService;
import com.jack.LabSystem.service.ResearcherService;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import com.jack.LabSystem.util.ResultUtil;

import com.jack.LabSystem.service.ContributorService;
import com.jack.LabSystem.model.entity.Contributor;

import java.util.HashMap;
import java.util.Map;


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
    @Autowired
    private OutcomeService outcomeService;
    @Autowired
    private ResearcherService researcherService;

    //根据id查询
    @GetMapping("/getoid={oid}&rid={rid}")
    public ResultUtil findOne(@PathVariable("oid") Integer oid,@PathVariable("rid") Integer rid) {

        QueryWrapper<Contributor> wrapper=new QueryWrapper<>();
        wrapper.eq("outcomeid",oid);
        wrapper.eq("researcherid",rid);
        return ResultUtil.success(contributorService.getOne(wrapper));
    }

    @GetMapping("/list")
    public ResultUtil<Map<String,Object>> getContributorList(@RequestParam(value = "outcomeid",required = false) Integer oid,
                                                        @RequestParam(value = "researcherid",required = false) Integer rid,
                                                        @RequestParam(value = "ranks",required = false) String rank,
                                                        @RequestParam("pageNo") Long pageNo,
                                                        @RequestParam("pageSize") Long pageSize){
        LambdaQueryWrapper<Contributor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(rid!=null ,Contributor::getResearcherid,rid);
        wrapper.eq(oid!=null ,Contributor::getOutcomeid,oid);
        wrapper.eq(rank!=null&&rank!="",Contributor::getRanks,rank);
        Page<Contributor> page = new Page<>(pageNo,pageSize);
        contributorService.page(page,wrapper);
        Map<String,Object> data=new HashMap<>();
        data.put("total",page.getTotal());
        data.put("rows",page.getRecords());
        return ResultUtil.success(data);
    }

    //新增接口
    @PostMapping
    public ResultUtil addContributor(@RequestBody Contributor contributor){
        //检查外键约束，查看新增贡献记录的结果和研究人员信息是否存在
        QueryWrapper<Outcome> outcomeQueryWrapper=new QueryWrapper<>();
        QueryWrapper<Researcher> researcherQueryWrapper=new QueryWrapper<>();
        outcomeQueryWrapper.eq("outcomeid",contributor.getOutcomeid());
        researcherQueryWrapper.eq("researcherid",contributor.getResearcherid());
        if(outcomeService.getOne(outcomeQueryWrapper)==null||researcherService.getOne(researcherQueryWrapper)==null){
            return ResultUtil.fail("贡献记录信息不存在！");
        }
        contributorService.save(contributor);
        return ResultUtil.success("新增贡献记录成功");
    }
    //修改接口
    @PutMapping("/update")
    public ResultUtil updateContributor(@RequestBody Contributor contributor){
        QueryWrapper<Outcome> outcomeQueryWrapper=new QueryWrapper<>();
        QueryWrapper<Researcher> researcherQueryWrapper=new QueryWrapper<>();
        outcomeQueryWrapper.eq("outcomeid",contributor.getOutcomeid());
        researcherQueryWrapper.eq("researcherid",contributor.getResearcherid());
        if(outcomeService.getOne(outcomeQueryWrapper)==null||researcherService.getOne(researcherQueryWrapper)==null){
            return ResultUtil.fail("贡献记录信息不存在！");
        }
        QueryWrapper<Contributor> wrapper=new QueryWrapper<>();
        wrapper.eq("outcomeid",contributor.getOutcomeid());
        wrapper.eq("researcherid",contributor.getResearcherid());
        contributorService.update(contributor,wrapper);
        return ResultUtil.success("修改贡献记录成功");
    }
    //直接物理删除
    @DeleteMapping("/deleteoid={oid}&rid={rid}")
    public ResultUtil deleteContributor(@PathVariable("oid") Integer oid,@PathVariable("rid") Integer rid){
        QueryWrapper<Contributor> wrapper=new QueryWrapper<>();
        wrapper.eq("outcomeid",oid);
        wrapper.eq("researcherid",rid);
        contributorService.remove(wrapper);
        return ResultUtil.success("删除贡献记录成功");
    }
}

