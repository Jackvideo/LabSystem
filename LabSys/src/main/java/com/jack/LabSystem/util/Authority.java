package com.jack.LabSystem.util;

import com.alibaba.fastjson2.JSON;
import com.jack.LabSystem.model.entity.User;
import com.jack.LabSystem.service.IUserService;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;

/**
 * 权限类
 * 缓存登录用户，检查用户权限等级
 * @author Jackvideo
 * @date 2023/12/4 10:11
 */

public class Authority {

    private static Authority authority=new Authority();

    private Authority(){loginUser=null;}

    public static Authority getInstance(){
        return authority;
    }
    public void setLoginUser(User loginUser) {
        this.loginUser = loginUser;
    }

    private static User loginUser;
    public static int getAuthority(){
        switch (loginUser.getAuthority()){
            case "admin" : return 1;
            case "normal" : return 2;
            case "tourist" : return 0;
            default: return 0;
        }
    }
}
