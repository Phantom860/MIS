package com.mis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mis.entity.Students;

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

}
