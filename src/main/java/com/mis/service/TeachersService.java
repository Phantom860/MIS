package com.mis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mis.dto.Result;
import com.mis.dto.TeacherCourseDTO;
import com.mis.entity.Teachers;

import java.util.List;
import java.util.ResourceBundle;

public interface TeachersService extends IService<Teachers> {
    List<TeacherCourseDTO> queryTeacherCourses(Long teacherId, String teacherName, Long courseId);

    /**
     * 删除教师
     * @param teacherId 教师id
     * @return 删除结果
     */
    Result deleteTeacherById(Long teacherId);
}
