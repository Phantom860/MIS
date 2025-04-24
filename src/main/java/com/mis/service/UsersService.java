package com.mis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mis.dto.LoginDTO;
import com.mis.dto.Result;
import com.mis.entity.Users;

public interface UsersService extends IService<Users> {

    Result login(LoginDTO loginDTO);
}
