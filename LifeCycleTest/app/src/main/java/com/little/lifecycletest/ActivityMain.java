package com.little.lifecycletest;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class ActivityMain extends Activity {


    Button btnActivityLifeCycle,btnFragmentLifeCycle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnActivityLifeCycle = (Button)this.findViewById(R.id.btnActivityLifeCycle);
        btnFragmentLifeCycle = (Button)this.findViewById(R.id.btnFragmentLifeCycle);

        btnActivityLifeCycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityMain.this,ActivityActLifecycle.class);
                startActivity(intent);
            }
        });

    }

}
