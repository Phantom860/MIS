package com.mis.service.impl;

import com.mis.entity.Students;
import com.mis.mapper.StudentsMapper;
import com.mis.service.StudentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional
    /*
     * 根据id查询学生信息
     */
    public Students getByStudentId(long id) {
        return this.getById(id);
    }

    @Override
    public boolean deleteById(Long id) {
        return studentsMapper.deleteById(id) > 0;
    }

}
