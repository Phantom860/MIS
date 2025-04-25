package com.mis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mis.entity.CourseChoosing;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface CourseChoosingMapper extends BaseMapper<CourseChoosing> {

    @Delete("DELETE FROM course_choosing WHERE student_id = #{studentId}")
    void deleteByStudentId(Long studentId);
}
