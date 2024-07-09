package com.mind.xzs.core.auth;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import com.mind.xzs.domain.PowerEntity;
import com.mind.xzs.domain.RoleEntity;
import com.mind.xzs.domain.RolePowerEntity;
import com.mind.xzs.domain.UserEntity;
import com.mind.xzs.service.PowerService;
import com.mind.xzs.service.RolePowerService;
import com.mind.xzs.service.RoleService;
import com.mind.xzs.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;




@Component
public class StpInterfaceImpl implements StpInterface {

	@Resource
	private UserService userService;

	@Resource
	private RoleService roleService;

	@Resource
	private RolePowerService sysRolePowerService;

	@Resource
	private PowerService sysPowerService;

	/**
	 * 返回一个账号所拥有的权限码集合
	 */
	@Override
	public List<String> getPermissionList(Object loginId, String loginType) {
		System.out.println("======================getPermissionList======================");
		// 1. 声明权限码集合
		List<String> permissionList = new ArrayList<>();
		// 查询用户对应的角色
		UserEntity loginUser = userService.getOne(Wrappers.<UserEntity>lambdaQuery().eq(UserEntity::getId, loginId));
		List<Long> roleIds =new ArrayList<>();// userRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
		roleIds.add(loginUser.getRole());
		// 查询角色对应的权限
		List<RolePowerEntity> rolePowerLists = sysRolePowerService.list(Wrappers.<RolePowerEntity>lambdaQuery().in(RolePowerEntity::getRoleId, roleIds));
		List<Long> powerIds = rolePowerLists.stream().map(RolePowerEntity::getPowerId).collect(Collectors.toList());
		List<PowerEntity> sysPowers = sysPowerService.list(Wrappers.<PowerEntity>lambdaQuery().in(PowerEntity::getMenuId, powerIds).eq(PowerEntity::getEnable,1));
		permissionList = sysPowers.stream().map(PowerEntity::getPowerCode).collect(Collectors.toList());

		RoleEntity roleById = roleService.getById(loginUser.getRole());
		//对超管用户跳过所有权限标识认证
		if(StrUtil.equals(roleById.getRoleKey(),"admin")){
			permissionList.add("power:admin");
		}
		// 3. 返回权限码集合
		return permissionList;
	}

	/**
	 * 返回指定账号id所拥有的角色标识集合
	 * @param loginId 账号id
	 * @param loginType 账号类型
	 * @return 该账号id具有的角色标识集合
	 */
	@Override
	public List<String> getRoleList(Object loginId, String loginType) {
		System.out.println("======================getRoleList======================");
		SaSession session = StpUtil.getSessionByLoginId(loginId);

		UserEntity loginUser = userService.getOne(Wrappers.<UserEntity>lambdaQuery().eq(UserEntity::getId, loginId));
		List<String> roleList=new ArrayList<>();
		roleList.add(loginUser.getRole()+"");
		session.set("Role_List",roleList);
		StpUtil.getTokenSession().set("Role_List_token",roleList);
		return roleList;
	}

}
