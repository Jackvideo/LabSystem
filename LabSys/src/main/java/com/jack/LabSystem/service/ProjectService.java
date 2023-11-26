package com.jack.LabSystem.service;

import com.jack.LabSystem.model.entity.Project;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jack.LabSystem.model.entity.Researchlab;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jackvideo
 * @since 2023-11-11 20:19
 */
public interface ProjectService extends IService<Project> {

    public Project findByName(String name);

}
