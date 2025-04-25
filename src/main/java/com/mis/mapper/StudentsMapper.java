package com.mis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mis.dto.StudentCourseDTO;
import com.mis.entity.Students;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Phantom
 * @since 2025-04-23
 */
public interface StudentsMapper extends BaseMapper<Students> {

    List<StudentCourseDTO> queryStudentCourse(@Param("studentId") Long studentId,
                                              @Param("name") String name);
}
