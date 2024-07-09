package com.mind.xzs.secure.util;//package com.mind.xzs.secure.util;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.mind.xzs.core.constants.AuthConstant;
import com.mind.xzs.domain.UserEntity;
import com.mind.xzs.secure.dto.SimpleUser;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *
 * </pre>
 *
 * @author sunjiyong
 * @since 2024/7/5 13:48
 */
public class UserInfoUtil {

    // 登录接口，什么也不传，通过token获取用户信息
    public static Map<String, Object> userinfo() {
        Map<String, Object> userInfo = new HashMap<>();
        SaSession session = StpUtil.getSessionByLoginId(StpUtil.getLoginId());
        SimpleUser simpleUser = (SimpleUser) session.get(AuthConstant.CURRENT_USER);
        if (simpleUser == null) {
            throw new RuntimeException("获取用户信息失败！");
        } else {
            //获取用户详细信息
            userInfo = object2Map(simpleUser);
        }
        return userInfo;
    }


    // 登录接口，什么也不传，通过token获取用户信息
    public static UserEntity userinfoEntity() {
        SaSession session = StpUtil.getSessionByLoginId(StpUtil.getLoginId());
        UserEntity currentUser = (UserEntity) session.get(AuthConstant.CURRENT_USER);
        if (currentUser == null) {
            throw new RuntimeException("获取用户信息失败！");
        } else {
            //获取用户详细信息
            return currentUser;
        }

    }


    /**
     * 根据当前用户获取绑定的角色
     *
     * @return
     */
    public static List<String> getUserRole() {
        SaSession session = StpUtil.getSessionByLoginId(StpUtil.getLoginId());
        List<String> roleList =(List<String>) session.get(AuthConstant.CURRENT_USER_ROLE_LIST);
        return roleList;
    }


    /**
     * 实体对象转成Map
     *
     * @param obj 实体对象
     * @return
     */
    public static Map<String, Object> object2Map(Object obj) {
        Map<String, Object> map = new HashMap<>();
        if (obj == null) {
            return map;
        }
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
