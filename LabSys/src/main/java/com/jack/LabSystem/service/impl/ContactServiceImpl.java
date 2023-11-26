package com.jack.LabSystem.service.impl;

import com.jack.LabSystem.model.entity.Contact;
import com.jack.LabSystem.mapper.ContactMapper;
import com.jack.LabSystem.service.ContactService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jackvideo
 * @since 2023-11-12 10:51
 */
@Service
public class ContactServiceImpl extends ServiceImpl<ContactMapper, Contact> implements ContactService {

}
