package com.mis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mis.entity.Courses;
import com.mis.mapper.CourseChoosingMapper;
import com.mis.mapper.CoursesMapper;
import com.mis.service.CoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CoursesServiceImpl extends ServiceImpl<CoursesMapper, Courses> implements CoursesService {

    @Autowired
    private CoursesMapper coursesMapper;

    @Autowired
    private CourseChoosingMapper courseChoosingMapper;

    /**
     * 根据课程编号和课程名称查询课程信息
     * @param courseId   课程编号
     * @param courseName 课程名称
     * @return 课程信息列表
     */
    @Override
    public List<Courses> queryCourses(String courseId, String courseName) {
        QueryWrapper<Courses> wrapper = new QueryWrapper<>();

        if (StringUtils.hasText(courseId)) {
            // 优先匹配课程ID（精确）
            wrapper.eq("course_id", courseId);
        } else if (StringUtils.hasText(courseName)) {
            // 模糊匹配课程名称
            wrapper.like("name", courseName);
        }

        return coursesMapper.selectList(wrapper);
    }

    /**
     * 根据课程id删除课程信息
     * @param courseId 课程id
     * @return 删除结果
     */
    @Override
    public boolean deleteCourseById(String courseId) {
        // 先检查该课程是否存在
        Courses course = coursesMapper.selectById(courseId);
        if (course == null) {
            return false;
        }

        // 删除课程记录
        int rows = coursesMapper.deleteById(courseId);
        return rows > 0;
    }

    /**
     * 更新课程信息
     * @param course 课程信息
     * @return 更新结果
     */
    @Override
    public boolean updateCourse(Courses course) {
        int rows = coursesMapper.updateById(course);
        return rows > 0;
    }
}
