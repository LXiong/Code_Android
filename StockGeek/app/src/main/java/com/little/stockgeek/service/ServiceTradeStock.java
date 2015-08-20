package com.little.stockgeek.service;

import android.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.little.stockgeek.ActivityMain;
import com.little.stockgeek.AppUtil;
import com.little.stockgeek.R;
import com.little.stockgeek.bean.SimpleStock;
import com.little.stockgeek.bean.TradeStock;
import com.little.stockgeek.bean.TradeStockRecord;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import cn.salesuite.saf.app.SAFApp;

/**
 * Created by tusm on 15/8/6.
 */
public class ServiceTradeStock {

    private static ServiceTradeStock serviceTradeStock;

    public static ServiceTradeStock getInstance(){
        if(serviceTradeStock==null) {
            return new ServiceTradeStock();
        } else {
            return serviceTradeStock;
        }
    }

    public void buyOneStock(SimpleStock simpleStock,Integer buyVol,Double buyPrice) {

        if( SAFApp.getInstance().tradeHome.getRestMoney() < buyVol * buyPrice  ) {
            showCannotBuy("资金余额不够");
            return;
        }

        else {
            final AVQuery<AVObject> query = new AVQuery<AVObject>("TradeStock");
            query.whereEqualTo("deviceId", SAFApp.getInstance().deviceid).whereEqualTo("shortCode", simpleStock.getShortCode());

            try {
                List<AVObject> avObjects = query.find();
                if(avObjects.size()>0) {
                    AVObject oldTradeStock = avObjects.get(0);
                    buyOneStockOld(oldTradeStock,simpleStock,buyVol,buyPrice);
                    oldTradeStock.delete();

                } else {
                    buyOneStockNew(simpleStock,buyVol,buyPrice);
                }

                SAFApp.getInstance().tradeHome.setRestMoney( SAFApp.getInstance().tradeHome.getRestMoney() - buyVol * buyPrice );
                SAFApp.getInstance().tradeHome.setMarketMoney(SAFApp.getInstance().tradeHome.getMarketMoney() + buyVol * buyPrice);
                SAFApp.getInstance().saveTradeHome();

            } catch (AVException e) {
                Log.d("失败", "查询错误: " + e.getMessage());
            }
        }



    }

