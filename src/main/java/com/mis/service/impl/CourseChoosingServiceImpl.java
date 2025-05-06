package com.mis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mis.dto.CourseChoosingDTO;
import com.mis.dto.Result;
import com.mis.entity.CourseChoosing;
import com.mis.entity.Courses;
import com.mis.entity.Students;
import com.mis.mapper.CourseChoosingMapper;
import com.mis.mapper.CoursesMapper;
import com.mis.mapper.StudentsMapper;
import com.mis.service.CourseChoosingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseChoosingServiceImpl extends ServiceImpl<CourseChoosingMapper, CourseChoosing> implements CourseChoosingService {
    @Autowired
    private StudentsMapper studentsMapper;

    @Autowired
    private CoursesMapper coursesMapper;

    @Autowired
    private CourseChoosingMapper courseChoosingMapper;

    /**
     * 添加选课记录
     *
     * @param courseChoosing 选课记录对象
     * @return 添加结果
     */
    @Override
    public Result addCourseChoosing(CourseChoosing courseChoosing) {
        // 1. 查询学生信息
        Students student = studentsMapper.selectById(courseChoosing.getStudentId());
        if (student == null) {
            return Result.fail("学生不存在，无法选课");
        }

        // 2. 查询课程信息
        Courses course = coursesMapper.selectById(courseChoosing.getCourseId());
        if (course == null) {
            return Result.fail("课程不存在，无法选课");
        }

        // 3. 校验选课年份和课程取消年份
        Integer canceledYear = course.getCanceledYear();
        if (canceledYear != null && courseChoosing.getChosenYear() >= canceledYear) {
            return Result.fail("选课年份必须早于课程取消年份");
        }

        // 4. 校验学生年级是否符合课程要求
        int studentGrade = courseChoosing.getChosenYear() - student.getEntranceYear() + 1;
        if (studentGrade < course.getGrade()) {
            return Result.fail("学生年级不足，无法选修该课程");
        }

        // 5. 校验教师ID是否正确
        if (courseChoosing.getTeacherId() == null || !courseChoosing.getTeacherId().equals(course.getTeacherId())) {
            return Result.fail("教师ID与该课程的授课教师不匹配");
        }

        // 6. 保存选课信息
        int rows = courseChoosingMapper.insert(courseChoosing);
        if (rows > 0) {
            return Result.ok("选课成功");
        } else {
            return Result.fail("选课失败，数据库异常");
        }
    }


    /**
     * 删除选课记录
     *
     * @param studentId     学生ID
     * @param courseId      课程ID
     * @param teacherId     教师ID
     * @return 删除结果
     */
    @Override
    public boolean deleteCourseChoosing(Long studentId, Long courseId, Long teacherId) {
        QueryWrapper<CourseChoosing> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id", studentId)
                .eq("course_id", courseId)
                .eq("teacher_id", teacherId);
        int rows = this.baseMapper.delete(queryWrapper);
        return rows > 0;
    }

    @Override
    public List<CourseChoosingDTO> queryCourseChoosing(Long studentId, String studentName, Long courseId, String courseName) {
        return courseChoosingMapper.queryCourseChoosing(studentId, studentName, courseId, courseName);
    }

    @Override
    public boolean updateCourseChoosing(CourseChoosing courseChoosing, String role) {
        // 先查出数据库原始记录
        CourseChoosing existing = courseChoosingMapper.selectOne(new QueryWrapper<CourseChoosing>()
                .eq("student_id", courseChoosing.getStudentId())
                .eq("course_id", courseChoosing.getCourseId())
                .eq("teacher_id", courseChoosing.getTeacherId()));

        if (existing == null) {
            return false; // 没找到
        }

        if ("ADMIN".equals(role)) {
            // 管理员不能改成绩
            courseChoosing.setScore(existing.getScore());
        } else if ("TEACHER".equals(role)) {
            // 教师只能改成绩，其他字段不能改
            courseChoosing.setChosenYear(existing.getChosenYear());
            courseChoosing.setStudentId(existing.getStudentId());
            courseChoosing.setCourseId(existing.getCourseId());
            courseChoosing.setTeacherId(existing.getTeacherId());
        } else {
            return false; // 没权限
        }

        // 调用自定义更新
        return courseChoosingMapper.updateCourseChoosing(courseChoosing) > 0;
    }

}
