package com.jack.LabSystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jack.LabSystem.model.entity.Outcome;
import com.jack.LabSystem.mapper.OutcomeMapper;
import com.jack.LabSystem.model.entity.Researchlab;
import com.jack.LabSystem.service.OutcomeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jackvideo
 * @since 2023-11-11 20:23
 */
@Service
public class OutcomeServiceImpl extends ServiceImpl<OutcomeMapper, Outcome> implements OutcomeService {

    @Autowired
    private OutcomeMapper outcomeMapper;

    @Override
    public Outcome findByName(String name) {
        QueryWrapper<Outcome> wrapper=new QueryWrapper<>();
        wrapper.eq("outcomename",name);
        return outcomeMapper.selectOne(wrapper);
    }
}