    public void buyOneStockOld( AVObject oldTradeStock, SimpleStock simpleStock,Integer buyVol,Double buyPrice) {


        AVObject newTradeStock = new AVObject("TradeStock");
        newTradeStock.put("deviceId", SAFApp.getInstance().deviceid);
        newTradeStock.put("shortCode",simpleStock.getShortCode());
        newTradeStock.put("name",simpleStock.getName());
        newTradeStock.put("haveVal", oldTradeStock.getInt("haveVal") + buyVol );
        newTradeStock.put("curCost", oldTradeStock.getDouble("curCost") + buyVol * buyPrice);


        final  SimpleStock stock = simpleStock;
        final Integer buyVolF = buyVol;
        final Double buyPriceF = buyPrice;

        newTradeStock.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {

                AVObject newTradeStockRecord = new AVObject("TradeStockRecord");
                TradeStockRecord tradeStockRecord = new TradeStockRecord(
                        SAFApp.getInstance().deviceid,
                        stock.getShortCode(),
                        stock.getName(),
                        "buy",
                        new Date(),
                        buyPriceF,
                        buyVolF
                );
                ServiceTradeStockRecord.getInstance().addTradeStockRecord(tradeStockRecord);

            }
        });



    }


    //买一个仓位里还不存在的个股,买入量即持仓量
    public void buyOneStockNew(SimpleStock simpleStock,Integer buyVol,Double buyPrice) {

        AVObject testObject = new AVObject("TradeStock");
        testObject.put("deviceId", SAFApp.getInstance().deviceid);
        testObject.put("shortCode",simpleStock.getShortCode());
        testObject.put("name",simpleStock.getName());
        testObject.put("haveVal",buyVol);
        testObject.put("curCost",buyVol * buyPrice);


        final  SimpleStock stock = simpleStock;
        final Integer buyVolF = buyVol;
        final Double buyPriceF = buyPrice;

        testObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                AVObject newTradeStockRecord = new AVObject("TradeStockRecord");
                TradeStockRecord tradeStockRecord = new TradeStockRecord(
                        SAFApp.getInstance().deviceid,
                        stock.getShortCode(),
                        stock.getName(),
                        "buy",
                        new Date(),
                        buyPriceF,
                        buyVolF
                );
                ServiceTradeStockRecord.getInstance().addTradeStockRecord(tradeStockRecord);
            }
        });





    }


   public void sellOneStock( SimpleStock simpleStock, Integer sellVol, Double sellPrice){
       final AVQuery<AVObject> query = new AVQuery<AVObject>("TradeStock");
       query.whereEqualTo("deviceId", SAFApp.getInstance().deviceid).whereEqualTo("shortCode", simpleStock.getShortCode());

       try {
           List<AVObject> avObjects = query.find();
           if(avObjects.size()>0) {

               AVObject oldTradeStock = avObjects.get(0);
               if( oldTradeStock.getInt("haveVal") <  sellVol ) {

                   showCannotSell("卖出股数大于仓位股数，无法卖出");
               }

               else if( oldTradeStock.getInt("haveVal") ==  sellVol ) {
                   SAFApp.getInstance().tradeHome.setMarketMoney( SAFApp.getInstance().tradeHome .getMarketMoney() -  sellVol*sellPrice );
                   SAFApp.getInstance().tradeHome.setRestMoney(SAFApp.getInstance().tradeHome.getRestMoney() + sellVol * sellPrice);
                   SAFApp.getInstance().saveTradeHome();
                   oldTradeStock.delete();

                   TradeStockRecord tradeStockRecord = new TradeStockRecord(
                           SAFApp.getInstance().deviceid,
                           simpleStock.getShortCode(),
                           simpleStock.getName(),
                           "sell",
                           new Date(),
                           sellPrice,
                           sellVol
                   );
                   //String deviceId, String shortCode, String name, String tradeType, Date tradeDate, Double tradePrice, Integer tradeVal
                   ServiceTradeStockRecord.getInstance().addTradeStockRecord(tradeStockRecord);

               }

               else if( oldTradeStock.getInt("haveVal") >  sellVol ) {

                   SAFApp.getInstance().tradeHome.setMarketMoney(SAFApp.getInstance().tradeHome.getMarketMoney() - sellVol * sellPrice);
                   SAFApp.getInstance().tradeHome.setRestMoney(SAFApp.getInstance().tradeHome.getRestMoney() + sellVol * sellPrice);
                   SAFApp.getInstance().saveTradeHome();

                   oldTradeStock.put("haveVal", oldTradeStock.getInt("haveVal") - sellVol);
                   oldTradeStock.save();


                   TradeStockRecord tradeStockRecord = new TradeStockRecord(
                           SAFApp.getInstance().deviceid,
                           simpleStock.getShortCode(),
                           simpleStock.getName(),
                           "sell",
                           new Date(),
                           sellPrice,
                           sellVol
                   );
                   //String deviceId, String shortCode, String name, String tradeType, Date tradeDate, Double tradePrice, Integer tradeVal
                   ServiceTradeStockRecord.getInstance().addTradeStockRecord(tradeStockRecord);

               }else {

               }
               //buyOneStockOld(oldTradeStock,simpleStock,buyVol,buyPrice);
               //oldTradeStock.delete();*/

           } else {
               showCannotSell("您尚未买入该个股，无法卖出");

           }
       } catch (AVException e) {
           Log.d("失败", "查询错误: " + e.getMessage());
       }
   }


    public void showCannotSell(String msg) {
        View popView =  LayoutInflater.from(SAFApp.getInstance().getApplicationContext()).inflate(R.layout.popwin_msg, null);
        TextView tv = (TextView)popView.findViewById(R.id.tvContent); tv.setText(msg);
        Button btnOk = (Button)popView.findViewById(R.id.btnOK);
        final AlertDialog alertDialog =  new AlertDialog.Builder(SAFApp.getInstance().getApplicationContext()).setTitle("提示").setView(popView).create();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }


    public void showCannotBuy(String msg) {
        View popView =  LayoutInflater.from(SAFApp.getInstance().getApplicationContext()).inflate(R.layout.popwin_msg, null);
        TextView tv = (TextView)popView.findViewById(R.id.tvContent); tv.setText(msg);
        Button btnOk = (Button)popView.findViewById(R.id.btnOK);
        final AlertDialog alertDialog =  new AlertDialog.Builder(SAFApp.getInstance().getApplicationContext()).setTitle("提示").setView(popView).create();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }







}
