package com.little.stockgeek;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.AVObject;
import com.little.stockgeek.bean.MyStock;
import com.little.stockgeek.bean.TradeStock;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.salesuite.saf.app.SAFAdapter;
import cn.salesuite.saf.app.SAFApp;

/**
 * Created by tusm on 15/8/5.
 */
public class AdapterMyStockList extends SAFAdapter<MyStock> {
    List<MyStock> myStockList = new ArrayList<>();
    Activity  activity;

    public AdapterMyStockList(Activity  activity){
        this.activity = activity;
    }

    @Override
    public void setData(List list) {
        super.setData(list);
        this.myStockList = list;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(activity).inflate(R.layout.lv_item_for_mystock,null);
        TextView tvStockName = (TextView)view.findViewById(R.id.stockName);
        TextView tvStockShortCode = (TextView)view.findViewById(R.id.stockShortCode);
        TextView tvStockCurPrice = (TextView)view.findViewById(R.id.stockCurPrice);
        TextView tvStockCurRange = (TextView)view.findViewById(R.id.stockCurRange);
        //TextView tvStockFullCode = (TextView)view.findViewById(R.id.stockFullCode);


        tvStockName.setText( myStockList.get(position).getName() );
        tvStockShortCode.setText( myStockList.get(position).getShortCode() );
        tvStockCurPrice.setText( myStockList.get(position).getStockPrice() );
        tvStockCurRange.setText( myStockList.get(position).getStockRange() + "%" );
        //tvStockFullCode.setText( myStockList.get(position).getFullCode() );
        return view;
    }

    public void updateData(final List<AVObject> list) {
        this.myStockList = new ArrayList<>();
        //int size = avObjects.size();
        String link = "http://qt.gtimg.cn/q=";

        for (int i = 0; i < list.size(); i++) {
            //AVObject testObject = avObjects.get(i);
            link += "s_" + list.get(i).getString("fullCode") + ",";
        }
        final AdapterMyStockList adapterMyStock = this;

        final String url = link;
        new Thread(){
            @Override
            public void run() {
                HttpGet httpGet = new HttpGet(url);
                try {
                    HttpResponse response=new DefaultHttpClient().execute(httpGet);
                    if(response.getStatusLine().getStatusCode()==200){//如果状态码为200,就是正常返回
                        String result = EntityUtils.toString(response.getEntity());
                        String []resp = result.split(";");

                        //股票名称~股票代码~现价~涨跌价~涨跌幅~成交量(手)~成交额(万)~总市值(亿)
                        JSONObject responseJsonObject = new JSONObject();
                        for(int j=0; j<resp.length-1; j++) {

                            String []oneStockStr = resp[j].split("~");
                            MyStock myStock = new MyStock(
                                    list.get(j).getString("shortCode"),
                                    list.get(j).getString("fullCode"),
                                    list.get(j).getString("name")
                            );
                            myStock.setStockPrice(oneStockStr[3]);
                            myStock.setStockRange(oneStockStr[5]);
                            myStockList.add(myStock);
                        }


                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapterMyStock.setData(myStockList);
                                adapterMyStock.notifyDataSetChanged();
                            }
                        });

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
