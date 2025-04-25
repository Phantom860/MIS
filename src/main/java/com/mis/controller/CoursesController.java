package com.mis.controller;

import com.mis.dto.Result;
import com.mis.entity.Courses;
import com.mis.mapper.TeachersMapper;
import com.mis.service.CoursesService;
import com.mis.utils.CourseValidator;
import com.mis.utils.PermissionChecker;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Phantom
 * @since 2025-04-23
 */
@RestController
@RequestMapping("/courses")
@Slf4j
public class CoursesController {
    @Autowired
    private CoursesService coursesService;

    @Autowired
    private TeachersMapper teachersMapper;

    /**
     * 添加课程信息
     * @param courses 课程数据
     * @return 课程id
     */
    @PostMapping("/add")
    public Result addCourse(@RequestBody Courses courses, HttpServletRequest request) {
        // 权限检查
        if (!PermissionChecker.isAdmin(request)) {
            return Result.fail("权限不足：仅管理员可添加课程信息");
        }
        // 调用工具类校验
        Result validation = CourseValidator.validate(courses, teachersMapper);
        if (!validation.getSuccess()) {
            return validation; // 校验失败时直接返回
        }
        log.info("新增课程：{}", courses);
        coursesService.save(courses);
        return Result.ok(courses.getCourseId());
    }


    /**
     * 根据课程id或名称查询课程信息
     * @param courseId 课程id
     * @param courseName 课程名称
     * @return 课程信息
     */
    @GetMapping("/query")
    public Result queryCourses(
            @RequestParam(required = false) String courseId,
            @RequestParam(required = false) String courseName) {

        List<Courses> courses = coursesService.queryCourses(courseId, courseName);
        return Result.ok(courses);
    }


    /**
     * 根据课程id删除课程信息
     * @param courseId 课程id
     * @return 删除结果
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteCourse(@PathVariable("id") String courseId, HttpServletRequest request) {
        // 权限校验：只有管理员可操作
        if (!PermissionChecker.isAdmin(request)) {
            return Result.fail("权限不足：仅管理员可删除课程");
        }

        // 合法性校验：课程ID需为7位
        if (courseId == null || !courseId.matches("\\d{7}")) {
            return Result.fail("课程ID必须为7位数字");
        }

        boolean success = coursesService.deleteCourseById(courseId);
        if (success) {
            return Result.ok("删除成功");
        } else {
            return Result.fail("删除失败，课程ID可能不存在");
        }
    }


    /**
     * 修改课程信息
     * @param course 课程信息
     * @return 修改结果
     */
    @PutMapping("/update")
    public Result updateCourse(@RequestBody Courses course, HttpServletRequest request) {
        // 权限检查：只有管理员可以修改课程信息
        if (!PermissionChecker.isAdmin(request)) {
            return Result.fail("权限不足：仅管理员可修改课程信息");
        }

        // 调用工具类校验
        Result validation = CourseValidator.validate(course, teachersMapper);
        if (!validation.getSuccess()) {
            return validation; // 校验失败时直接返回
        }

        boolean updated = coursesService.updateCourse(course);
        return updated ? Result.ok("课程信息修改成功") : Result.fail("修改失败，课程可能不存在");
    }

}
