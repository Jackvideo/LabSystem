package com.jack.LabSystem.service;

import com.jack.LabSystem.model.entity.Researchlab;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jackvideo
 * @since 2023-11-11 18:50
 */
public interface ResearchlabService extends IService<Researchlab> {


    public Researchlab findByName(String name);
}
