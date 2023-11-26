package com.jack.LabSystem.service;

import com.jack.LabSystem.model.entity.Subproject;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jackvideo
 * @since 2023-11-11 20:23
 */
public interface SubprojectService extends IService<Subproject> {

    public List<Subproject> findProject(Integer pid);

    public Subproject findOne(Integer pid, Integer spid);


}
