package com.mis.controller;

import com.mis.dto.Result;
import com.mis.entity.Courses;
import com.mis.service.CoursesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 添加课程信息
     * @param courses 课程数据
     * @return 课程id
     */
    @PostMapping("/add")
    public Result addCourse(@RequestBody Courses courses) {
        log.info("新增课程：{}", courses);
        coursesService.save(courses);
        return Result.ok(courses.getCourseId());
    }



}
