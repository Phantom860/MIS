package com.mis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mis.dto.TeacherCourseDTO;
import com.mis.entity.Teachers;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TeachersMapper extends BaseMapper<Teachers> {
    List<TeacherCourseDTO> queryTeacherCourses(@Param("teacherId") Long teacherId,
                                               @Param("teacherName") String teacherName,
                                               @Param("courseId") Long courseId);
}
