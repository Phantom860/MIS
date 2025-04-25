package com.mis.controller;

import com.mis.dto.Result;
import com.mis.entity.Teachers;
import com.mis.service.TeachersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teachers")
@Slf4j
public class TeachersController {

    @Autowired
    private TeachersService teachersService;

    /**
     * 添加课程信息
     * @param teachers 课程数据
     * @return 课程id
     */
    @PostMapping("/add")
    public Result addTeacher(@RequestBody Teachers teachers) {
        log.info("新增教师：{}", teachers);
        teachersService.save(teachers);
        return Result.ok(teachers.getTeacherId());
    }
}
