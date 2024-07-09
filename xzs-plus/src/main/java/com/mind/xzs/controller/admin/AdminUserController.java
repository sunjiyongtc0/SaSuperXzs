package com.mind.xzs.controller.admin;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.SecureUtil;
import com.github.pagehelper.PageInfo;
import com.mind.xzs.controller.BaseApiController;
import com.mind.xzs.core.response.RestResponse;
import com.mind.xzs.domain.UserEntity;
import com.mind.xzs.domain.UserEventLogEntity;
import com.mind.xzs.domain.other.KeyValue;
import com.mind.xzs.domain.viewmodel.admin.user.UserEventLogVM;
import com.mind.xzs.domain.viewmodel.admin.user.UserEventPageRequestVM;
import com.mind.xzs.service.UserEventLogService;
import com.mind.xzs.service.UserService;
import com.mind.xzs.utils.DateTimeUtil;
import com.mind.xzs.utils.PageInfoHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <pre>
 *
 * </pre>
 *
 * @author sunjiyong
 * @since 2024/7/8 10:13
 */
@RestController("AdminUserController")
@RequestMapping(value = "/api/admin/user")
public class AdminUserController extends BaseApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserEventLogService userEventLogService;


    @RequestMapping(value = "/page/list", method = RequestMethod.POST)
    public RestResponse<PageInfo<UserEntity>> pageList(@RequestBody UserEntity model) {
        PageInfo<UserEntity> pageInfo = userService.userPage(model);
        PageInfo<UserEntity> page = PageInfoHelper.copyMap(pageInfo, d -> d);
        return RestResponse.ok(page);
    }


    @RequestMapping(value = "/select/{id}", method = RequestMethod.POST)
    public RestResponse<UserEntity> select(@PathVariable Long id) {
        UserEntity user = userService.getById(id);
        return RestResponse.ok(user);
    }



    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public RestResponse<UserEntity> edit(@RequestBody  UserEntity user) {
        if (user.getId() == null) {  //create
            UserEntity existUser = userService.getUserByUserName(user.getUserName());
            if (null != existUser) {
                return new RestResponse<>(2, "用户已存在");
            }
            if (StringUtils.isBlank(user.getPassword())) {
                return new RestResponse<>(3, "密码不能为空");
            }
        }
        if (user.getId() == null) {
            user.setId(generateId());
            String encodePwd= SecureUtil.sha256(user.getPassword());
            user.setPassword(encodePwd);
            user.setUserUuid(UUID.randomUUID().toString());
            user.setCreateTime(new Date());
            user.setLastActiveTime(new Date());
            user.setDeleted(false);
            userService.save(user);
        } else {
            UserEntity updateUser=userService.getById(user.getId());
            if (!StringUtils.isBlank(user.getPassword())) {
                if(!StringUtils.equals(updateUser.getPassword(),user.getPassword())){
                    String encodePwd= SecureUtil.sha256(user.getPassword());
                    user.setPassword(encodePwd);
                }
            }
            user.setModifyTime(new Date());
            userService.updateById(user);
        }
        return RestResponse.ok(user);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public RestResponse delete(@PathVariable Long id) {
        UserEntity user = userService.getById(id);
        user.setDeleted(true);
        userService.updateById(user);
        return RestResponse.ok();
    }



    @RequestMapping(value = "/selectByUserName", method = RequestMethod.POST)
    public RestResponse<List<KeyValue>> selectByUserName(@RequestBody String userName) {
        List<KeyValue> keyValues = userService.selectByUserName(userName);
        return RestResponse.ok(keyValues);
    }


    @RequestMapping(value = "/event/page/list", method = RequestMethod.POST)
    public RestResponse<PageInfo<UserEventLogVM>> eventPageList(@RequestBody UserEventPageRequestVM model) {
        PageInfo<UserEventLogEntity> pageInfo = userEventLogService.page(model);
        PageInfo<UserEventLogVM> page = PageInfoHelper.copyMap(pageInfo, d -> {
            UserEventLogVM vm = BeanUtil.copyProperties(d, UserEventLogVM.class);
            vm.setCreateTime(DateTimeUtil.dateFormat(d.getCreateTime()));
            return vm;
        });
        return RestResponse.ok(page);
    }
}
