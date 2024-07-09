package com.mind.xzs.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("t_role_power")
public class RolePowerEntity {

  @TableId("role_id")
  private Long roleId;

  @TableField("power_id")
  private Long powerId;



}
