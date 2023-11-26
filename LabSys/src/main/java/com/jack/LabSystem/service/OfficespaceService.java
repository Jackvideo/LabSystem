package com.jack.LabSystem.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jack.LabSystem.model.entity.Officespace;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jack.LabSystem.model.entity.Researcher;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jackvideo
 * @since 2023-11-12 10:48
 */
public interface OfficespaceService extends IService<Officespace> {


    public Officespace findByName(String name);

}
