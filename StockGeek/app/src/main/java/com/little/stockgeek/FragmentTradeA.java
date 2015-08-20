package com.little.stockgeek;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.little.stockgeek.bean.MyStock;
import com.little.stockgeek.bean.TradeStock;
import com.little.stockgeek.service.ServiceTradeStock;
import com.little.stockgeek.uilib.HVListView;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import cn.salesuite.saf.app.SAFAdapter;
import cn.salesuite.saf.app.SAFApp;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTradeA extends Fragment {



    AdapterMyTradeStock adapterMyTradeStocks;
    HVListView listView;

    public FragmentTradeA() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_trade_a, container, false);
        listView = (HVListView)mainView.findViewById(R.id.listView);
        listView.mListHead = (LinearLayout)mainView.findViewById(R.id.head);
        ArrayList<TradeStock> tradeStocks = new ArrayList<>();
        adapterMyTradeStocks = new AdapterMyTradeStock();
        adapterMyTradeStocks.setData(tradeStocks);
        listView.setAdapter(adapterMyTradeStocks);

        adapterMyTradeStocks.realoadData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TradeStock tradeStock = adapterMyTradeStocks.getList().get(position);
                Bundle bundle = new Bundle();
                bundle.putString("code", tradeStock.getShortCode());
                bundle.putString("fullCode", AppUtil.getFullCodeFromCode(tradeStock.getShortCode()) );
                bundle.putString("name", tradeStock.getName());

                Intent intentToActivityStockChart = new Intent(getActivity(), ActivityStockChart.class);
                intentToActivityStockChart.putExtras(bundle);
                startActivity(intentToActivityStockChart);
            }
        });


        return mainView;
    }


    class AdapterMyTradeStock extends SAFAdapter<TradeStock> {

        public List<TradeStock> stockArrayList;


        @Override
        public void setData(List list) {
            super.setData(list);
            this.stockArrayList = list;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.lv_item_for_tradestock, null);
            TradeStock tradeStock = this.stockArrayList.get(position);

            TextView tvStockName = (TextView)view.findViewById(R.id.stockName);  tvStockName.setText(tradeStock.getName());
            TextView tvStockCurPrice = (TextView)view.findViewById(R.id.stockCurPrice); tvStockCurPrice.setText( String.valueOf(tradeStock.getCurPrice()) );
            TextView tvStockHaveVol = (TextView)view.findViewById(R.id.stockHaveVol);  tvStockHaveVol.setText(  String.valueOf( tradeStock.getHaveVol() ));
            TextView tvStockCurVal = (TextView)view.findViewById(R.id.stockCurVal);   tvStockCurVal.setText( String.format("%.2f", tradeStock.getCurVal()));   //市值
            TextView tvStockCurCost = (TextView)view.findViewById(R.id.stockCurCost);  tvStockCurCost.setText( String.format("%.2f", tradeStock.getCurCost()) ); //成本
            TextView tvStockDeltaMoney = (TextView)view.findViewById(R.id.stockDeltaMoney);  tvStockDeltaMoney.setText( String.format("%.2f", tradeStock.getDeltaMoney()) );
            TextView tvStockDeltaRate = (TextView)view.findViewById(R.id.stockDeltaRate);   tvStockDeltaRate.setText( String.format("%.2f", tradeStock.getDeltaRate()) + "%"  );


            return view;
        }



        public void realoadData() {
            final AVQuery<AVObject> query = new AVQuery<AVObject>("TradeStock");
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
            int size = avObjects.size();
            String link = "http://qt.gtimg.cn/q=";

            for (int i = 0; i < size; i++) {
                AVObject testObject = avObjects.get(i);
                link += AppUtil.getFullCodeFromCode( testObject.getString("shortCode") ) + ",";
            }
            final AdapterMyTradeStock adapterMyTradeStock = this;

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
                            for(int i=0; i<resp.length-1; i++) {
                                //BeanRealtimeStock beanRealtimeStock = new BeanRealtimeStock();
                                AVObject testObject = avObjects.get(i);
                                String []oneStockStr = resp[i].split("~");
                                TradeStock tradeStock = new TradeStock(
                                        SAFApp.getInstance().deviceid,
                                        testObject.getString("shortCode"),
                                        testObject.getString("name")
                                );
                                tradeStock.setCurCost(testObject.getDouble("curCost"));
                                tradeStock.setCurPrice(Double.parseDouble(oneStockStr[3]));
                                tradeStock.setHaveVol(testObject.getInt("haveVal"));
                                tradeStock.setCurVal( tradeStock.getCurPrice() * tradeStock.getHaveVol() );
                                tradeStock.setDeltaMoney(  tradeStock.getCurVal() - tradeStock.getCurCost() );
                                tradeStock.setDeltaRate( (tradeStock.getCurVal() - tradeStock.getCurCost())*100/tradeStock.getCurCost() );



                                stockArrayList.add(tradeStock);

                            }

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    notifyDataSetChanged();
                                }
                            });

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();

        }


        @Override
        public List<TradeStock> getList() {
            return stockArrayList;
        }
    }





}
