package com.little.stockgeek.service;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.DeleteCallback;
import com.avos.avoscloud.FindCallback;
import com.little.stockgeek.AdapterMyStockList;
import com.little.stockgeek.bean.MyStock;
import com.little.stockgeek.bean.SimpleStock;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;

import cn.salesuite.saf.app.SAFApp;

/**
 * Created by tusm on 15/8/6.
 */
public class ServiceMyStock {

    private static ServiceMyStock serviceMyStock;

    public static ServiceMyStock getInstance(){
        if(serviceMyStock==null) {
            return new ServiceMyStock();
        } else {
            return serviceMyStock;
        }
    }

    public void getMyStocks(final AdapterMyStockList adapterMyStockList) {

        //
        AVQuery<AVObject> query = new AVQuery<AVObject>("MyStock");
        query.whereEqualTo("deviceId", SAFApp.getInstance().deviceid);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                adapterMyStockList.updateData(list);
            }
        });
    }

    public void addMyStock(final MyStock myStock){

        AVQuery<AVObject> query = new AVQuery<AVObject>("MyStock");
        query.whereEqualTo("deviceId", SAFApp.getInstance().deviceid);
        query.whereEqualTo("shortCode", myStock.getShortCode());


        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (list.size() == 0) {
                    AVObject testObject = new AVObject("MyStock");
                    testObject.put("deviceId", SAFApp.getInstance().deviceid);
                    testObject.put("shortCode", myStock.getShortCode());
                    testObject.put("fullCode", myStock.getFullCode());
                    testObject.put("name", myStock.getName());
                    testObject.saveInBackground();
                }
            }
        });
    }


    public void removeMyStock(final AdapterMyStockList adapterMyStockList,final MyStock myStock) {
        AVQuery<AVObject> query = new AVQuery<AVObject>("MyStock");
        query.whereEqualTo("deviceId", SAFApp.getInstance().deviceid);
        query.whereEqualTo("shortCode", myStock.getShortCode());

        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (list.size() > 0) {
                   list.get(0).deleteInBackground(new DeleteCallback() {
                       @Override
                       public void done(AVException e) {

                           AVQuery<AVObject> query2 = new AVQuery<AVObject>("MyStock");
                           query2.whereEqualTo("deviceId", SAFApp.getInstance().deviceid);
                           query2.findInBackground(new FindCallback<AVObject>() {
                               @Override
                               public void done(List<AVObject> list, AVException e) {
                                   adapterMyStockList.updateData(list);
                               }
                           });



                       }
                   });
                }
            }
        });

    }

}
