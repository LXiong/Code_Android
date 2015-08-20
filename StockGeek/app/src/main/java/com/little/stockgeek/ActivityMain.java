package com.little.stockgeek;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SignUpCallback;
import com.little.stockgeek.bean.TradeHome;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;

import java.util.ArrayList;
import java.util.List;

import cn.salesuite.saf.app.SAFAdapter;
import cn.salesuite.saf.app.SAFApp;


public class ActivityMain extends ActionBarActivity {
    //声明相关变量
    public static Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView lvLeftMenu;
    private adapterForDrawerListview adapterForDrawerListview;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UmengUpdateAgent.update(this);


        AVUser user = new AVUser();
        user.setUsername(SAFApp.getInstance().deviceid);
        user.setPassword("default");
        user.signUpInBackground(new SignUpCallback() {
            public void done(AVException e) {
                if (e == null) {
                    // successfully
                    //Log.d("mytag","success");
                } else {
                    //Log.d("mytag","fail");
                }
            }
        });

        //各种findviews
        toolbar = (Toolbar) findViewById(R.id.tl_custom);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.mainDrawer);
        lvLeftMenu = (ListView) findViewById(R.id.lv_left_menu);

        //初始化左侧抽屉列表
        ArrayList<MyListViewItem> listViewItems = new ArrayList<>();
        listViewItems.add(new MyListViewItem("home","首页","\uF015"));
        listViewItems.add(new MyListViewItem("summary","行情概览","\uF201"));
        listViewItems.add(new MyListViewItem("block","板块行情","\uF00b"));
        listViewItems.add(new MyListViewItem("mine","自选股","\uF21e"));
        listViewItems.add(new MyListViewItem("moni","模拟炒股","\uF157"));
        listViewItems.add(new MyListViewItem("about","关于","\uF05a"));
        listViewItems.add(new MyListViewItem("exit","退出应用","\uF18e"));
        adapterForDrawerListview = new adapterForDrawerListview();
        adapterForDrawerListview.setData(listViewItems);
        lvLeftMenu.setAdapter(adapterForDrawerListview);
        lvLeftMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mDrawerLayout.closeDrawers();
                jumpToFragment(position);
            }
        });




        FragmentMain fragmentMain = new FragmentMain();
        this.getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer,fragmentMain).commit();


        toolbar.setTitle("模拟炒股CC");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff")); //设置标题颜色
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //创建返回键，并实现打开关/闭监听
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);



        //Intent intentToReg = new Intent(ActivityMain.this,ActivityReg.class );
        //startActivity(intentToReg);

        UmengUpdateAgent.update(this);

    }


    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }


    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


    public void jumpToFragment(int index) {

        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(index==0){
            FragmentMain fragmentMain = new FragmentMain();
            fragmentTransaction.replace(R.id.fragmentContainer, fragmentMain);
        }
        else if(index==1) {
            FragmentOne fragmentOne = new FragmentOne();
            fragmentTransaction.replace(R.id.fragmentContainer, fragmentOne);
        }else if(index==2) {
            FragmentTwo fragmentTwo = new FragmentTwo();
            fragmentTransaction.replace(R.id.fragmentContainer, fragmentTwo);
        }else if(index==3) {
            FragmentThree fragmentThree = new FragmentThree();
            fragmentTransaction.replace(R.id.fragmentContainer, fragmentThree);
        }else if(index==4) {
            FragmentFour fragmentFour = new FragmentFour();
            fragmentTransaction.replace(R.id.fragmentContainer, fragmentFour);
        } else if(index==5) {
            FragmentAbout fragmentAbout = new FragmentAbout();
            fragmentTransaction.replace(R.id.fragmentContainer, fragmentAbout);
        } else if(index==6) {
            android.os.Process.killProcess(android.os.Process.myPid());    //获取PID
            System.exit(0);
        }
        fragmentTransaction.commit();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            jumpToFragment(0);
        }
        return false;
    }




    class MyListViewItem {
        String name;
        String iconText;
        String titleText;

        MyListViewItem(String name,String titleText,String iconText) {
            this.name = name;
            this.iconText = iconText;
            this.titleText = titleText;
        }
    }



    class adapterForDrawerListview extends SAFAdapter<MyListViewItem> {

        Typeface font = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");

        List<MyListViewItem> list;

        @Override
        public void setData(List<MyListViewItem> list) {
            super.setData(list);
            this.list = list;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.lv_item_for_drawer,null);
            TextView iconTv = (TextView)view.findViewById(R.id.itemIconText);
            TextView titleTv = (TextView)view.findViewById(R.id.itemTitleText);
            iconTv.setText(this.list.get(position).iconText);
            iconTv.setTypeface(font);
            titleTv.setText(this.list.get(position).titleText);

            return view;
        }


        @Override
        public int getCount() {
            return list.size();
        }
    }
}