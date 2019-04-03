package com.oj.service.education;

import com.oj.entity.education.Class;

import java.util.List;
import java.util.Map;

public interface ClassService {
    // 获取班级列表
    public List<Map> getClassMapList(Map<String, String> param);
    //获取学院下拉信息
    public List<Map> getMajorSelectInfo();
    //获取年级下拉信息
    public List<Map> getGradeSelectInfo();
    //保存或更新班级
    public void saveOrUpdateClass(Class clas) throws Exception;
    //班级删除
    public void classDelete(String id);
    //获取学生列表
    public List<Map> getStudentMapByClassList(String id);
    /*
    public Map getCourseById(String id);
    public Map getCourseByName(String name);
    //课程删除
    public void courseDelete(String id);
    //保存课程绑定班级的信息
    public void saveCourseClassList(Map<String, Object> param);*/
}
