package com.little.stockgeek;

import android.app.AlertDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.little.stockgeek.bean.MyStock;
import com.little.stockgeek.bean.SimpleStock;
import com.little.stockgeek.bean.TradeStock;
import com.little.stockgeek.bean.TradeStockRecord;
import com.little.stockgeek.service.ServiceMyStock;
import com.little.stockgeek.service.ServiceRealtimeStock;
import com.little.stockgeek.service.ServiceTradeStock;

import cn.salesuite.saf.app.SAFApp;


public class ActivityStockChart extends ActionBarActivity {

    RadioButton btn1min,btn30min,btn1day,btn7day,btn30day;
    Button btnAddMyStock,btnMoniBuy,btnMoniSell;
    WebView webView;

    String shortCode = "";
    String fullCode = "";
    String name = "";
    String url1min = "http://chart.finance.china.cn/stock/big/min/";//sh000001.gif";  //分时
    String url30min ="http://chart.finance.china.cn/stock/big/k30min/";//sh000001.gif";  //30min
    String urlDayK = "http://chart.finance.china.cn/stock/big/kline/";//sh000001.gif"; //
    String urlWeekK = "http://chart.finance.china.cn/stock/big/wkline/";//sh000001.gif"; //
    String urlMonthK = "http://chart.finance.china.cn/stock/big/mkline/";//sh000001.gif"; //

    View mainView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainView = LayoutInflater.from(this).inflate(R.layout.activity_stock_chart,null);
        setContentView(mainView);
        this.shortCode = this.getIntent().getExtras().getString("code");
        this.fullCode = this.getIntent().getExtras().getString("fullCode");
        this.name = this.getIntent().getExtras().getString("name");

        this.url1min += fullCode + ".gif";
        this.url30min += fullCode + ".gif";
        this.urlDayK += fullCode + ".gif";
        this.urlWeekK += fullCode + ".gif";
        this.urlMonthK += fullCode + ".gif";

        btn1min = (RadioButton)findViewById(R.id.btn1min);
        btn30min = (RadioButton)findViewById(R.id.btn30min);
        btn1day = (RadioButton)findViewById(R.id.btn1day);
        btn7day = (RadioButton)findViewById(R.id.btn7day);
        btn30day = (RadioButton)findViewById(R.id.btn30day);

        btnAddMyStock = (Button)findViewById(R.id.btnAddMyStock);
        btnMoniBuy = (Button)findViewById(R.id.btnMoniBuy);
        btnMoniSell = (Button)findViewById(R.id.btnMoniSell);

        webView = (WebView)findViewById(R.id.webView);


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


        btnAddMyStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyStock myStock = new MyStock(shortCode, fullCode, name);
                ServiceMyStock.getInstance().addMyStock(myStock);
            }
        });


        btnMoniBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*TradeStockRecord tradeStockRecord = new TradeStockRecord(
                        SAFApp.getInstance().deviceid,
                        code,
                        name,


                );*/
               /* SimpleStock simpleStock = new SimpleStock(code, fullCode, name);
                ServiceRealtimeStock.getInstance().getRealTimeStockAndBuy(simpleStock);*/

                showBuyWindow();
            }
        });

        btnMoniSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*TradeStockRecord tradeStockRecord = new TradeStockRecord(
                        SAFApp.getInstance().deviceid,
                        code,
                        name,


                );*/
               /* SimpleStock simpleStock = new SimpleStock(code, fullCode, name);
                ServiceRealtimeStock.getInstance().getRealTimeStockAndBuy(simpleStock);*/

                showSellWindow();
            }
        });


    }


    public void showBuyWindow() {
        View popView =  LayoutInflater.from(this).inflate(R.layout.popwin_trade, null);
        TextView tv = (TextView)popView.findViewById(R.id.tvTradeText); tv.setText("买入");
        final EditText etBuyVol = (EditText)popView.findViewById(R.id.etTradeVol);
        Button btnOk = (Button)popView.findViewById(R.id.btnOK);
        Button btnCancel = (Button)popView.findViewById(R.id.btnCancel);
        final AlertDialog alertDialog =  new AlertDialog.Builder(this).setTitle("模拟买入").setView(popView).create();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer buyVol = Integer.parseInt( etBuyVol.getText().toString() );
                SimpleStock simpleStock = new SimpleStock(shortCode, fullCode, name);
                ServiceRealtimeStock.getInstance().getRealTimeStockAndBuy(simpleStock,buyVol);
                alertDialog.dismiss();
/*
                TradeStock tradeStock = new TradeStock(
                        SAFApp.getInstance().deviceid,
                        shortCode,
                        name
                );
                Integer buyVol = Integer.parseInt( etBuyVol.getText().toString() );
                ServiceTradeStock.getInstance().buyOneStock(tradeStock,buyVol);*/

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }


    public void showSellWindow() {
        View popView =  LayoutInflater.from(this).inflate(R.layout.popwin_trade, null);
        TextView tv = (TextView)popView.findViewById(R.id.tvTradeText); tv.setText("卖出");
        final EditText etSellVol = (EditText)popView.findViewById(R.id.etTradeVol);
        Button btnOk = (Button)popView.findViewById(R.id.btnOK);
        Button btnCancel = (Button)popView.findViewById(R.id.btnCancel);
        final AlertDialog alertDialog =  new AlertDialog.Builder(this).setTitle("模拟卖出").setView(popView).create();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer sellVol = Integer.parseInt( etSellVol.getText().toString() );
                SimpleStock simpleStock = new SimpleStock(shortCode, fullCode, name);
                ServiceRealtimeStock.getInstance().getRealTimeStockAndSell(simpleStock, sellVol);
                alertDialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

}
