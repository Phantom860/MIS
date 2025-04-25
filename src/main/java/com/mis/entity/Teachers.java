package com.mis.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("teachers")
public class Teachers implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "teacher_id")
    private Long teacherId;

    @TableField("name")
    private String teacherName;
}

