package com.mis.controller;

import com.mis.dto.*;
import com.mis.service.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Phantom
 * @since 2025-04-23
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    /**
     * 用户登录
     *
     * @param loginDTO 登录信息
     * @return 登录结果
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginDTO loginDTO, HttpServletRequest request) {
        return usersService.login(loginDTO, request);
    }

    /**
     * 用户注册
     *
     * @param registerDTO 注册信息
     * @return 注册结果
     */
    @PostMapping("/register")
    public Result register(@RequestBody RegisterDTO registerDTO) {
        return usersService.register(registerDTO);
    }

    /**
     * 获取当前用户信息
     *
     * @param request 请求对象
     * @return 当前用户信息
     */
    @GetMapping("/current")
    public ResponseEntity<UserDTO> getCurrentUser(HttpServletRequest request) {
        UserDTO user = (UserDTO) request.getSession().getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.ok(user);
    }

    /**
     * 注销当前用户
     *
     * @param request 请求对象
     * @return 注销结果
     */
    @DeleteMapping("/delete-current")
    public Result deleteCurrentUser(HttpServletRequest request) {
        UserDTO user = (UserDTO) request.getSession().getAttribute("user");
        if (user == null) {
            return Result.fail("未登录，无法注销");
        }

        boolean success = usersService.deleteUserById(user.getUserId());
        request.getSession().invalidate(); // 注销 session
        return success ? Result.ok("用户已注销") : Result.fail("注销失败");
    }

    /**
     * 修改密码
     *
     * @param dto 修改密码信息
     * @return 修改结果
     */
    @PostMapping("/change-password")
    public Result changePassword(@RequestBody PasswordDTO dto, HttpSession session) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            return Result.fail("未登录");
        }
        boolean success = usersService.changePassword(user.getUserId(), dto.getOldPassword(), dto.getNewPassword());
        return success ? Result.ok() : Result.fail("原密码错误");
    }

}
