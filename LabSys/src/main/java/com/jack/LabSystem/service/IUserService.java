package com.jack.LabSystem.service;

import com.jack.LabSystem.model.entity.User;

import java.util.Map;

public interface IUserService {

    Map<String, Object> getUserInfo(String token);

    void logout(String token);

    Map<String, Object> login(User user);
}
