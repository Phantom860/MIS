package com.mis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mis.dto.StudentCourseDTO;
import com.mis.entity.CourseChoosing;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseChoosingMapper extends BaseMapper<CourseChoosing> {

}
