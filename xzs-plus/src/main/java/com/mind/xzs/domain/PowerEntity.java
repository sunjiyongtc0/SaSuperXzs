package com.mind.xzs.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_power")
public class PowerEntity {

  @TableId
  private Long id;

  @TableField("menu_id")
  private long menuId;

  @TableField("power_code")
  private String powerCode;
  private String remark;
  private long enable;
  private long sort;

  @TableField("power_url")
  private String powerUrl;

  @TableField(exist = false)
  private long parentMenuId;

  @TableField(exist = false)
  private String menuName;

  @TableField(exist = false)
  private String roleId;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  private Date createTime;

  @TableField(exist = false)
  private Integer pageNum;

  @TableField(exist = false)
  private Integer pageSize;


}
