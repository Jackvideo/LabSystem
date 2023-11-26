package com.jack.LabSystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jack.LabSystem.model.entity.Researcher;
import com.jack.LabSystem.mapper.ResearcherMapper;
import com.jack.LabSystem.service.ResearcherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jackvideo
 * @since 2023-11-11 20:18
 */
@Service
public class ResearcherServiceImpl extends ServiceImpl<ResearcherMapper, Researcher> implements ResearcherService {

    @Autowired
    private ResearcherMapper researcherMapper;

    //按研究人员名字查询
    @Override
    public Researcher findByName(String name) {
        QueryWrapper<Researcher> wrapper=new QueryWrapper<>();
        wrapper.eq("researchername",name);
        return researcherMapper.selectOne(wrapper);
    }


}
