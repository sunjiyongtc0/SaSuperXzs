package com.mind.xzs.domain;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_role")
public class RoleEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @TableId("role_id")
  private Long roleId;

  @TableField("role_name")
  private String roleName;

  @TableField("role_key")
  private String roleKey;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date createTime;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date updateTime;

  @TableField("del_flag")
  private int delFlag;

//  @TableField(exist = false)
//  private List<SysMenu> SysMenuList;

  @TableField(exist = false)
  private String menuId;

  @TableField(exist = false)
  private Integer pageNum;

  @TableField(exist = false)
  private Integer pageSize;
}
