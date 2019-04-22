package com.oj.service.other;

import com.oj.entity.other.MyFile;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface MyFileService {
    //获取教师下拉信息
    public List<Map> getAdminSelectInfo();
    // 获取文件列表
    public List<Map> getFileMapList(Map<String, String> param);
    //上传文件
    public void uploadMyFile(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws Exception;
    //删除文件
    public void fileDelete(HttpServletRequest request);
    //下载文件
    public void downloadFile(String id, HttpServletResponse response);
    //检查文件名字
    public void checkFileName(String name) throws Exception;
    //根据文件ID获取状态
    public List<Map> fileFlag(String id);
    //保存状态
    public void saveFileFlag(String id, String flag);
    /*
    //获取年级下拉信息
    public List<Map> getGradeSelectInfo();
    //班级删除
    public void classDelete(String id);
    //获取学生列表
    public List<Map> getStudentMapByClassList(String id);*/
}
