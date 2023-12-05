package com.jack.LabSystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jack.LabSystem.model.entity.Pdrelation;
import com.jack.LabSystem.mapper.PdrelationMapper;
import com.jack.LabSystem.service.PdrelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jackvideo
 * @since 2023-12-05 10:24
 */
@Service
public class PdrelationServiceImpl extends ServiceImpl<PdrelationMapper, Pdrelation> implements PdrelationService {


    @Autowired
    private PdrelationService pdrelationService;


    @Override
    public boolean hasPrinciple(Integer pid) {
        QueryWrapper<Pdrelation> wrapper=new QueryWrapper<>();
        wrapper.eq("projectid",pid);
        Pdrelation pdrelation = pdrelationService.getOne(wrapper);
        if(pdrelation.getPrinciple()!=null&&pdrelation.getPrinciple()!="")
            return true;
        return false;
    }

    @Override
    public boolean hasQualifier(Integer pid) {
        QueryWrapper<Pdrelation> wrapper=new QueryWrapper<>();
        wrapper.eq("projectid",pid);
        Pdrelation pdrelation = pdrelationService.getOne(wrapper);
        if(pdrelation.getQualifier()!=null&&pdrelation.getQualifier()!="")
            return true;
        return false;
    }
}
