package com.little.userloginregdemo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.little.utils.UtilLogin;


public class ActivityMain extends Activity {

    Button btnNeedLogin,btnDontNeedLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNeedLogin = (Button)this.findViewById(R.id.btnNeedLogin);
        btnDontNeedLogin = (Button)this.findViewById(R.id.btnDontNeedLogin);

        btnNeedLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ActivityNeedLogin.class);
                startActivity(intent);
            }
        });

        btnDontNeedLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ActivityDontNeedLogin.class);
                startActivity(intent);
            }
        });







    }

}
