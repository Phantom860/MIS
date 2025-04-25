package com.mis.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("course_choosing")
public class CourseChoosing implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long studentId;

    private Long courseId;

    private Long teacherId;

    private Integer chosenYear;

    private Double score;
}
