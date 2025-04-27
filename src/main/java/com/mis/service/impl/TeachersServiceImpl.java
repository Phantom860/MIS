package com.mis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mis.dto.TeacherCourseDTO;
import com.mis.entity.Teachers;
import com.mis.mapper.TeachersMapper;
import com.mis.service.TeachersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeachersServiceImpl extends ServiceImpl<TeachersMapper, Teachers> implements TeachersService {

    @Autowired
    private TeachersMapper teachersMapper;


    @Override
    public List<TeacherCourseDTO> queryTeacherCourses(Long teacherId, String teacherName, Long courseId) {
        return teachersMapper.queryTeacherCourses(teacherId, teacherName, courseId);
    }
}
