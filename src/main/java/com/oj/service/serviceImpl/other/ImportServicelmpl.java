package com.oj.service.serviceImpl.other;

import com.oj.entity.education.Student;
import com.oj.service.education.StudentService;
import com.oj.service.other.ImportService;
import com.oj.service.serviceImpl.education.StudentServicelmpl;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/*
 * @author xielanning
 * @Time 2019年4月4日 10点41分
 * @Description 处理前端传来的excel文件
 */
public class ImportServicelmpl implements ImportService {

    private StudentService service = new StudentServicelmpl();

    public List getBankListByExcel(InputStream in, String fileName) throws Exception{
        List list = new ArrayList<>();
        Student student;
        String error;
        //创建Excel工作薄
        Workbook work = this.getWorkbook(in, fileName);
        if (null == work) {
            throw new Exception("创建Excel工作薄为空！");
        }
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;

        //------- !!!! 记得改过来
        for (int i = 1; i < work.getNumberOfSheets(); i++) {
            //遍历每一张表
            sheet = work.getSheetAt(i);
            if (sheet == null) {
                continue;
            }
            error=""+"表"+(i+1);
            for (int j = sheet.getFirstRowNum(); j <= sheet.getLastRowNum(); j++) {
                //遍历表里的每一行
                System.out.println("#j:"+j);
                row = sheet.getRow(j);
                if (row == null || row.getFirstCellNum() == j) {
                    continue;
                }
                error+="的第"+(j+1)+"行/";
                //List<Object> li = new ArrayList<>();

                if(row.getLastCellNum()!=3){
                    System.out.println("#:"+row.getLastCellNum());
                    throw new Exception(error+"数据不规范！中断操作，剩余数据未被读取！");
                }
                try {
                    DecimalFormat df = new DecimalFormat("0");
                    cell=row.getCell(0);
                    String whatYourWant = df.format(cell.getNumericCellValue());
                    System.out.println("#i:0/"+whatYourWant);
                    System.out.println("#i:1/"+row.getCell(1).toString());
                    System.out.println("#i:2/"+row.getCell(2).toString());
                    student = check(whatYourWant, row.getCell(1).toString(), row.getCell(2).toString());
                    //System.out.println("#:"+student);
                    service.addNewStudent(student);
                }catch (Exception e){
                    throw new Exception(error+e);
                }

                /*
                for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                    cell = row.getCell(y);
                    System.out.println(y+":"+cell);
                    li.add(cell);
                }*/
                //list.add(li);
            }
        }
        work.close();
        return list;
    }

    public Workbook getWorkbook(InputStream inStr, String fileName) throws Exception{
        Workbook workbook = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (".xls".equals(fileType)) {
            workbook = new HSSFWorkbook(inStr);
        } else if (".xlsx".equals(fileType)) {
            workbook = new XSSFWorkbook(inStr);
        } else {
            throw new Exception("请上传excel文件！");
        }
        return workbook;
    }

    //检查对应字符串是否符号规范，并返回一个student对象
    public Student checkB(String account, String name, String classId ) throws Exception{

        //Pattern p=Pattern.compile("^[0-9]*[1-9][0-9]*$");
        //Map<String, String> map=null;
        //Matcher m=p.matcher(bot);
        if(null==account || null==name ) throw new Exception("学号、姓名都不能为空");
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
