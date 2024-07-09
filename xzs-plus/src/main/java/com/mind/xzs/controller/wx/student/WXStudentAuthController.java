package com.mind.xzs.controller.wx.student;
//
//import com.mindskip.xzs.base.RestResponse;
//import com.mindskip.xzs.configuration.property.SystemConfig;
//import com.mindskip.xzs.controller.wx.BaseWXApiController;
//import com.mindskip.xzs.domain.User;
//import com.mindskip.xzs.domain.UserToken;
//import com.mindskip.xzs.domain.enums.UserStatusEnum;
//import com.mindskip.xzs.service.AuthenticationService;
//import com.mindskip.xzs.service.UserService;
//import com.mindskip.xzs.service.UserTokenService;
//import com.mindskip.xzs.utility.WxUtil;
//import com.mindskip.xzs.viewmodel.wx.student.user.BindInfo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.validation.Valid;
//import javax.validation.constraints.NotBlank;


import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.mind.xzs.controller.wx.BaseWXApiController;
import com.mind.xzs.core.config.SystemConfig;
import com.mind.xzs.core.response.RestResponse;
import com.mind.xzs.domain.UserEntity;
import com.mind.xzs.domain.UserTokenEntity;
import com.mind.xzs.domain.enums.UserStatusEnum;
import com.mind.xzs.domain.viewmodel.user.wx.BindInfo;
import com.mind.xzs.service.UserService;
import com.mind.xzs.service.UserTokenService;
import com.mind.xzs.utils.WxUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("WXStudentAuthController")
@RequestMapping(value = "/api/wx/student/auth")
@ResponseBody
public class WXStudentAuthController extends BaseWXApiController {


    @Autowired
    private  SystemConfig systemConfig;
//    private final AuthenticationService authenticationService;
    @Autowired
    private  UserService userService;
    @Autowired
    private  UserTokenService userTokenService;
//
//    @Autowired
//    public AuthController(SystemConfig systemConfig, AuthenticationService authenticationService, UserService userService, UserTokenService userTokenService) {
//        this.systemConfig = systemConfig;
//        this.authenticationService = authenticationService;
//        this.userService = userService;
//        this.userTokenService = userTokenService;
//    }

    @RequestMapping(value = "/bind", method = RequestMethod.POST)
    public RestResponse bind(BindInfo model) {
        UserEntity user = userService.getUserByUserName(model.getUserName());
        if (user == null) {
            return RestResponse.fail(2, "用户名或密码错误");
        }
        boolean result =false;
        String securePwd= SecureUtil.sha256(model.getPassword());
        if(StrUtil.equals(model.getUserName(),user.getUserName())&&StrUtil.equals(securePwd,user.getPassword())){
             result =true;
        }
        if (!result) {
            return RestResponse.fail(2, "用户名或密码错误");
        }
        UserStatusEnum userStatusEnum = UserStatusEnum.fromCode(user.getStatus());
        if (UserStatusEnum.Disable == userStatusEnum) {
            return RestResponse.fail(3, "用户被禁用");
        }
        String code = model.getCode();
        String openid = WxUtil.getOpenId(systemConfig.getWx().getAppid(), systemConfig.getWx().getSecret(), code);
        if (null == openid) {
            return RestResponse.fail(4, "获取微信OpenId失败");
        }
        user.setWxOpenId(openid);
        UserTokenEntity userToken = userTokenService.bind(user);
        return RestResponse.ok(userToken.getToken());
    }


    @RequestMapping(value = "/checkBind", method = RequestMethod.POST)
    public RestResponse checkBind(String code) {
        String openid = WxUtil.getOpenId(systemConfig.getWx().getAppid(), systemConfig.getWx().getSecret(), code);
        if (null == openid) {
            return RestResponse.fail(3, "获取微信OpenId失败");
        }
        UserTokenEntity userToken = userTokenService.checkBind(openid);
        if (null != userToken) {
            return RestResponse.ok(userToken.getToken());
        }
        return RestResponse.fail(2, "用户未绑定");
    }


    @RequestMapping(value = "/unBind", method = RequestMethod.POST)
    public RestResponse unBind() {
        UserTokenEntity userToken = getUserToken();
        userTokenService.unBind(userToken);
        return RestResponse.ok();
    }
}
