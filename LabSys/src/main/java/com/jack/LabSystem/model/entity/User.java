package com.jack.LabSystem.model.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

@Data
public class User {

    //忽略用户模块，直接写死
    public User(){
        username="admin";
        password="111111";
    };

    @Autowired
    public int id;

    @Autowired
    public String username;

    @Autowired
    public String password;

}
