package com.mis.dto;

import lombok.Data;

@Data
public class CourseChoosingDTO {
    private Long studentId;
    private String studentName;

    private Long courseId;
    private String courseName;

    private Long teacherId;
    private String teacherName;

    private Integer chosenYear;
    private Integer score;
}

