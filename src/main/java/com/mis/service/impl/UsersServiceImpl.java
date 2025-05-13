package com.mis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mis.dto.LoginDTO;
import com.mis.dto.RegisterDTO;
import com.mis.dto.Result;
import com.mis.dto.UserDTO;
import com.mis.entity.Role;
import com.mis.entity.Users;
import com.mis.mapper.UsersMapper;
import com.mis.service.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
    public Result login(LoginDTO loginDTO, HttpServletRequest request) {
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

        // ✅ 4. 将登录用户信息保存到 session 中（特别是 role）
        request.getSession().setAttribute("user", userDTO);

        return Result.ok(userDTO);
    }

    @Override
    public Result register(RegisterDTO registerDTO) {
        String username = registerDTO.getUsername();
        String password = registerDTO.getPassword();
        String roleStr = registerDTO.getRole();

        // 基本校验
        if (username == null || password == null || roleStr == null) {
            return Result.fail("用户名、密码和角色不能为空");
        }

        // 查询是否已存在
        QueryWrapper<Users> query = new QueryWrapper<>();
        query.eq("username", username);
        Users existingUser = usersMapper.selectOne(query);
        if (existingUser != null) {
            return Result.fail("用户名已存在");
        }

        // 将字符串角色转换为枚举类型
        Role role;
        try {
            role = Role.valueOf(roleStr.toUpperCase());  // 将字符串转换为枚举
        } catch (IllegalArgumentException e) {
            return Result.fail("无效的角色类型，必须是 STUDENT、TEACHER 或 ADMIN");
        }

        // 创建新用户
        Users newUser = new Users();
        newUser.setUsername(username);
        newUser.setPassword(password); // 密码可以加密处理
        newUser.setRole(role);  // 设置为枚举类型

        // 保存用户到数据库
        int rows = usersMapper.insert(newUser);
        if (rows > 0) {
            return Result.ok("注册成功");
        } else {
            return Result.fail("注册失败，数据库异常");
        }
    }

    @Override
    public boolean deleteUserById(Long userId) {
        return usersMapper.deleteById(userId) > 0;
    }

    @Override
    public boolean changePassword(Long userId, String oldPwd, String newPwd) {
        Users user = usersMapper.selectById(userId);
        if (user == null || !user.getPassword().equals(oldPwd)) {
            return false;
        }
        user.setPassword(newPwd);
        usersMapper.updateById(user);
        return true;
    }

}

