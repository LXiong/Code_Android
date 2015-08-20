package com.little.stockgeek.service;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.little.stockgeek.bean.MyStock;
import com.little.stockgeek.bean.TradeStockRecord;

import java.util.List;

import cn.salesuite.saf.app.SAFApp;

/**
 * Created by tusm on 15/8/6.
 */
public class ServiceTradeStockRecord {

    private static ServiceTradeStockRecord serviceTradeStockRecord;

    public static  ServiceTradeStockRecord getInstance(){
        if(serviceTradeStockRecord==null) {
            return new ServiceTradeStockRecord();
        } else {
            return serviceTradeStockRecord;
        }
    }


    public void addTradeStockRecord(TradeStockRecord tradeStockRecord){

        final TradeStockRecord record = tradeStockRecord;
        //AVQuery<AVObject> query = new AVQuery<AVObject>("TradeStockRecord");
        AVObject testObject = new AVObject("TradeStockRecord");

        testObject.put("deviceId", SAFApp.getInstance().deviceid);
        testObject.put("tradeDate", tradeStockRecord.getTradeDate());
        testObject.put("tradeType", tradeStockRecord.getTradeType());
        testObject.put("name", tradeStockRecord.getName());
        testObject.put("deviceId", tradeStockRecord.getDeviceId());
        testObject.put("shortCode", tradeStockRecord.getShortCode());
        testObject.put("tradePrice", tradeStockRecord.getTradePrice());
        testObject.put("tradeVal", tradeStockRecord.getTradeVal());

        testObject.saveInBackground(new SaveCallback() {
            public void done(AVException e) {
                if (e == null) {
                    Log.d("mytag","success");// 保存成功
                } else {
                    Log.d("mytag",e.getMessage());      // 保存失败，输出错误信息
                }
            }
        });

    }

}
