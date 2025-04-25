package com.mis.utils;

import com.mis.dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;

public class PermissionChecker {
    /**
     * 检查是否为管理员角色
     * @param request HttpServletRequest
     * @return 是否为管理员
     */
    public static boolean isAdmin(HttpServletRequest request) {
        // 从 session 中获取用户信息
        UserDTO userDTO = (UserDTO) request.getSession().getAttribute("user");
        // 打印日志查看 session 中的用户信息
        System.out.println("User from session: " + userDTO);
        // 如果存在且角色是 ADMIN，则返回 true
        return userDTO != null && "ADMIN".equals(userDTO.getRole());
    }

    /**
     * 检查是否为教师角色
     * @param request HttpServletRequest
     * @return 是否为教师
     */
    public static boolean isTeacher(HttpServletRequest request) {
        UserDTO userDTO = (UserDTO) request.getSession().getAttribute("user");
        return userDTO != null && "TEACHER".equals(userDTO.getRole());
    }

    /**
     * 检查是否为学生角色
     * @param request HttpServletRequest
     * @return 是否为学生
     */
    public static boolean isStudent(HttpServletRequest request) {
        UserDTO userDTO = (UserDTO) request.getSession().getAttribute("user");
        return userDTO != null && "STUDENT".equals(userDTO.getRole());
    }
}

