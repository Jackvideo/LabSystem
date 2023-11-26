package com.jack.LabSystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jack.LabSystem.model.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    public List<String> getRoleNameByUserId(Integer userId);
}
