package com.little.stockgeek;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.little.stockgeek.bean.MyStock;
import com.little.stockgeek.service.ServiceMyStock;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import cn.salesuite.saf.app.SAFApp;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentThree extends Fragment {

    private AutoCompleteTextView autotext;
    private ArrayAdapter<String> arrayAdapter;
    AdapterMyStockList adapterMyStockList;
    ListView listViewMyStocks;
    String [] arr={};

    public FragmentThree() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ActivityMain.toolbar.setTitle("自选股");

        View view = inflater.inflate(R.layout.fragment_three, container, false);
        autotext =(AutoCompleteTextView) view.findViewById(R.id.autotext);
        listViewMyStocks = (ListView)view.findViewById(R.id.listView);
        autotext.setThreshold(1);

        arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,arr);
        autotext.setAdapter(arrayAdapter);

        autotext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                loadData(s);
            }
        });

        autotext.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String code = arr[position].substring(0, 6);
                String name = arr[position].substring(6);
                String fullCode = AppUtil.getFullCodeFromCode(code);

                Bundle bundle = new Bundle();
                bundle.putString("code", code);
                bundle.putString("fullCode", fullCode);
                bundle.putString("name", name);

                Intent intentToActivityStockChart = new Intent(getActivity(), ActivityStockChart.class);
                intentToActivityStockChart.putExtras(bundle);
                startActivity(intentToActivityStockChart);
            }
        });


        adapterMyStockList = new AdapterMyStockList(getActivity());
        listViewMyStocks.setAdapter(adapterMyStockList);


        listViewMyStocks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyStock myStock = adapterMyStockList.getList().get(position);
                Bundle bundle = new Bundle();
                bundle.putString("code", myStock.getShortCode());
                bundle.putString("fullCode", myStock.getFullCode());
                bundle.putString("name", myStock.getName());

                Intent intentToActivityStockChart = new Intent(getActivity(), ActivityStockChart.class);
                intentToActivityStockChart.putExtras(bundle);
                startActivity(intentToActivityStockChart);
            }
        });

        listViewMyStocks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showIfDelte(position);
                return  true;
            }
        });




        //ServiceMyStock.getInstance().getMyStocks(adapterMyStockList);





        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        ServiceMyStock.getInstance().getMyStocks(adapterMyStockList);
    }

    public void showIfDelte(final int position) {
        View popView =  LayoutInflater.from(SAFApp.getInstance().getApplicationContext()).inflate(R.layout.popwin_msg2, null);
        Button btnOk = (Button)popView.findViewById(R.id.btnOK);
        Button btnCancel = (Button)popView.findViewById(R.id.btnCancel);
        final AlertDialog alertDialog =  new AlertDialog.Builder(getActivity()).setTitle("是否删除?").setView(popView).create();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
                ServiceMyStock.getInstance().removeMyStock(adapterMyStockList,adapterMyStockList.getList().get(position));

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

    public void loadData(CharSequence text) {

        if(text.toString().length() == 0) {
            return;
        }
        final String url = "http://code.jrjimg.cn/code?1=1&item=10&typepri=s&areapri=cn&type=cn_s&key=" + text.toString();
        new Thread(){
            @Override
            public void run() {


                HttpGet httpGet = new HttpGet(url);
                try {
                    HttpResponse response=new DefaultHttpClient().execute(httpGet);
                    if(response.getStatusLine().getStatusCode()==200){//如果状态码为200,就是正常返回
                        String result= EntityUtils.toString(response.getEntity());
                        result = result.substring(14,result.length()-1);
                        JSONObject jsonObject = JSON.parseObject(result);
                        JSONArray jsonArray = jsonObject.getJSONArray("CodeData");
                        arr = new String[jsonArray.size()];
                        for(int i=0; i<jsonArray.size(); i++) {
                            JSONObject oneStockObject = jsonArray.getJSONObject(i);
                            arr[i] = oneStockObject.getString("code") + oneStockObject.getString("name");
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,arr);
                                autotext.setAdapter(arrayAdapter);
                                arrayAdapter.notifyDataSetChanged();
                            }
                        });


                        //如果是下载文件,可以用response.getEntity().getContent()返回InputStream
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

}
