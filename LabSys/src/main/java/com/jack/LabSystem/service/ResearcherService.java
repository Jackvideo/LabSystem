package com.jack.LabSystem.service;

import com.jack.LabSystem.model.entity.Researcher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jack.LabSystem.util.ResultUtil;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jackvideo
 * @since 2023-11-11 20:18
 */
public interface ResearcherService extends IService<Researcher> {

    public Researcher findByName(String name);
}
