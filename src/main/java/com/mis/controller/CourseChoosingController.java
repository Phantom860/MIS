package com.mis.controller;

import com.mis.dto.CourseChoosingDTO;
import com.mis.dto.CourseScoreStats;
import com.mis.dto.Result;
import com.mis.entity.CourseChoosing;
import com.mis.service.CourseChoosingService;
import com.mis.utils.PermissionChecker;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses-choosing")
@Slf4j
public class CourseChoosingController {

    @Autowired
    private CourseChoosingService courseChoosingService;

    /**
     * 添加选课信息
     * @param courseChoosing 选课数据
     * @return 课程id
     */
    @PostMapping("/add")
    public Result addCourseSelection(@RequestBody CourseChoosing courseChoosing, HttpServletRequest request) {
        // 权限检查
        if (!PermissionChecker.isAdmin(request)) {
            return Result.fail("权限不足：仅管理员可插入选课信息");
        }
        log.info("新增选课信息：{}", courseChoosing);
        return courseChoosingService.addCourseChoosing(courseChoosing);
    }

    /**
     * 删除选课信息
     * @param studentId 学生id
     * @param courseId 课程id
     * @param teacherId 教师id
     * @return 删除结果
     */
    @DeleteMapping("/delete")
    public Result deleteCourseChoosing(
            @RequestParam Long studentId,
            @RequestParam Long courseId,
            @RequestParam Long teacherId,
            HttpServletRequest request) {

        // 权限检查
        if (!PermissionChecker.isAdmin(request)) {
            return Result.fail("权限不足：仅管理员可删除选课信息");
        }

        boolean success = courseChoosingService.deleteCourseChoosing(studentId, courseId, teacherId);
        if (success) {
            return Result.ok("删除成功");
        } else {
            return Result.fail("删除失败，记录不存在");
        }
    }

    /**
     * 查询选课信息
     * @param studentId 学生id
     * @param studentName 学生姓名
     * @param courseId 课程id
     * @param courseName 课程名称
     * @return 选课信息列表
     */
    @GetMapping("/list")
    public Result listCourseChoosing(
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) String studentName,
            @RequestParam(required = false) Long courseId,
            @RequestParam(required = false) String courseName) {
        log.info("查询选课信息: studentId={}, studentName={}, courseId={}, courseName={}",
                studentId, studentName, courseId, courseName);

        List<CourseChoosingDTO> list = courseChoosingService.queryCourseChoosing(studentId, studentName, courseId, courseName);

        return Result.ok(list);
    }

    /**
     * 更新选课信息
     * @param courseChoosing 选课数据
     * @return 更新结果
     */
    @PutMapping("/update")
    public Result updateCourseChoosing(@RequestBody CourseChoosing courseChoosing, HttpServletRequest request) {
        String role = PermissionChecker.getRole(request);

        // 只有教师可修改成绩
        if (!"TEACHER".equals(role)) {
            return Result.fail("权限不足：只有教师可以修改成绩信息");
        }

        log.info("更新选课信息：{}，操作人角色：{}", courseChoosing, role);

        boolean success = courseChoosingService.updateCourseChoosing(courseChoosing, role);
        if (success) {
            return Result.ok("更新成功");
        } else {
            return Result.fail("更新失败：记录不存在或权限不足");
        }
    }

    /**
     * 获取课程成绩统计
     * @param courseId 课程id
     * @return 课程成绩统计
     */
    @GetMapping("/{courseId}/score-stats")
    public CourseScoreStats getCourseScoreStats(@PathVariable Long courseId) {
        return courseChoosingService.getCourseScoreStats(courseId);
    }

}
