package com.mis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mis.dto.CourseChoosingDTO;
import com.mis.dto.CourseScoreStats;
import com.mis.dto.Result;
import com.mis.entity.CourseChoosing;
import com.mis.entity.Courses;
import com.mis.entity.Students;
import com.mis.mapper.CourseChoosingMapper;
import com.mis.mapper.CoursesMapper;
import com.mis.mapper.StudentsMapper;
import com.mis.service.CourseChoosingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CourseChoosingServiceImpl extends ServiceImpl<CourseChoosingMapper, CourseChoosing> implements CourseChoosingService {
    @Autowired
    private StudentsMapper studentsMapper;

    @Autowired
    private CoursesMapper coursesMapper;

    @Autowired
    private CourseChoosingMapper courseChoosingMapper;

    /**
     * 添加选课记录
     *
     * @param courseChoosing 选课记录对象
     * @return 添加结果
     */
    @Override
    public Result addCourseChoosing(CourseChoosing courseChoosing) {
        // 1. 校验学生ID
        if (courseChoosing.getStudentId() == null) {
            return Result.fail("学生ID不能为空");
        }
        Students student = studentsMapper.selectById(courseChoosing.getStudentId());
        if (student == null) {
            return Result.fail("学生不存在，无法选课");
        }

        // 2. 校验课程ID
        if (courseChoosing.getCourseId() == null) {
            return Result.fail("课程ID不能为空");
        }
        Courses course = coursesMapper.selectById(courseChoosing.getCourseId());
        if (course == null) {
            return Result.fail("课程不存在，无法选课");
        }

        // 3. 校验选课年份
        if (courseChoosing.getChosenYear() == null) {
            return Result.fail("选课年份不能为空");
        }
        Integer canceledYear = course.getCanceledYear();
        if (canceledYear != null && courseChoosing.getChosenYear() >= canceledYear) {
            return Result.fail("选课年份必须早于课程取消年份");
        }

        // 4. 校验学生年级
        if (student.getEntranceYear() == null) {
            return Result.fail("学生入学年份不能为空");
        }
        int studentGrade = courseChoosing.getChosenYear() - student.getEntranceYear() + 1;
        if (studentGrade < course.getGrade()) {
            return Result.fail("学生年级不足，无法选修该课程");
        }

        // 5. 校验教师ID
        if (courseChoosing.getTeacherId() == null) {
            return Result.fail("教师ID不能为空");
        }
        if (!courseChoosing.getTeacherId().equals(course.getTeacherId())) {
            return Result.fail("教师ID与该课程的授课教师不匹配");
        }

        // 6. 插入选课信息（只插入一次）
        try {
            int rows = courseChoosingMapper.insert(courseChoosing);
            if (rows > 0) {
                return Result.ok("选课成功");
            } else {
                return Result.fail("选课失败，数据库未插入记录");
            }
        } catch (DuplicateKeyException e) {
            return Result.fail("选课失败：该学生已选择过该课程");
        } catch (Exception e) {
            return Result.fail("选课失败：" + e.getMessage());
        }
    }


    /**
     * 删除选课记录
     *
     * @param studentId     学生ID
     * @param courseId      课程ID
     * @param teacherId     教师ID
     * @return 删除结果
     */
    @Override
    public boolean deleteCourseChoosing(Long studentId, Long courseId, Long teacherId) {
        QueryWrapper<CourseChoosing> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id", studentId)
                .eq("course_id", courseId)
                .eq("teacher_id", teacherId);
        int rows = this.baseMapper.delete(queryWrapper);
        return rows > 0;
    }

    @Override
    public List<CourseChoosingDTO> queryCourseChoosing(Long studentId, String studentName, Long courseId, String courseName) {
        return courseChoosingMapper.queryCourseChoosing(studentId, studentName, courseId, courseName);
    }

    @Override
    public boolean updateCourseChoosing(CourseChoosing courseChoosing, String role) {
        // 先查出数据库原始记录
        CourseChoosing existing = courseChoosingMapper.selectOne(new QueryWrapper<CourseChoosing>()
                .eq("student_id", courseChoosing.getStudentId())
                .eq("course_id", courseChoosing.getCourseId())
                .eq("teacher_id", courseChoosing.getTeacherId()));

        if (existing == null) {
            return false; // 没找到
        }

        if ("ADMIN".equals(role)) {
            // 管理员不能改成绩
            courseChoosing.setScore(existing.getScore());
        } else if ("TEACHER".equals(role)) {
            // 教师只能改成绩，其他字段不能改
            courseChoosing.setChosenYear(existing.getChosenYear());
            courseChoosing.setStudentId(existing.getStudentId());
            courseChoosing.setCourseId(existing.getCourseId());
            courseChoosing.setTeacherId(existing.getTeacherId());
        } else {
            return false; // 没权限
        }

        // 调用自定义更新
        return courseChoosingMapper.updateCourseChoosing(courseChoosing) > 0;
    }

    /**
     * 根据课程ID获取课程成绩统计信息
     *
     * @param courseId 课程ID
     * @return 课程成绩统计信息
     */
    @Override
    public CourseScoreStats getCourseScoreStats(Long courseId) {
        // 查询该课程的所有成绩
        List<CourseChoosing> courseChoosingList = courseChoosingMapper.selectList(
                new QueryWrapper<CourseChoosing>().eq("course_id", courseId)
        );

        if (courseChoosingList.isEmpty()) {
            return new CourseScoreStats(0, new HashMap<>());  // 如果没有数据，返回默认值
        }

        // 过滤掉 null 成绩
        List<CourseChoosing> validList = courseChoosingList.stream()
                .filter(c -> c.getScore() != null)
                .collect(Collectors.toList());

        if (validList.isEmpty()) {
            return new CourseScoreStats(0, new HashMap<>()); // 没有有效成绩也返回默认值
        }

        // 计算平均成绩
        double average = validList.stream()
                .mapToDouble(CourseChoosing::getScore)
                .average()
                .orElse(0.0);

        // 计算成绩分布
        Map<String, Long> distribution = calculateScoreDistribution(validList);

        // 返回 CourseScoreStats 对象
        return new CourseScoreStats(average, distribution);
    }


    /**
     * 计算成绩分布
     *
     * @param courseChoosingList 学生成绩列表
     * @return 成绩分布
     */
    private Map<String, Long> calculateScoreDistribution(List<CourseChoosing> courseChoosingList) {
        Map<String, Long> distribution = new HashMap<>();

        // 设定成绩区间
        String[] scoreRanges = {"0-60", "60-70", "70-80", "80-90", "90-100"};

        // 将学生成绩按照区间分组
        for (CourseChoosing courseChoosing : courseChoosingList) {
            double score = courseChoosing.getScore();

            String range = getScoreRange(score, scoreRanges);
            distribution.put(range, distribution.getOrDefault(range, 0L) + 1);
        }

        return distribution;
    }

    /**
     * 获取成绩区间
     *
     * @param score       成绩
     * @param scoreRanges 成绩区间
     * @return 成绩区间
     */
    private String getScoreRange(double score, String[] scoreRanges) {
        for (int i = 0; i < scoreRanges.length; i++) {
            String range = scoreRanges[i];
            String[] rangeBounds = range.split("-");
            double lower = Double.parseDouble(rangeBounds[0]);
            double upper = Double.parseDouble(rangeBounds[1]);

            // 对最后一个区间使用 <=，其余的用 < 上限
            if (i == scoreRanges.length - 1) {
                if (score >= lower && score <= upper) {
                    return range;
                }
            } else {
                if (score >= lower && score < upper) {
                    return range;
                }
            }
        }
        return "未知区间";  // 如果成绩不在任何预设区间内，返回未知区间
    }

}
