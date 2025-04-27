package com.mis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mis.dto.Result;
import com.mis.dto.TeacherCourseDTO;
import com.mis.entity.Courses;
import com.mis.entity.Teachers;
import com.mis.mapper.TeachersMapper;
import com.mis.service.CoursesService;
import com.mis.service.TeachersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeachersServiceImpl extends ServiceImpl<TeachersMapper, Teachers> implements TeachersService {

    @Autowired
    private TeachersMapper teachersMapper;

    @Autowired
    private CoursesService coursesService;

    @Override
    public List<TeacherCourseDTO> queryTeacherCourses(Long teacherId, String teacherName, Long courseId) {
        return teachersMapper.queryTeacherCourses(teacherId, teacherName, courseId);
    }

    @Override
    public Result deleteTeacherById(Long teacherId) {
        try {
            // 先检查教师是否存在
            if (teachersMapper.selectById(teacherId) == null) {
                return Result.fail("删除失败，教师id不存在");
            }

            // 检查是否有课程关联
            List<Courses> courses = coursesService.list(
                    new QueryWrapper<Courses>().eq("teacher_id", teacherId)
            );
            if (courses != null && !courses.isEmpty()) {
                return Result.fail("该教师仍有授课，无法删除，请先处理相关课程");   // 有关联课程，不允许删除
            }

            // 删除教师信息
            teachersMapper.deleteById(teacherId);
            return Result.ok("删除成功");
        } catch (Exception e) {
            // 记录日志或进一步处理异常
            return Result.fail("删除失败，请稍后再试");
        }
    }
}
