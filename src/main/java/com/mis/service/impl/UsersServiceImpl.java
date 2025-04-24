package com.mis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mis.dto.LoginDTO;
import com.mis.dto.Result;
import com.mis.dto.UserDTO;
import com.mis.entity.Users;
import com.mis.mapper.UsersMapper;
import com.mis.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yourname
 * @since 2025-04-23
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

    @Autowired
    private UsersMapper usersMapper;

    @Override
    public Result login(LoginDTO loginDTO) {
        // 1. 根据用户名查询用户信息
        Users user = usersMapper.selectByUsername(loginDTO.getUsername());

        // 2. 校验用户是否存在以及密码是否正确
        if (user == null || !user.getPassword().equals(loginDTO.getPassword())) {
            return Result.fail("用户名或密码错误");
        }

        // 3. 登录成功，封装 UserDTO 返回
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setUsername(user.getUsername());
        userDTO.setRole(user.getRole().name());

        return Result.ok(userDTO);
    }
}
