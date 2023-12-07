package com.jack.LabSystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jack.LabSystem.model.entity.Pdrelation;
import com.jack.LabSystem.mapper.PdrelationMapper;
import com.jack.LabSystem.service.PdrelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private PdrelationMapper pdrelationMapper;


    @Override
    public Integer hasPrinciple(Integer pid) {
        QueryWrapper<Pdrelation> wrapper=new QueryWrapper<>();
        wrapper.eq("projectid",pid);
        List<Pdrelation> pdrelationList = pdrelationMapper.selectList(wrapper);
        for(Pdrelation temppd:pdrelationList) {
            if (temppd.getPrinciple() != null && temppd.getPrinciple() != "")
                return temppd.getRecordid();
        }
        return -1;
    }

    @Override
    public Integer hasQualifier(Integer pid) {
        QueryWrapper<Pdrelation> wrapper=new QueryWrapper<>();
        wrapper.eq("projectid",pid);
        List<Pdrelation> pdrelationList = pdrelationMapper.selectList(wrapper);
        for(Pdrelation temppd:pdrelationList) {
            if (temppd.getQualifier() != null && temppd.getQualifier() != "")
                return temppd.getRecordid();
        }
        return -1;
    }
}
