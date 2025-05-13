package com.mis.controller;

import com.mis.dto.Result;
import com.mis.dto.StudentCourseDTO;
import com.mis.entity.Students;
import com.mis.entity.Teachers;
import com.mis.service.StudentsService;
import com.mis.utils.PermissionChecker;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mis.dto.Result.ok;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Phantom
 * @since 2025-04-23
 */
@RestController
@RequestMapping("/students")
@Slf4j
public class StudentsController {
    @Autowired
    private StudentsService studentsService;

    /**
     * 添加学生信息
     * @param student 学生数据
     * @return 学生id
     */
    @PostMapping("/add")
    public Result addStudent(@RequestBody Students student, HttpServletRequest request) {
        if (!PermissionChecker.isAdmin(request)) {
            return Result.fail("权限不足：仅管理员可添加学生");
        }
        // 查询数据库，检查是否已存在该id
        Students existingStudent = studentsService.getById(student.getStudentId());
        if (existingStudent != null) {
            return Result.fail("ID已存在，请重新添加");
        }
        log.info("新增学生：{}", student);
        studentsService.save(student);
        // 返回学生id
        return Result.ok(student.getStudentId());
    }

    /*
     * 根据id查询学生信息
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable long id) {
        log.info("根据id查询学生：{}",id);
        Students student = studentsService.getByStudentId(id);
        if (student != null) {
            return ok(student);
        } else {
            return Result.fail("未找到该学生");
        }
    }

    /**
     * 删除学生信息
     * @param studentId 学生id
     * @return 无
     */
    @DeleteMapping("/delete/{studentId}")
    public Result deleteStudent(@PathVariable Long studentId, HttpServletRequest request) {
        log.info("根据id删除学生信息：{}",studentId);
        // 权限检查：只有管理员能删
        if (!PermissionChecker.isAdmin(request)) {
            return Result.fail("权限不足：仅管理员可删除学生");
        }

        boolean success = studentsService.deleteStudentById(studentId);
        if (success) {
            return Result.ok("删除成功");
        } else {
            return Result.fail("删除失败，学生ID可能不存在");
        }
    }

    /**
     * 修改学生信息
     * @param student 学生数据
     * @return 无
     */
    @PutMapping("/update")
    public Result updateStudent(@RequestBody Students student, HttpServletRequest request) {
        // 权限检查
        if (!PermissionChecker.isAdmin(request)) {
            return Result.fail("权限不足：仅管理员可修改学生信息");
        }
        log.info("修改学生：{}", student);
        boolean success = studentsService.updateById(student);
        if (success) {
            return Result.ok("更新成功");
        } else {
            return Result.fail("更新失败，学生可能不存在");
        }
    }

    /**
     * 根据学生id或姓名查询学生信息和选课信息
     * @param studentId 学生id
     * @param name 学生姓名
     * @return 学生信息和选课信息
     */
    @GetMapping("/course-info")
    public Result queryStudentWithCourses(
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) String name
    ) {
        List<StudentCourseDTO> result = studentsService.getStudentWithCourses(studentId, name);
        return Result.ok(result);
    }

}
