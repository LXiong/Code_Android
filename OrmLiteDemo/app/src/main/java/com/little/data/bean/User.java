package com.little.data.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by tusm on 15/9/6.
 */

@DatabaseTable(tableName = "tb_user")
public class User {

    @DatabaseField(generatedId = true)
    Integer id;


    @DatabaseField(columnName = "userName")
    String userName;  //用户名

    @DatabaseField(columnName = "userAge")
    Integer userAge; //年龄

    @DatabaseField(columnName = "userGender")
    String userGender; //性别


    //空的构造函数
    public User() {

    }


    //
    public User(String userName, Integer userAge, String userGender) {
        this.userName = userName;
        this.userAge = userAge;
        this.userGender = userGender;
    }



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }



}
