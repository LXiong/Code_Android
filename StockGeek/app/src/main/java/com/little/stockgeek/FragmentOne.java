package com.little.stockgeek;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOne extends Fragment  {

    ViewPager pager;
    SlidingTabLayout tabs;

    public FragmentOne() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ActivityMain.toolbar.setTitle("行情概览");

        Log.d("mytag","fragmentOne onCreateView");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_one, container, false);

        pager = (ViewPager)view.findViewById(R.id.pagerSummary);
        pager.setAdapter(new ViewPagerAdapter(getActivity().getSupportFragmentManager()));
        tabs = (SlidingTabLayout)view.findViewById(R.id.tabSummary);
        tabs.setDistributeEvenly(true);
        tabs.setViewPager(pager);




        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("mytag","fragmentOne onCreate");

    }





    class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        public Fragment getItem(int num) {
            if(num==0) {
                return new FragmentA();  //上证指数
            }
            else if(num == 1) {
                return new FragmentB();  //深圳指数
            }
            else if(num ==2) {
                return new FragmentC(); //
            }else{
                return null;
            }

        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            if(position==0) {
                return "上证指数";
            }
            else if(position == 1) {
                return "深圳指数";
            }
            else if(position ==2) {
                return "创业板指";
            }else{
                return null;
            }
        }

    }

}

