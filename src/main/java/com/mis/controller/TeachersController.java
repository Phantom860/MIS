package com.mis.controller;

import com.mis.dto.Result;
import com.mis.dto.TeacherCourseDTO;
import com.mis.entity.Teachers;
import com.mis.service.TeachersService;
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
    public Result addTeacher(@RequestBody Teachers teachers) {
        log.info("新增教师：{}", teachers);
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
}
