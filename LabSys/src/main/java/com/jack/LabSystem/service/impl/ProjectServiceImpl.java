package com.jack.LabSystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jack.LabSystem.model.entity.Project;
import com.jack.LabSystem.mapper.ProjectMapper;
import com.jack.LabSystem.model.entity.Researchlab;
import com.jack.LabSystem.service.ProjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jackvideo
 * @since 2023-11-11 20:19
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {

    @Autowired
    private ProjectMapper projectMapper;

    @Override
    public Project findByName(String name) {
        QueryWrapper<Project> wrapper=new QueryWrapper<>();
        wrapper.eq("projectname",name);
        return projectMapper.selectOne(wrapper);
    }
}
