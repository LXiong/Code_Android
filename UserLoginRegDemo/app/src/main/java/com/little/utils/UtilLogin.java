package com.little.utils;

/**
 * Created by tusm on 15/9/4.
 */
public class UtilLogin {

    private static UtilLogin utilLogin;

    //1.私有化构造函数
    private UtilLogin(){}

    //2.获得实例函数
    public static UtilLogin getInstance(){
        if( utilLogin == null ) {
            utilLogin = new UtilLogin();
        }
        return utilLogin;
    }


    public boolean ifHasLogined() {  //判断当日用户是否已经登录
        return false;
    }


}
