package com.little.viewpageractionbartoolbardemo;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;


public class ActivityMain extends ActionBarActivity {

    //1.添加android的appcompat v7的库
    //2.写好toolbar.xml
    //3.在activityMain布局里include这个toolbar.xml
    //4.初始化 slidingmenu，按钮等view


    RadioButton btn1,btn2,btn3;
    RadioButton btn4;
    Toolbar toolbar;
    ViewPager viewPager;
    LocalActivityManager localActivityManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        localActivityManager = new LocalActivityManager(this,true);
        localActivityManager.dispatchCreate(savedInstanceState);


        initToolBar();
        initHeaderButtons();
        initSlidingMenu();
        initViewPager();




    }



    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");

        //toolbar.setLogo(R.drawable.ic_launcher);
        //toolbar.setTitle("Rocko");// 标题的文字需在setSupportActionBar之前，不然会无效
        //toolbar.setSubtitle("副标题");
        setSupportActionBar(toolbar);
        /* 这些通过ActionBar来设置也是一样的，注意要在setSupportActionBar(toolbaÏr);之后，不然就报错了 */
        // getSupportActionBar().setTitle("标题");
        // getSupportActionBar().setSubtitle("副标题");
        // getSupportActionBar().setLogo(R.drawable.ic_launcher);


        /* 菜单的监听可以在toolbar里设置，也可以像ActionBar那样，通过Activity的onOptionsItemSelected回调方法来处理 */
        /*
        //不需要menu
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_settings:
                        Toast.makeText(ActivityMain.this, "action_settings", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.action_share:
                        Toast.makeText(ActivityMain.this, "action_share", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        */
    }

    private void initHeaderButtons(){

        Typeface font = Typeface.createFromAsset(this.getAssets(), "fontawesome-webfont.ttf");

        btn1 = (RadioButton)this.findViewById(R.id.btn1);
        btn2 = (RadioButton)this.findViewById(R.id.btn2);
        btn3 = (RadioButton)this.findViewById(R.id.btn3);
        btn4 = (RadioButton)this.findViewById(R.id.btn4);
        btn1.setTypeface(font); btn1.setText("\uF015");
        btn2.setTypeface(font); btn2.setText("\uF201");
        btn3.setTypeface(font); btn3.setText("\uF00B");
        btn4.setTypeface(font); btn4.setText("\uF007");

    }


    private void initSlidingMenu(){
        // configure the SlidingMenu
        final SlidingMenu menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.RIGHT);

        // 设置触摸屏幕的模式
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);

        // 设置滑动菜单视图的宽度
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        // 设置渐入渐出效果的值
        menu.setFadeDegree(0.35f);
        /**
         * SLIDING_WINDOW will include the Title/ActionBar in the content
         * section of the SlidingMenu, while SLIDING_CONTENT does not.
         */
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        //为侧滑菜单设置布局
        menu.setMenu(R.layout.rightmenu);


        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.showMenu(true);
            }
        });

    /*    menu.setOnOpenListener(new SlidingMenu.OnOpenListener() {
            @Override
            public void onOpen() {
                btn4.setChecked(true);
            }
        });*/

        menu.setOnCloseListener(new SlidingMenu.OnCloseListener() {
            @Override
            public void onClose() {
                btn4.setChecked(false);
            }
        });


        menu.setOnOpenedListener(new SlidingMenu.OnOpenedListener() {
            @Override
            public void onOpened() {
                btn4.setChecked(true);
            }
        });
    }

    private void initViewPager() {

        viewPager = (ViewPager)this.findViewById(R.id.viewPager);
        ArrayList<View> listPageView = new ArrayList<>();

        View viewActOne   =  localActivityManager.startActivity("ActivityOne",   new Intent(this,ActivityOne.class)   ).getDecorView();
        View viewActTwo   =  localActivityManager.startActivity("ActivityTwo",   new Intent(this,ActivityTwo.class)   ).getDecorView();
        View viewActThree =  localActivityManager.startActivity("ActivityThree", new Intent(this,ActivityThree.class) ).getDecorView();

        listPageView.add( viewActOne );
        listPageView.add( viewActTwo );
        listPageView.add( viewActThree );

        AdapterViewPager adapterViewPager = new AdapterViewPager(listPageView);
        viewPager.setAdapter(adapterViewPager);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    btn1.setChecked(true);
                } else if (position == 1) {
                    btn2.setChecked(true);
                } else if (position == 2) {
                    btn3.setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0, true);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1,true);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2,true);
            }
        });

    }



    class AdapterViewPager extends PagerAdapter {

        ArrayList<View> listPageView;

        public AdapterViewPager(ArrayList<View> listPageView) {
            this.listPageView = listPageView;
        }


        @Override
        public int getCount() {
            return listPageView.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            ViewPager viewPager = (ViewPager)container;
            viewPager.addView( listPageView.get(position) );
            return listPageView.get(position);

        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            ViewPager viewPager = (ViewPager)container;
            viewPager.removeView(listPageView.get(position));

        }



    }

    /*
    //不需要menu
    @Override
    public boolean onCreateOptionsMenu(Menu rightmenu) {
        // Inflate the rightmenu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.rightmenu.menu_activity_main, rightmenu);
        return true;
    }*/

}
