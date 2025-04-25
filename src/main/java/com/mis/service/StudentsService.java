package com.mis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mis.dto.StudentCourseDTO;
import com.mis.entity.Students;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yourname
 * @since 2025-04-23
 */
public interface StudentsService extends IService<Students> {

    /*
     * 根据id查询学生信息
     */
    Students getByStudentId(long id);

    /*
     * 根据id删除学生信息
     */
    boolean deleteById(Long id);

    /*
     * 根据id或姓名查询学生信息
     */
    List<StudentCourseDTO> getStudentWithCourses(Long studentId, String name);
}
