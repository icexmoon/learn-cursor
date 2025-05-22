package cn.icexmoon.oaservice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 
 * @TableName department
 */
@TableName(value ="department")
@Data
public class Department {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 父部门id，0表示根部门
     */
    private Long parentId;

    /**
     * 部门负责人
     */
    private Long userId;
}