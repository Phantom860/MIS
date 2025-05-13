package com.mis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mis.dto.CourseChoosingDTO;
import com.mis.dto.CourseScoreStats;
import com.mis.dto.Result;
import com.mis.entity.CourseChoosing;

import java.util.List;

public interface CourseChoosingService extends IService<CourseChoosing> {
    Result addCourseChoosing(CourseChoosing courseChoosing);

    boolean deleteCourseChoosing(Long studentId, Long courseId, Long teacherId);

    List<CourseChoosingDTO> queryCourseChoosing(Long studentId, String studentName, Long courseId, String courseName);

    boolean updateCourseChoosing(CourseChoosing courseChoosing, String role);

    CourseScoreStats getCourseScoreStats(Long courseId);
}
