package com.mind.xzs.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mind.xzs.core.constants.AuthConstant;
import com.mind.xzs.core.response.RestResponse;
import com.mind.xzs.core.response.SystemCode;
import com.mind.xzs.domain.UserEntity;
import com.mind.xzs.secure.dto.LoginDto;
import com.mind.xzs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <pre>
 *
 * </pre>
 *
 * @author sunjiyong
 * @since 2024/7/5 16:11
 */

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public RestResponse login(@RequestBody LoginDto loginDto) {

        String securePwd=SecureUtil.sha256(loginDto.getPassword());

        UserEntity sysUser = userService.getOne(Wrappers.<UserEntity>lambdaQuery()
                .eq(UserEntity::getUserName, loginDto.getUserName())
                .eq(UserEntity::getPassword,securePwd ));

        if (sysUser == null) {
            return RestResponse.fail(SystemCode.AuthError.getCode(),SystemCode.AuthError.getMessage());
        }

        if(sysUser.getStatus().intValue()==1){
            return RestResponse.fail(SystemCode.ParameterValidError.getCode(),"用户:" + loginDto.getUserName() + "已被禁用");
        }
        StpUtil.login(sysUser.getId());
        StpUtil.getSession().set(AuthConstant.CURRENT_USER,sysUser);
        Object ob=StpUtil.getTokenInfo();
        return RestResponse.ok(ob);
    }



    @PostMapping("/logout")
    @SaCheckLogin
    public RestResponse logout() {
        //获取token
        //   1. 尝试从request里读取 tokenName 默认值 satoken
        //   2. 尝试从请求体里面读取
        //   3. 尝试从header里读取
        //   4. 尝试从cookie里读取
        // 删除cookie
        String loginId=StpUtil.getLoginIdAsString();
        StpUtil.logout();
        return RestResponse.ok();
    }

}
