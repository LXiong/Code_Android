package com.little.lifecycletest;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class ActivityActLifecycle extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_lifecycle);

        Log.d("mytag","onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d("mytag", "onStart");
    }


    @Override
    protected void onStop() {
        super.onStop();

        Log.d("mytag", "onStop");

    }


    @Override
    protected void onPause() {
        super.onPause();

        Log.d("mytag", "onPause");

    }



    @Override
    protected void onResume() {
        super.onResume();

        Log.d("mytag", "onResume");
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("mytag", "onDestroy");
    }


    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {

        Log.d("mytag", "onCreateView");


        return super.onCreateView(parent, name, context, attrs);



    }
}
