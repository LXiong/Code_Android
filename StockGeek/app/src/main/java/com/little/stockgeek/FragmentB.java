package com.little.stockgeek;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import cn.salesuite.saf.imagecache.ImageLoader;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentB extends Fragment {

    RadioButton btn1min,btn30min,btn1day,btn7day,btn30day;
    ImageView imageView;
    ImageLoader imageLoader;
    WebView webView;

    String url1min = "http://chart.finance.china.cn/stock/big/min/sz399001.gif";  //分时
    String url30min ="http://chart.finance.china.cn/stock/big/k30min/sz399001.gif";  //30min
    String urlDayK = "http://chart.finance.china.cn/stock/big/kline/sz399001.gif"; //
    String urlWeekK = "http://chart.finance.china.cn/stock/big/wkline/sz399001.gif"; //
    String urlMonthK = "http://chart.finance.china.cn/stock/big/mkline/sz399001.gif"; //



    public FragmentB() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a, container, false);
        init(view);
        return view;
    }

    public void init(View view) {
        btn1min = (RadioButton)view.findViewById(R.id.btn1min);
        btn30min = (RadioButton)view.findViewById(R.id.btn30min);
        btn1day = (RadioButton)view.findViewById(R.id.btn1day);
        btn7day = (RadioButton)view.findViewById(R.id.btn7day);
        btn30day = (RadioButton)view.findViewById(R.id.btn30day);

        webView = (WebView)view.findViewById(R.id.webView);
        webView.setBackgroundColor(0);


       /* imageView = (ImageView) view.findViewById(R.id.kkk);
        imageLoader = new ImageLoader(getActivity(),0);*/
        //a.displayImage(,img);
        //imageLoader.displayImage(,img);

        final String htmlHead = "<center><img style='width:100%;' src='";
        final String htmlFoot = "'></center>";
        btn1min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadDataWithBaseURL(null, htmlHead + url1min + htmlFoot, "text/html", "utf-8", null);
            }
        });

        btn30min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //imageLoader.displayImage(url30min,imageView);
                webView.loadDataWithBaseURL(null, htmlHead + url30min + htmlFoot, "text/html", "utf-8", null);
            }
        });

        btn1day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //imageLoader.displayImage(urlDayK,imageView);
                webView.loadDataWithBaseURL(null, htmlHead + urlDayK + htmlFoot, "text/html", "utf-8", null);
            }
        });

        btn7day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //imageLoader.displayImage(urlWeekK,imageView);
                webView.loadDataWithBaseURL(null, htmlHead + urlWeekK + htmlFoot, "text/html", "utf-8", null);
            }
        });


        btn30day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //imageLoader.displayImage(urlMonthK,imageView);
                webView.loadDataWithBaseURL(null, htmlHead + urlMonthK + htmlFoot, "text/html", "utf-8", null);
            }
        });


        btn1min.performClick();

    }


}
