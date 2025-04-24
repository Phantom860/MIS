package com.mis.controller;

import com.mis.dto.Result;
import com.mis.entity.Students;
import com.mis.service.StudentsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yourname
 * @since 2025-04-23
 */
@RestController
@RequestMapping("/students")
@Slf4j
public class StudentsController {
    @Autowired
    private StudentsService studentsService;

    // 根据id查询学生信息
    @GetMapping("/{id}")
    public Result getById(@PathVariable String id) {
        log.info("根据id查询学生：{}",id);
        Students student = studentsService.getByStudentId(id);
        if (student != null) {
            return Result.ok(student);
        } else {
            return Result.fail("未找到该学生");
        }
    }
}
