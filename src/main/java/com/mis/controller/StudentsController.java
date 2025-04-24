package com.mis.controller;

import com.mis.dto.Result;
import com.mis.entity.Students;
import com.mis.service.StudentsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Result addStudent(@RequestBody Students student) {
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
     * @param id 学生id
     * @return 无
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteStudent(@PathVariable Long id) {
        log.info("删除学生，ID：{}", id);
        studentsService.deleteById(id);
        return Result.ok("删除成功") ;
    }

    /**
     * 更新学生信息
     * @param student 学生数据
     * @return 无
     */
    @PutMapping("/update")
    public Result updateStudent(@RequestBody Students student) {
        log.info("更新学生：{}", student);
        studentsService.updateById(student);
        return Result.ok("更新成功");
    }


}
