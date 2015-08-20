package com.little.stockgeek;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.little.stockgeek.bean.BlockItem;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;

import cn.salesuite.saf.app.SAFAdapter;
import cn.salesuite.saf.http.CommHttpClient;
import cn.salesuite.saf.utils.StringUtils;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentBlockA extends Fragment {

    RadioButton rbtnBlockUp,rbtnBlockDown;
    ListView listView;
    BlockListViewAdapter blockListViewAdapter;
    ArrayList<BlockItem> blockItems;


    public FragmentBlockA() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_block_a, container, false);
        listView = (ListView)view.findViewById(R.id.listView);
        blockItems = new ArrayList<>();

        rbtnBlockUp = (RadioButton)view.findViewById(R.id.btnBlockUp);
        rbtnBlockDown = (RadioButton)view.findViewById(R.id.btnBlockDown);

        blockListViewAdapter = new BlockListViewAdapter(getActivity());
        blockListViewAdapter.setData(blockItems);
        listView.setAdapter(blockListViewAdapter);


        rbtnBlockUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String url = "http://q.jrjimg.cn/?" + URLEncoder.encode("q=cn|bk|5&n=hqa&c=l&o=pl,d&p=1020");
                loadData(url);
            }
        });

        rbtnBlockDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String url = "http://q.jrjimg.cn/?"+ URLEncoder.encode("q=cn|bk|5&n=hqa&c=l&o=pl,a&p=1020");
                loadData(url);
            }
        });

        rbtnBlockUp.performClick();


        return view;
    }




    public void loadData( String strUrl) {
        final String url = strUrl;
        new Thread(){
            @Override
            public void run() {


                HttpGet httpGet = new HttpGet(url);
                try {
                    HttpResponse response=new DefaultHttpClient().execute(httpGet);
                    if(response.getStatusLine().getStatusCode()==200){//如果状态码为200,就是正常返回
                        String result= EntityUtils.toString(response.getEntity());
                        result = result.substring(8,result.length()-2);
                        JSONObject jsonObject = JSON.parseObject(result);
                        JSONArray jsonArray = jsonObject.getJSONArray("HqData");
                        blockItems.clear();
                        for(int i=0; i<jsonArray.size(); i++) {
                            JSONArray oneItem = jsonArray.getJSONArray(i);
                            blockItems.add(new BlockItem(
                                    oneItem.getString(2),
                                    oneItem.getString(10),
                                    oneItem.getString(7),
                                    oneItem.getString(8)
                            ));
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                blockListViewAdapter.notifyDataSetChanged();
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
