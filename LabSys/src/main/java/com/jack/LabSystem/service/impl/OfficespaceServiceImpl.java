package com.jack.LabSystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jack.LabSystem.model.entity.Officespace;
import com.jack.LabSystem.mapper.OfficespaceMapper;
import com.jack.LabSystem.model.entity.Researcher;
import com.jack.LabSystem.service.OfficespaceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jackvideo
 * @since 2023-11-12 10:48
 */
@Service
public class OfficespaceServiceImpl extends ServiceImpl<OfficespaceMapper, Officespace> implements OfficespaceService {

    @Autowired
    private OfficespaceMapper officespaceMapper;

    @Override
    public Officespace findByName(String name) {
        QueryWrapper<Officespace> wrapper=new QueryWrapper<>();
        wrapper.eq("Spacename",name);
        return officespaceMapper.selectOne(wrapper);
    }
}
