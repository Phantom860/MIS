package com.mis.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("courses")
public class Courses implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "course_id")
    private Long courseId;

    private String name;

    private Long teacherId;

    private Integer credit;

    private Integer grade;

    private Integer canceledYear;
}
