package com.oj.controller.education;

import com.oj.entity.education.Class;
import com.oj.service.education.ClassService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zt
 * @Time 2019年4月2日 17点31分
 * @Description 班级管理控制类
 */
@Controller
@RequestMapping("/classMn")
public class ClassController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ClassService classService;

    //返回班级管理页面
    @RequestMapping("/")
    public String index(ModelMap modelMap, HttpServletRequest request)
    {
        return "education/class";
    }

    @PostMapping("/getClassMapList")
    @ResponseBody
    public List<Map> getClassMapList(@RequestBody Map<String, String> param, HttpServletRequest request)
    {
        List<Map> list = classService.getClassMapList(param);
        return list;
    }

    @PostMapping("/getMajorSelectInfo")
    @ResponseBody
    //获取学院下拉信息
    public List<Map> getMajorSelectInfo(){
        List<Map> list = classService.getMajorSelectInfo();
        return list;
    }

    @PostMapping("/getGradeSelectInfo")
    @ResponseBody
    //获取年级下拉信息
    public List<Map> getGradeSelectInfo()
    {
        List<Map> List = classService.getGradeSelectInfo();
        return List;
    }

    @PostMapping("/saveOrUpdateClass")
    @ResponseBody
    public Map<String, String> saveOrUpdateClass(@RequestBody Class clas)
    {
        Map<String, String> map = new HashMap<>();
        try {
            classService.saveOrUpdateClass(clas);
            map.put("flag", "1");
            return map;
        } catch (Exception e){
            map.put("flag", "0");
            map.put("message", e.getMessage());
            log.error(e.getMessage());
            return map;
        }
    }

    @PostMapping("/classDelete")
    @ResponseBody
    public Map<String, String> classDelete(HttpServletRequest request){
        Map<String, String> map = new HashMap<>();
        try {
            classService.classDelete(request.getParameter("id"));
            map.put("flag", "1");
            return map;
        }catch (Exception e){
            map.put("flag", "0");
            log.error(e.getMessage());
            return map;
        }
    }

    @PostMapping("/getStudentMapByClassList")
    @ResponseBody
    public List<Map> getStudentMapByClassList(HttpServletRequest request)
    {
        return classService.getStudentMapByClassList(request.getParameter("id"));
    }
    /*
    //通过课程ID查找课程信息接口
    @PostMapping("/getCourseById")
    @ResponseBody
    public Map getCourseById(HttpServletRequest request){
        return classService.getCourseById(request.getParameter("id").toString());
    }
    //通过课程名查找课程信息接口
    @PostMapping("/getCourseByName")
    @ResponseBody
    public Map getCourseByName(HttpServletRequest request){
        return courseService.getCourseByName(request.getParameter("name").toString());
    }


    @PostMapping("/saveOrUpdateCourse")
    @ResponseBody
    public Map<String, String> saveOrUpdateCourse(@RequestBody Course course)
    {
        Map<String, String> map = new HashMap<>();
        try {
            courseService.saveOrUpdateCourse(course);
            map.put("flag", "1");
            return map;
        } catch (Exception e){
            map.put("flag", "0");
            map.put("message", e.getMessage());
            log.error(e.getMessage());
            return map;
        }
    }
    @PostMapping("/courseDelete")
    @ResponseBody
    public Map<String, String> courseDelete(HttpServletRequest request){
        Map<String, String> map = new HashMap<>();
        try {
            courseService.courseDelete(request.getParameter("id"));
            map.put("flag", "1");
            return map;
        }catch (Exception e){
            map.put("flag", "0");
            log.error(e.getMessage());
            return map;
        }
    }


    @PostMapping("/saveCourseClassList")
    @ResponseBody
    public Map<String, String> saveCourseClassList(@RequestBody Map<String, Object> param){
        Map<String, String> map = new HashMap<>();
        try {
            courseService.saveCourseClassList(param);
            map.put("flag", "1");
            return map;
        }catch (Exception e){
            map.put("flag", "0");
            log.error(e.getMessage());
            return map;
        }
    }*/

}
