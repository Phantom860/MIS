package com.mis.service.impl;

import com.mis.dto.StudentCourseDTO;
import com.mis.entity.Students;
import com.mis.mapper.CourseChoosingMapper;
import com.mis.mapper.CoursesMapper;
import com.mis.mapper.StudentsMapper;
import com.mis.service.StudentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yourname
 * @since 2025-04-23
 */
@Service
public class StudentsServiceImpl extends ServiceImpl<StudentsMapper, Students> implements StudentsService {

    @Autowired
    private StudentsMapper studentsMapper;

    @Autowired
    private CourseChoosingMapper courseChoosingMapper;

    @Autowired
    private CoursesMapper coursesMapper;

    @Override
    @Transactional
    /*
     * 根据id查询学生信息
     */
    public Students getByStudentId(long id) {
        return this.getById(id);
    }

    @Override
    public List<StudentCourseDTO> getStudentWithCourses(Long studentId, String name) {
        return studentsMapper.queryStudentCourse(studentId, name);
    }

    /*
     * 根据id删除学生信息
     */
    @Override
    public boolean deleteStudentById(Long studentId) {
        try {
            // 先检查学生是否存在
            if (studentsMapper.selectById(studentId) == null) {
                return false;  // 如果学生不存在，返回 false
            }

            // 先删除选课信息（虽然数据库已经设置了级联删除，但可以先删除选课信息确保删除成功）
            courseChoosingMapper.deleteByStudentId(studentId);

            // 再删除学生信息
            studentsMapper.deleteById(studentId);

            return true;
        } catch (Exception e) {
            // 记录日志或进一步处理异常
            return false;
        }
    }

}
