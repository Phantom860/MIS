package com.mis.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class CourseChoosingId implements Serializable {

    private Long studentId;
    private Long courseId;
    private Long teacherId;
}
