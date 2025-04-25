package com.mis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mis.entity.Courses;

import java.util.List;

public interface CoursesService extends IService<Courses> {

    /*
    * 根据课程编号和课程名查询课程信息
    */
    List<Courses> queryCourses(String courseId, String courseName);

    /**
     * 根据课程id删除课程信息
     * @param courseId 课程id
     * @return 删除结果
     */
    boolean deleteCourseById(String courseId);

    /**
     * 更新课程信息
     * @param course 课程信息
     * @return 更新结果
     */
    boolean updateCourse(Courses course);
}
