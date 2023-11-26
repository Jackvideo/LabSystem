package com.jack.LabSystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jack.LabSystem.model.entity.Subproject;
import com.jack.LabSystem.mapper.SubprojectMapper;
import com.jack.LabSystem.service.SubprojectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jackvideo
 * @since 2023-11-11 20:23
 */
@Service
public class SubprojectServiceImpl extends ServiceImpl<SubprojectMapper, Subproject> implements SubprojectService {

    @Autowired
    private SubprojectMapper subprojectMapper;

    //根据项目id查询该项目下的所有子项目
    @Override
    public List<Subproject> findProject(Integer pid) {
        QueryWrapper<Subproject> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("Projectid",pid);
        return subprojectMapper.selectList(queryWrapper);
    }

    //根据项目id和子项目id查询子项目
    @Override
    public Subproject findOne(Integer pid, Integer spid) {
        QueryWrapper<Subproject> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("Projectid",pid);
        queryWrapper.eq("Subprojectid",spid);
        return subprojectMapper.selectOne(queryWrapper);
    }
}
