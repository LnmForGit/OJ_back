package com.oj.entity;
/**
 * @author lixu
 * @Time 2019年3月9日 15点21分
 * @Description 对应数据库中User表实体类
 */
public class User {

    //用户id
    private int id;
    //用户姓名
    private String userName;
    //用户密码
    private String userPassword;
    //用户性别
    private int sex;
    //用户电话
    private String userPhone;
    //用户邮箱
    private String userMail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }
}
