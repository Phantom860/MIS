package com.mis.dto;

import lombok.Data;

import java.util.Map;

@Data
public class CourseScoreStats {
    private double average;  // 平均成绩
    private Map<String, Long> distribution;  // 成绩分布，成绩区间与人数的映射

    public CourseScoreStats(double average, Map<String, Long> distribution) {
        this.average = average;
        this.distribution = distribution;
    }
}
