package com.mis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mis.entity.Courses;
import com.mis.mapper.CoursesMapper;
import com.mis.service.CoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoursesServiceImpl extends ServiceImpl<CoursesMapper, Courses> implements CoursesService {

    @Autowired
    private CoursesMapper coursesMapper;

}
