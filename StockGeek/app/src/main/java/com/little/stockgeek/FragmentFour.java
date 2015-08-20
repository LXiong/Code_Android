package com.little.stockgeek;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.little.stockgeek.uilib.CustomViewPager;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFour extends Fragment {

    CustomViewPager pager;
    SlidingTabLayout tabs;

    public FragmentFour() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        ActivityMain.toolbar.setTitle("模拟炒股");

        View view = inflater.inflate(R.layout.fragment_four, container, false);

        pager = (CustomViewPager)view.findViewById(R.id.pagerBlock);
        pager.setAdapter(new ViewPagerAdapter(getActivity().getSupportFragmentManager()));
        tabs = (SlidingTabLayout)view.findViewById(R.id.tabBlock);
        tabs.setDistributeEvenly(true);
        tabs.setViewPager(pager);



        return view;

    }



    class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        public Fragment getItem(int num) {
            if(num==0) {
                return new FragmentTradeA();  //板块排行
            }
            else if(num == 1) {
                return new FragmentTradeB();  //概念排行
            }
            else{
                return null;
            }

        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            if(position==0) {
                return "盈亏统计";
            }
            else if(position == 1) {
                return "交易记录";
            }
            else{
                return null;
            }
        }

    }



}
