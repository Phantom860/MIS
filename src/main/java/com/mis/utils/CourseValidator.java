package com.mis.utils;

import com.mis.dto.Result;
import com.mis.entity.Courses;
import com.mis.mapper.CoursesMapper;
import com.mis.mapper.TeachersMapper;

import java.time.LocalDate;

public class CourseValidator {
    /**
     * 校验课程信息是否合法
     * @param course 课程实体
     * @param teachersMapper 用于验证 teacherId 是否存在
     * @return 校验结果
     */
    public static Result validate(Courses course,
                                  TeachersMapper teachersMapper,
                                  CoursesMapper coursesMapper,
                                  boolean isAdd) {

        if (course.getCourseId() == null || String.valueOf(course.getCourseId()).length() != 7) {
            return Result.fail("课程ID必须为7位");
        }

        if (isAdd && coursesMapper.selectById(course.getCourseId()) != null) {
            return Result.fail("ID已存在，请重新添加");
        }

        if (course.getCredit() == null || course.getCredit() < 0.5 || course.getCredit() > 10.0) {
            return Result.fail("学分不合法，应在0.5~10之间");
        }

        Integer canceledYear = course.getCanceledYear();
        if (canceledYear != null && (canceledYear < 1950 || canceledYear > LocalDate.now().getYear())) {
            return Result.fail("取消年份不合法");
        }

        if (course.getTeacherId() != null && teachersMapper.selectById(course.getTeacherId()) == null) {
            return Result.fail("教师ID不存在，无法修改课程信息");
        }

        return Result.ok();
    }

}
