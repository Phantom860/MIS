package com.mis.entity;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum Role implements IEnum<String> {
    STUDENT,
    TEACHER,
    ADMIN;

    @Override
    public String getValue() {
        return this.name(); // 或根据需求返回自定义值
    }
}
