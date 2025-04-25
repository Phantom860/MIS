package com.mis.dto;

import lombok.Data;
import java.util.List;

@Data
public class StudentCourseDTO {
    private Long studentId;
    private String name;
    private String sex;
    private Integer entranceAge;
    private Integer entranceYear;
    private String className;

    private Long courseId;
    private String courseName;
    private Long teacherId;
    private String teacherName;
    private Integer credit;
    private Integer grade;
    private Integer canceledYear;

    private Integer chosenYear;
    private Integer score;
}


