package com.jack.LabSystem.service;

import com.jack.LabSystem.model.entity.Pdrelation;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jackvideo
 * @since 2023-12-05 10:24
 */
public interface PdrelationService extends IService<Pdrelation> {


    public Integer hasPrinciple(Integer projectid);

    public Integer hasQualifier(Integer projectid);

}
