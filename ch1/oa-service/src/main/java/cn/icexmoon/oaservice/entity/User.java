package cn.icexmoon.oaservice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 所属部门
     */
    private Long deptId;

    /**
     * 兼职部门
     */
    private String exDepts;

    /**
     * 部门名称
     */
    @TableField(exist = false)
    private String DeptName;

    /**
     * 职位
     */
    private Integer positionId;

    @TableField(exist = false)
    private Position position;
}