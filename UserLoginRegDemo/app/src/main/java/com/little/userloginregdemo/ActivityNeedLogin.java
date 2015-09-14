package com.little.userloginregdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.little.template.Authenticity;
import com.little.utils.UtilLogin;


public class ActivityNeedLogin extends Activity implements Authenticity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkAuthenticity();
        setContentView(R.layout.activity_need_login);

    }


    @Override
    public boolean isNeedNet() {
        return false;
    }

    @Override
    public boolean isNeedLogin() {   //此activity需要登录
        return true;
    }

    @Override
    public boolean isNeedWifi() {
        return false;
    }

    @Override
    public void checkAuthenticity() {

        if( isNeedLogin() ) {
            if( UtilLogin.getInstance().ifHasLogined() == false ) {
                new AlertDialog.Builder(this).setTitle("提示").setMessage("是否立即登录")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getApplicationContext(),ActivityLogin.class);
                                startActivity(intent);  //此处应该用 startActivityForResult
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setCancelable(false)
                        .show();
            }

        }


    }
}
