package com.jack.LabSystem.service;

import com.jack.LabSystem.model.entity.Outcome;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jack.LabSystem.model.entity.Project;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jackvideo
 * @since 2023-11-11 20:23
 */
public interface OutcomeService extends IService<Outcome> {

    public Outcome findByName(String name);
}
