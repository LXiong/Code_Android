package com.little.stockgeek;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAbout extends Fragment {

    View mainView;

    public FragmentAbout() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        ActivityMain.toolbar.setTitle("关于");


        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_about, container, false);

        Button btnComment = (Button)mainView.findViewById(R.id.btnComment);
        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToLofter();
            }
        });
        return mainView;
    }


    public void jumpToLofter(){
        Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse("http://taojar.lofter.com/post/477f1e_7e124bf"));
        it.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
        getActivity().startActivity(it);
    }

}
