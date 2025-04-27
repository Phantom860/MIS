package com.mis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mis.dto.TeacherCourseDTO;
import com.mis.entity.Teachers;

import java.util.List;

public interface TeachersService extends IService<Teachers> {
    List<TeacherCourseDTO> queryTeacherCourses(Long teacherId, String teacherName, Long courseId);
}
