package com.oj.service.serviceImpl.education;

import com.oj.entity.education.Student;
import com.oj.entity.other.BulkAddStudentPackage;
import com.oj.entity.other.NewStu;
import com.oj.frameUtil.OJPWD;
import com.oj.mapper.education.StudentMapper;
import com.oj.service.education.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/*
 * @author xielanning
 * @Time 2019年4月4日 10点41分
 * @Description 学生管理相关功能的service接口实现
 */
@Service
public class StudentServicelmpl implements StudentService {
    @Autowired(required = false)
    private StudentMapper mapper;

    @Override
    public List<Map> getTargetStudentList(Map<String, String> param){
        List<Map> result = null;
        result = mapper.getTargetStudent(param);
        return result;
    }
    @Override
    public Map<String, String> getTheStudentById(String id){
        return mapper.getTheStudentById(id);
    }
    @Override
    public List<Map> getClassList(){
        return mapper.getClassList();
    }

    @Override
    public int addNewStudent(Student student) throws Exception {

        Map<String, String> map = mapper.getTheStudentByAccount(student.getAccount());
        if(null!=map && map.size()>0)
            throw new Exception("学号"+student.getAccount()+"已存在");
        return mapper.addNewStudent(student);
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public int addMoreNewStudent(BulkAddStudentPackage basp) throws Exception{

        //多low哦
        for(NewStu ns : basp.getData()){
            try {
                addNewStudent(new Student(ns.getAccount(), " ", ns.getName(), basp.getClassId()));
            }catch(Exception e){
                throw new Exception(e.getMessage());
            }
        }
        return 0;
    }

    @Override
    public void deleteTheStudentById(String id){
         mapper.deleteTheStudentById(id);
    }
    @Override
    public void deleteStudentByClassId(String classId){
        mapper.deleteStudentByClassId(classId);
    }

    @Override
    public int updateTheStudentById(String id, Student student) throws Exception{
        //Map 类型下， Mapper返回的数据类型与数据库表内属性类型有关！map.get("id")返回的是int型数据
        Map map = mapper.getTheStudentByAccount(student.getAccount());
        if(null!=map && map.size()>0 && !((id.equals(""+map.get("id")) )) ) {
            throw new Exception("该学号已存在");
            //return -1;
        }
        student.setId(id);
        return mapper.updateTheStudent(student);
    }

    @Override
    public void updateTheStudentPWById(String id, String pw){

        System.out.println(pw);
        pw = OJPWD.OJPWDTOMD5(pw);
        System.out.println(pw);
        mapper.updateTheStudentPWById( id, pw);
    }

    @Override
    public String getTheClassIdByName(String className){
        System.out.println("#getTheClassIdByName/1");
        String result=null;
        Map map = mapper.getTheClassIdByName(className);
        System.out.println("#getTheClassIdByName/2");
        if(null==map)
            System.out.println("map is null");
        result=""+map.get("id");
        System.out.println("#:"+result);
        return result;
    }
}
