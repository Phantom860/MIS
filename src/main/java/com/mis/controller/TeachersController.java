package com.mis.controller;

import com.mis.dto.Result;
import com.mis.dto.TeacherCourseDTO;
import com.mis.entity.Teachers;
import com.mis.service.TeachersService;
import com.mis.utils.PermissionChecker;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
@Slf4j
public class TeachersController {

    @Autowired
    private TeachersService teachersService;


    /**
     * 添加教师信息
     * @param teachers 课程数据
     * @return 课程id
     */
    @PostMapping("/add")
    public Result addTeacher(@RequestBody Teachers teachers, HttpServletRequest request) {
        // 检查权限，确保只有管理员可以添加教师
        if (!PermissionChecker.isAdmin(request)) {
            return Result.fail("权限不足：仅管理员可添加教师");
        }

        log.info("新增教师：{}", teachers);

        // 查询数据库，检查是否已存在该 teacher_id
        Teachers existingTeacher = teachersService.getById(teachers.getTeacherId());
        if (existingTeacher != null) {
            return Result.fail("ID已存在，请重新添加");
        }

        // 如果 teacher_id 不存在，则保存教师信息
        teachersService.save(teachers);

        return Result.ok(teachers.getTeacherId());
    }


    /**
     * 查询教师及其所授课程信息
     * @param teacherId 教师ID（可选）
     * @param teacherName 教师姓名（可选）
     * @param courseId 课程编号（可选）
     * @return 查询结果
     */
    @GetMapping("/query")
    public Result queryTeacherCourses(@RequestParam(required = false) Long teacherId,
                                      @RequestParam(required = false) String teacherName,
                                      @RequestParam(required = false) Long courseId) {
        List<TeacherCourseDTO> result = teachersService.queryTeacherCourses(teacherId, teacherName, courseId);
        return Result.ok(result);
    }

    /**
     * 删除教师信息
     * @param teacherId 教师ID
     * @return 删除结果
     */
    @DeleteMapping("/delete/{teacherId}")
    public Result deleteTeacher(@PathVariable Long teacherId, HttpServletRequest request) {
        // 权限校验
        if (!PermissionChecker.isAdmin(request)) {
            return Result.fail("权限不足：仅管理员可删除教师");
        }
        log.info("根据id删除老师信息：{}",teacherId);
        return teachersService.deleteTeacherById(teacherId);
    }

    /**
     * 修改教师信息
     * @param teacher 教师信息
     * @return 修改结果
     */
    @PutMapping("/update")
    public Result updateTeacher(@RequestBody Teachers teacher, HttpServletRequest request) {
        // 权限检查
        if (!PermissionChecker.isAdmin(request)) {
            return Result.fail("权限不足：仅管理员可修改教师信息");
        }
        log.info("修改教师信息：{}", teacher);
        boolean success = teachersService.updateById(teacher);
        if (success) {
            return Result.ok("更新成功");
        } else {
            return Result.fail("更新失败，教师可能不存在");
        }
    }

}
