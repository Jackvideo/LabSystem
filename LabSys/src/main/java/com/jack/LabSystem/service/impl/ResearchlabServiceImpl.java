package com.jack.LabSystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jack.LabSystem.model.entity.Researcher;
import com.jack.LabSystem.model.entity.Researchlab;
import com.jack.LabSystem.mapper.ResearchlabMapper;
import com.jack.LabSystem.service.ResearchlabService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jackvideo
 * @since 2023-11-11 18:50
 */
@Service
public class ResearchlabServiceImpl extends ServiceImpl<ResearchlabMapper, Researchlab> implements ResearchlabService {

    @Autowired
    private ResearchlabMapper researchlabMapper;
    //按研究人员名字查询
    @Override
    public Researchlab findByName(String name) {
        QueryWrapper<Researchlab> wrapper=new QueryWrapper<>();
        wrapper.eq("Labname",name);
        return researchlabMapper.selectOne(wrapper);
    }

}
