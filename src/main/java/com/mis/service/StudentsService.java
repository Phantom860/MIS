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


    Students getByStudentId(String id);
}
