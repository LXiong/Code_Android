package com.little.stockgeek.service;

import android.content.SyncStatusObserver;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.little.stockgeek.bean.BlockItem;
import com.little.stockgeek.bean.MyStock;
import com.little.stockgeek.bean.SimpleStock;
import com.little.stockgeek.bean.TradeStockRecord;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Date;

import cn.salesuite.saf.app.SAFApp;

/**
 * Created by tusm on 15/8/6.
 */
public class ServiceRealtimeStock {

    private static ServiceRealtimeStock serviceRealtimeStock;

    public static  ServiceRealtimeStock getInstance(){
        if(serviceRealtimeStock==null) {
            return new ServiceRealtimeStock();
        } else {
            return serviceRealtimeStock;
        }
    }


    public void getRealTimeStockAndBuy(final SimpleStock simpleStock,final Integer buyVol) {

        String link = "http://qt.gtimg.cn/q=";
        String stockShortCode = simpleStock.getShortCode();
        if (stockShortCode.charAt(0) == '6') {
            stockShortCode = ("s_sh" + stockShortCode);
        } else {
            stockShortCode = ("s_sz" + stockShortCode);
        }
        link += stockShortCode;

        final String url = link;
        new Thread(){
            @Override
            public void run() {
                HttpGet httpGet = new HttpGet(url);
                try {
                    HttpResponse response=new DefaultHttpClient().execute(httpGet);
                    if(response.getStatusLine().getStatusCode()==200){//如果状态码为200,就是正常返回
                        String result = EntityUtils.toString(response.getEntity());
                        String [] arr = result.split("~");

                        ServiceTradeStock.getInstance().buyOneStock(
                                simpleStock,
                                buyVol,   //买入股数
                                Double.parseDouble( arr[3] ) //买入价
                        );

                        /*TradeStockRecord tradeStockRecord = new TradeStockRecord(
                                SAFApp.getInstance().deviceid,
                                simpleStock.getShortCode(),
                                simpleStock.getName(),
                                "buy",
                                new Date(),
                                Double.parseDouble( arr[3] ),
                                buyVol
                                //String deviceId, String shortCode, String name, String tradeType, Date tradeDate, Double tradePrice, Integer tradeVal
                        );

                        ServiceTradeStockRecord.getInstance().addTradeStockRecord(tradeStockRecord);*/




                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }


    public void getRealTimeStockAndSell(final SimpleStock simpleStock,final Integer sellVol) {

        String link = "http://qt.gtimg.cn/q=";
        String stockShortCode = simpleStock.getShortCode();
        if (stockShortCode.charAt(0) == '6') {
            stockShortCode = ("s_sh" + stockShortCode);
        } else {
            stockShortCode = ("s_sz" + stockShortCode);
        }
        link += stockShortCode;

        final String url = link;
        new Thread(){
            @Override
            public void run() {
                HttpGet httpGet = new HttpGet(url);
                try {
                    HttpResponse response=new DefaultHttpClient().execute(httpGet);
                    if(response.getStatusLine().getStatusCode()==200){//如果状态码为200,就是正常返回
                        String result = EntityUtils.toString(response.getEntity());
                        String [] arr = result.split("~");

                        ServiceTradeStock.getInstance().sellOneStock(simpleStock,sellVol,Double.parseDouble( arr[3] ));
                        /*TradeStockRecord tradeStockRecord = new TradeStockRecord(
                                SAFApp.getInstance().deviceid,
                                simpleStock.getShortCode(),
                                simpleStock.getName(),
                                "sell",
                                new Date(),
                                Double.parseDouble( arr[3] ),
                                sellVol
                                //String deviceId, String shortCode, String name, String tradeType, Date tradeDate, Double tradePrice, Integer tradeVal
                        );

                        ServiceTradeStockRecord.getInstance().addTradeStockRecord(tradeStockRecord);*/
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }


/*

            String []oneStockStr = response[i].split("~");
            beanRealtimeStock.setStockName(oneStockStr[1]);  //名称
            beanRealtimeStock.setStockCode(oneStockStr[2]);  //代码
            beanRealtimeStock.setStockPrice(oneStockStr[3]); //现价
            beanRealtimeStock.setStockRangePrice(oneStockStr[4]); //涨跌价
            beanRealtimeStock.setStockRange(oneStockStr[5]); //涨跌幅
            beanRealtimeStock.setStockVol(oneStockStr[6]);
            beanRealtimeStock.setStockVolPrice(oneStockStr[7]);
            beanRealtimeStock.setStockMarketPrice(oneStockStr[9].substring(0, oneStockStr[9].length()-1)); //总市值
            responseJsonObject.put(oneStockStr[2], beanRealtimeStock);
*/


}
