package com.mis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mis.entity.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Phantom
 * @since 2025-04-23
 */
public interface UsersMapper extends BaseMapper<Users> {
    @Select("SELECT * FROM users WHERE username = #{username}")
    Users selectByUsername(@Param("username") String username);

}
