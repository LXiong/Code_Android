package com.little.testservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ActivityMain extends ActionBarActivity {

    Button btn1, btn2, btn3, btn4;
    static TextView tv;
    Intent intentOne, intentTwo;
    ServiceConnection serviceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViews();
    }


    public void setupViews() {


        btn1 = (Button) this.findViewById(R.id.button1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLocalServiceOneByStart();
            }
        });

        btn2 = (Button) this.findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLocalServiceOneByBind();
            }
        });

        btn3 = (Button) this.findViewById(R.id.button3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopOne();
            }
        });

        btn4 = (Button) this.findViewById(R.id.button4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTwo();
            }
        });

        tv = (TextView) this.findViewById(R.id.textView);
        tv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                tv.setText("");
                return true;
            }
        });

    }


    //使用startService的方式启动
    public void startLocalServiceOneByStart() {
        //
        intentOne = new Intent(this, LocalServiceOne.class);
        startService(intentOne);
    }


    //
    public void stopOne() {

        stopService(intentOne);

    }


    //使用bindService的方式启动
    public void startLocalServiceOneByBind() {
        intentTwo = new Intent(this, LocalServiceOne.class);

        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                tv.append("\r\nonServiceConnected");
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                tv.append("\r\nonServiceDisconnected");
            }
        };


        bindService(intentTwo, serviceConnection, Context.BIND_AUTO_CREATE);
        //bind了以后记得启动
        startService(intentTwo);

    }


    public void stopTwo() {


        unbindService(serviceConnection);
        stopService(intentTwo); //unbind以后记得关闭
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
