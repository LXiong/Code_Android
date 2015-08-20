package com.little.stockgeek;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.little.stockgeek.bean.TradeStock;
import com.little.stockgeek.bean.TradeStockRecord;
import com.little.stockgeek.uilib.HVListView;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.salesuite.saf.app.SAFAdapter;
import cn.salesuite.saf.app.SAFApp;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTradeB extends Fragment {

    View mainView;
    HVListView listView;
    AdapterMyTradeStockRecord adapterMyTradeStockRecord;

    public FragmentTradeB() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_trade_b, container, false);

        listView = (HVListView)mainView.findViewById(R.id.listView);
        listView.mListHead = (LinearLayout)mainView.findViewById(R.id.head);
        ArrayList<TradeStockRecord> tradeStockRecords = new ArrayList<>();
        adapterMyTradeStockRecord = new AdapterMyTradeStockRecord();
        adapterMyTradeStockRecord.setData(tradeStockRecords);
        listView.setAdapter(adapterMyTradeStockRecord);

        adapterMyTradeStockRecord.realoadData();
        return mainView;
    }






    class AdapterMyTradeStockRecord extends SAFAdapter<TradeStock> {

        public List<TradeStockRecord> stockArrayList;


        @Override
        public void setData(List list) {
            super.setData(list);
            this.stockArrayList = list;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.lv_item_for_tradestockrecord, null);
            TradeStockRecord tradeStockRecord = this.stockArrayList.get(position);

            TextView tvStockName = (TextView)view.findViewById(R.id.stockName);  tvStockName.setText(tradeStockRecord.getName());

            String tradeType = "";
            if(tradeStockRecord.getTradeType().equals("sell")) {
                tradeType = "卖出";
            } else {
                tradeType = "买入";
            }

            TextView tvStockTradeType = (TextView)view.findViewById(R.id.stockTradeType); tvStockTradeType.setText( tradeType );
            TextView tvStockTradeVol = (TextView)view.findViewById(R.id.stockTradeVol);  tvStockTradeVol.setText(  String.valueOf(tradeStockRecord.getTradeVal()));
            TextView tvStockTradePrice = (TextView)view.findViewById(R.id.stockTradePrice);   tvStockTradePrice.setText( String.format("%.2f", tradeStockRecord.getTradePrice() ));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            TextView tvStockTradeDate = (TextView)view.findViewById(R.id.stockTradeDate);  tvStockTradeDate.setText( simpleDateFormat.format(tradeStockRecord.getTradeDate()) );


            return view;
        }



        public void realoadData() {
            final AVQuery<AVObject> query = new AVQuery<AVObject>("TradeStockRecord");
            query.whereEqualTo("deviceId", SAFApp.getInstance().deviceid);
            query.findInBackground(new FindCallback<AVObject>() {
                public void done(List<AVObject> avObjects, AVException e) {
                    if (e == null) {
                        Log.d("mytag", "ss查询到" + avObjects.size() + " 条符合条件的数据");
                        updateData(avObjects);
                    } else {
                        Log.d("mytag", "查询错误: " + e.getMessage());
                    }
                }
            });
        }

        public void updateData(final List<AVObject> avObjects) {
            this.stockArrayList.clear();

            for (int i = 0; i < avObjects.size(); i++) {
                //BeanRealtimeStock beanRealtimeStock = new BeanRealtimeStock();
                AVObject testObject = avObjects.get(i);
                TradeStockRecord tradeStockRecord = new TradeStockRecord(
                        SAFApp.getInstance().deviceid,
                        testObject.getString("shortCode"),
                        testObject.getString("name"),
                        testObject.getString("tradeType"),
                        testObject.getDate("tradeDate"),
                        testObject.getDouble("tradePrice"),
                        testObject.getInt("tradeVal")
                );
                this.stockArrayList.add(tradeStockRecord);
                //String deviceId, String shortCode, String name, String tradeType, Date tradeDate, Double tradePrice, Integer tradeVal

            }
            notifyDataSetChanged();
        }



    }


}
