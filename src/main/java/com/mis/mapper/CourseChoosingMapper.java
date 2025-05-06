package com.mis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mis.dto.CourseChoosingDTO;
import com.mis.entity.CourseChoosing;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface CourseChoosingMapper extends BaseMapper<CourseChoosing> {

    @Delete("DELETE FROM course_choosing WHERE student_id = #{studentId}")
    void deleteByStudentId(Long studentId);

    @Delete("DELETE FROM course_choosing WHERE course_id = #{courseId}")
    void deleteByCourseId(Long courseId);

    List<CourseChoosingDTO> queryCourseChoosing(
            @Param("studentId") Long studentId,
            @Param("studentName") String studentName,
            @Param("courseId") Long courseId,
            @Param("courseName") String courseName);

    int updateCourseChoosing(CourseChoosing courseChoosing);
}
