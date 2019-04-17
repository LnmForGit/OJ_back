package com.oj.service.serviceImpl.other;

import com.oj.entity.other.MyFile;
import com.oj.mapper.other.MyFileMapper;
import com.oj.service.other.MyFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.FileCopyUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.Date;

/*
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
*/

@Service
public class MyFileServicelmpl implements MyFileService {
    @Autowired(required = false)
    private MyFileMapper mapper;

    public List<Map> getAdminSelectInfo()
    {
        return mapper.getAdminSelectInfo();
    }

    public List<Map> getFileMapList(Map<String, String> param)
    {
        return mapper.getFileMapList(param);
    }


    //检查文件名字
    public void checkFileName(String name) throws Exception
    {
        if(mapper.getFileByName(name).size()>0){
            throw new Exception("当前文件名已存在!");
        }
    }

    //上传文件
    public void uploadMyFile(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws Exception
    {
        //若当前文件名已经存在，则抛出文件已存在的异常
        if(mapper.getFileByName(file.getOriginalFilename()).size()>0){
            throw new Exception("当前文件名已存在!");
        }else{
            InputStream in = null;
            OutputStream out = null;
            try
            {
                in = file.getInputStream();
                String id = request.getSession().getAttribute("user_id").toString();

                //判断是否有该用户的文件目录，无则创建以此来让文件能够顺利存储到该用户目录下
                String rootPath = "E:/学习笔记/OJ平台重构/testUpload/";
                String []list = new File(rootPath).list();
                int pathLength = list.length;
                boolean createFlag = false;
                for(int i = 0; i < pathLength; i++)
                {
                    if(list[i] == id)
                    {
                        createFlag = true;
                    }
                }
                if(!createFlag)
                {
                    String newRootPath = rootPath+id;
                    System.out.println(newRootPath);
                    File createPath = new File(newRootPath);
                    createPath.mkdirs();
                }

                String route = "E:/学习笔记/OJ平台重构/testUpload/"+id+"/"+file.getOriginalFilename();
                System.out.println(route);
                File targetfile = new File(route);
                out = new FileOutputStream(targetfile);
                FileCopyUtils.copy(in, out);
                MyFile M = new MyFile();
                M.setName(file.getOriginalFilename());
                M.setUpload_time(new Date().getTime()/1000);
                M.setUploader_id(id);
                M.setRoute(route);
                M.setSize(file.getSize());
                System.out.println(M);
                mapper.save(M);
            }
            catch(IOException e)
            {
                e.printStackTrace();
                throw new Exception("文件上传失败!");
            }
            finally
            {
                if(in != null)
                {
                    try
                    {
                        in.close();
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                if(out != null)
                {
                    try
                    {
                        out.close();
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void downloadFile(String id, HttpServletResponse response)
    {
        String path = mapper.getPathById(id);
        //System.out.println(path);
        String fileName = mapper.getFileNameById(id);
        File file = new File(path);
        if (file.exists()) {
            //System.out.println("File path is : " + path);
            response.setHeader("content-type", "application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            //response.setHeader("Pragma", "No-cache");
            //response.setHeader("Cache-Control", "No-cache");
            //response.setDateHeader("Expires", 0);
            byte[] buff = new byte[1024];
            BufferedInputStream bis = null;
            FileInputStream fis = null;
            try {
                OutputStream os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buff);
                while (i != -1) {
                    os.write(buff, 0, buff.length);
                    //os.flush();
                    i = bis.read(buff);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void fileDelete(HttpServletRequest request)
    {
        String userId = request.getSession().getAttribute("user_id").toString();
        String id = request.getParameter("id");
        String fileName = mapper.getFileNameById(id);
        File filePath = new File("E:/学习笔记/OJ平台重构/testUpload/" + userId + "/" + fileName);
        filePath.delete();
        mapper.fileDelete(id);
    }

}


/*
 * @author xielanning
 * @Time 2019年4月4日 10点41分
 * @Description 处理前端传来的excel文件
 */
/*
public class MyFileServicelmpl implements ImportService {

    private StudentService service = new StudentServicelmpl();



    //检查对应字符串是否符号规范，并返回一个student对象
    public Student checkB(String account, String name, String classId ) throws Exception{

        //Pattern p=Pattern.compile("^[0-9]*[1-9][0-9]*$");
        //Map<String, String> map=null;
        //Matcher m=p.matcher(bot);
        if(null==account || null==name || account.equals("") || name.equals("")) throw new Exception("学号、姓名都不能为空");
        if( account.getBytes().length>64) throw new Exception("学号不符合不能超过64位");
        if(name.getBytes().length>50) throw new Exception("姓名长度不能超过50位");
        return new Student(account, " ", name, classId);

    }
    public Student check(String account, String name, String className ) throws Exception{
        //暂时未用到的函数
        Pattern p=Pattern.compile("^[0-9]*[1-9][0-9]*$");
        //Map<String, String> map=null;
        //Matcher m=p.matcher(bot);
        if(null==account || null==name || null==className) throw new Exception("学号、姓名和班级名称都不能为空");
        System.out.println("#length:"+account.getBytes().length);
        if( account.getBytes().length>64) throw new Exception("学号不符合不能超过64位");
        if(name.getBytes().length>50) throw new Exception("姓名长度不能超过50位");
        if(className.getBytes().length>40) throw new Exception("班级长度不能超过40位");
        System.out.println("#flag2");
        if(null==service)
            System.out.println("#service is null");
        className = service.getTheClassIdByName(className); //这里有问题！！！！
        System.out.println("#flag1");
        if(null==className) throw new Exception("未找到对应名称的班级");
        System.out.println("#flag");
        return new Student(account, " ", name, className);

    }
}
*/