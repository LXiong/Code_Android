package com.little.stockgeek;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.RequestMobileCodeCallback;


public class ActivityReg extends Activity {

    Button btnGetVerCode;
    EditText etPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_reg);
        findViews();

    }

    private void findViews() {
        btnGetVerCode = (Button)findViewById(R.id.btnGetVerCode);
        etPhone = (EditText)findViewById(R.id.etPhone);

        btnGetVerCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phoneNum = etPhone.getText().toString();
                Toast.makeText(getApplicationContext(),phoneNum,Toast.LENGTH_LONG).show();
                //如果你的账号需要重新发送短信请参考下面的代码
                AVUser.requestMobilePhoneVerifyInBackground(phoneNum, new RequestMobileCodeCallback() {

                    @Override
                    public void done(AVException e) {
                        //发送了验证码以后做点什么呢
                    }
                });
            }
        });
    }
}
