package com.oj.service.serviceImpl.education;

import com.oj.entity.education.Class;
import com.oj.mapper.education.ClassMapper;
import com.oj.service.education.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author zt
 * @Time 2019年4月2日 17点52分
 * @Description 班级管理相关功能Service接口功能实现类
 */

@Service
public class ClassServiceImpl implements ClassService {
    @Autowired(required = false)
    private ClassMapper mapper;

    public List<Map> getClassMapList(Map<String, String> param)
    {
        return mapper.getClassMapList(param);
    }

    //获取学院下拉信息
    public List<Map> getMajorSelectInfo()
    {
        return mapper.getMajorSelectInfo();
    }

    //获取年级下拉信息
    public List<Map> getGradeSelectInfo()
    {
        return mapper.getGradeSelectInfo();
    }
    //保存或更新班级
    public void saveOrUpdateClass(Class clas) throws Exception
    {
        //若班级id为空，为保存
        if (StringUtils.isEmpty(clas.getId())){
            //若当前班级号已经存在，则抛出班级已存在的异常
            if(mapper.getClassByName(clas.getName()).size()>0){
                throw new Exception("当前班级已存在!");
            }else{
                mapper.save(clas);
            }
        }else{
            mapper.update(clas);
        }
    }

    @Transactional
    //班级删除
    public void classDelete(String id)
    {
        mapper.classDelete(id);
    }

    //@Override
    //获取学生列表
    public List<Map> getStudentMapByClassList(String id) {
        return mapper.getStudentMapByClassList(id);
    }
    /**
     * 通过id获取课程信息接口功能实现
     * @param id
     * @return
     */
    //@Override
    /*
    public Map getCourseById(String id) {
        return mapper.getCourseById(id).get(0);
    }
    public Map getCourseByName(String name)
    {
        return mapper.getCourseByName(name).get(0);
    }

    public void saveOrUpdateCourse(Course course) throws Exception
    {
        //若课程id为空，为保存
        if (StringUtils.isEmpty(course.getId())){
            //若当前课程号已经存在，则抛出课程已存在的异常
            if(mapper.getCourseByName(course.getName()).size()>0){
                throw new Exception("当前课程已存在!");
            }else{
                mapper.save(course);
            }
        }else{
            mapper.update(course);
        }
    }

    @Transactional
    //@Override
    public void courseDelete(String id) {
        //在删除课程之前应该先对其关联的班级信息进行解绑
        mapper.courseClassDelete(id);
        //解绑成功之后，再进行课程删除操作
        mapper.courseDelete(id);
    }


    //@Transactional
    //@Override
    public void saveCourseClassList(Map<String, Object> param)
    {
        String course_id = param.get("course_id").toString();
        List<String> class_list = (List<String>)param.get("class_list");
        //先解除课程的全部班级绑定
        mapper.courseClassDelete(course_id);
        //重新对用户的课程进行绑定
        for (String class_id : class_list){
            mapper.saveCourseClassList(course_id, class_id);
        }
    }*/
}
