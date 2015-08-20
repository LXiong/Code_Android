package com.little.stockgeek;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.LogUtil;
import com.little.stockgeek.bean.TradeHome;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;

import cn.salesuite.saf.app.SAFApp;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMain extends Fragment {


    View linearLayout1,linearLayout2,linearLayout3,linearLayout4;
    View mainView;
    TextView tvOriginMoney,tvMarketMoney,tvRestMoney,tvEarnMoney,tvEarnRate ,tvSumMoney;

    public FragmentMain() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.d("mytag","fragmentMain onCreateView");
        mainView = inflater.inflate(R.layout.fragment_main, container, false);


        linearLayout1 = (View)mainView.findViewById(R.id.linear1);
        linearLayout2 = (View)mainView.findViewById(R.id.linear2);
        linearLayout3 = (View)mainView.findViewById(R.id.linear3);
        linearLayout4 = (View)mainView.findViewById(R.id.linear4);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fontawesome-webfont.ttf");
        TextView tv1 = (TextView) mainView.findViewById(R.id.textView1);   tv1.setTypeface(font);
        tv1.getText();
        TextView tv2 = (TextView) mainView.findViewById(R.id.textView2);   tv2.setTypeface(font);
        TextView tv3 = (TextView) mainView.findViewById(R.id.textView3);   tv3.setTypeface(font);
        TextView tv4 = (TextView) mainView.findViewById(R.id.textView4);   tv4.setTypeface(font);

         tvOriginMoney = (TextView) mainView.findViewById(R.id.originMoney);
         tvMarketMoney = (TextView) mainView.findViewById(R.id.marketMoney);
         tvRestMoney = (TextView) mainView.findViewById(R.id.restMoney);
         tvEarnMoney = (TextView) mainView.findViewById(R.id.earnMoney);
         tvEarnRate = (TextView) mainView.findViewById(R.id.earnRate);
         tvSumMoney = (TextView) mainView.findViewById(R.id.sumMoney);


        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ActivityMain) getActivity()).jumpToFragment(1);
            }
        });

        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ActivityMain) getActivity()).jumpToFragment(2);
            }
        });

        linearLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ActivityMain) getActivity()).jumpToFragment(3);
            }
        });

        linearLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ActivityMain) getActivity()).jumpToFragment(4);
            }
        });


        //updateMoney();




        return mainView;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        Log.d("mytag", "fragmentMain onCreate");



    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onResume() {
        super.onResume();

        ActivityMain.toolbar.setTitle("模拟炒股CC");
        updateMoney();
    }




    public void updateMoney() {


        final AVQuery<AVObject> query = new AVQuery<AVObject>("TradeStock");
        query.whereEqualTo("deviceId", SAFApp.getInstance().deviceid);
        query.findInBackground(new FindCallback<AVObject>() {
            public void done(final List<AVObject> avObjects, AVException e) {
                if (e == null) {


                    Log.d("mytag", "ss查询到" + avObjects.size() + " 条符合条件的数据");
                    int size = avObjects.size();
                    if(size>0) {
                        String link = "http://qt.gtimg.cn/q=";

                        for (int i = 0; i < size; i++) {
                            AVObject testObject = avObjects.get(i);
                            link += AppUtil.getFullCodeFromCode(testObject.getString("shortCode")) + ",";
                        }

                        final String url = link;
                        new Thread() {
                            @Override
                            public void run() {
                                HttpGet httpGet = new HttpGet(url);
                                try {
                                    HttpResponse response = new DefaultHttpClient().execute(httpGet);
                                    if (response.getStatusLine().getStatusCode() == 200) {//如果状态码为200,就是正常返回
                                        String result = EntityUtils.toString(response.getEntity());
                                        String[] resp = result.split(";");

                                        Double marketMoney = 0.0;
                                        //股票名称~股票代码~现价~涨跌价~涨跌幅~成交量(手)~成交额(万)~总市值(亿)
                                        JSONObject responseJsonObject = new JSONObject();
                                        for (int i = 0; i < resp.length - 1; i++) {
                                            //BeanRealtimeStock beanRealtimeStock = new BeanRealtimeStock();
                                            AVObject testObject = avObjects.get(i);
                                            String[] oneStockStr = resp[i].split("~");
                                            marketMoney += Double.parseDouble(oneStockStr[3]) * testObject.getInt("haveVal");
                                        }

                                        //计算出当前股票市值，剩下的都好办了
                                        SAFApp.getInstance().tradeHome.setMarketMoney(marketMoney);

                                        updateAllMoney();

                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                    }

                    else {
                        //计算出当前股票市值，剩下的都好办了
                        SAFApp.getInstance().tradeHome.setMarketMoney(0.0);
                        updateAllMoney();
                    }



                } else {
                    Log.d("mytag", "查询错误: " + e.getMessage());
                }
            }
        });
    }


    public void updateAllMoney() {
        //查询初始仓位，如果没有，默认为50，000
        AVQuery<AVObject> query = new AVQuery<AVObject>("TradeHome");
        query.whereEqualTo("deviceId", SAFApp.getInstance().deviceid);

        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (list.size() > 0) {
                    AVObject avObject = list.get(0);

                    SAFApp.getInstance().tradeHome.setOriginMoney(SAFApp.ORIGIN_MONEY);
                    SAFApp.getInstance().tradeHome.setRestMoney(avObject.getDouble("restMoney"));
                    SAFApp.getInstance().tradeHome.setSumMoney(SAFApp.getInstance().tradeHome.getMarketMoney() + SAFApp.getInstance().tradeHome.getRestMoney());
                } else {

                    SAFApp.getInstance().tradeHome.setOriginMoney(SAFApp.ORIGIN_MONEY);
                    SAFApp.getInstance().tradeHome.setRestMoney(SAFApp.ORIGIN_MONEY);
                    SAFApp.getInstance().tradeHome.setSumMoney(SAFApp.getInstance().tradeHome.getMarketMoney() + SAFApp.getInstance().tradeHome.getRestMoney());
                    try {
                        SAFApp.getInstance().createTradeHome();
                    } catch (AVException e1) {
                        e1.printStackTrace();
                    }
                }



                Double sumMoney = SAFApp.getInstance().tradeHome.getRestMoney() + SAFApp.getInstance().tradeHome.getMarketMoney();
                tvOriginMoney.setText(String.format("%.0f", SAFApp.getInstance().tradeHome.getOriginMoney()));
                tvMarketMoney.setText(String.format("%.0f", SAFApp.getInstance().tradeHome.getMarketMoney() ));
                tvSumMoney.setText(String.format("%.0f", sumMoney ));
                tvRestMoney.setText( String.format("%.0f", SAFApp.getInstance().tradeHome.getRestMoney() ) );
                tvEarnMoney.setText(String.format("%.0f", sumMoney - SAFApp.getInstance().tradeHome.getOriginMoney()));
                tvEarnRate.setText(String.format("%.2f", (sumMoney - SAFApp.getInstance().tradeHome.getOriginMoney()) / SAFApp.getInstance().tradeHome.getOriginMoney()) + "%");

            }
        });

    }

}
