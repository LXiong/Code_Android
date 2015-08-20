package com.little.stockgeek;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.little.stockgeek.bean.BlockItem;

import java.util.List;

import cn.salesuite.saf.app.SAFAdapter;

public class BlockListViewAdapter extends SAFAdapter {

    FragmentActivity activity;
    List<BlockItem> blockItemList;

    BlockListViewAdapter(FragmentActivity activity) {
        this.activity = activity;
    }

    @Override
    public void setData(List list) {
        super.setData(list);
        this.blockItemList = list;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(this.activity).inflate(R.layout.lv_item_for_block, null);
        TextView tvBlockName = (TextView) view.findViewById(R.id.blockName);
        TextView tvBlockRange = (TextView) view.findViewById(R.id.blockRange);
        TextView tvBlockHand = (TextView) view.findViewById(R.id.blockSumHand);
        TextView tvBlockSumPrice = (TextView) view.findViewById(R.id.blockSumPrice);
        BlockItem blockItem = this.blockItemList.get(position);
        tvBlockName.setText(blockItem.getBlockName());
        tvBlockRange.setText(blockItem.getBlockRange());
        tvBlockHand.setText(blockItem.getBlockSumHand());
        tvBlockSumPrice.setText(blockItem.getBlockSumPrice());
        return view;
    }
}
