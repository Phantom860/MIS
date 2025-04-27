package com.mis.dto;

import lombok.Data;

@Data
public class TeacherCourseDTO {
    private Long teacherId;
    private String teacherName;

    private Long courseId;
    private String courseName;
    private Integer credit;
    private Integer grade;
    private Integer canceledYear;
}

