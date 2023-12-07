package com.jack.LabSystem.controller;

import com.jack.LabSystem.model.entity.User;
import com.jack.LabSystem.service.IUserService;
import com.jack.LabSystem.util.ResultUtil;
import com.sun.tracing.dtrace.ProviderAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author anthony
 * @since 2023-06-16
 */
@RestController
@RequestMapping("/user")
@CrossOrigin  //处理跨域，因为前端和后端的IP一致但端口不一致，所以浏览器认为跨域，不给访问，可用Ngx来解决
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    public ResultUtil<Map<String,Object>> login(@RequestBody User user){
        // 因为 user传过来为json字符串，所以用@RequestBody 进行实体转换

        // 业务代码在userService里完成
        Map<String,Object> data = userService.login(user);

        if(data != null){
            return ResultUtil.success(data);
        }
        return ResultUtil.fail("用户名或密码错误");
    }


    @GetMapping("/info")
    public ResultUtil<Map<String,Object>> getUserInfo(@RequestParam("token") String token){
        // @RequestParam("token") 是从url中获取值
        // 根据token获取用户信息，信息存进了redis中
        Map<String,Object> data = userService.getUserInfo(token);
        if(data != null){
            return ResultUtil.success(data);
        }
        return ResultUtil.fail("登录信息错误");
    }

    @PostMapping("/logout")
    public ResultUtil<?> logout(@RequestHeader("X-Token") String token){
        userService.logout(token);
        return ResultUtil.success("用户注销成功");
    }

}

