package com.little.stockgeek;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTwo extends Fragment {

    ViewPager pager;
    SlidingTabLayout tabs;


    public FragmentTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ActivityMain.toolbar.setTitle("板块行情");

        View view = inflater.inflate(R.layout.fragment_two, container, false);

        pager = (ViewPager)view.findViewById(R.id.pagerBlock);
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
                return new FragmentBlockA();  //板块排行
            }
            else if(num == 1) {
                return new FragmentBlockB();  //概念排行
            }
            else if(num ==2) {
                return new FragmentBlockC(); //地域排行
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
                return "概念排行";
            }
            else if(position == 1) {
                return "行业排行";
            }
            else if(position ==2) {
                return "地域排行";
            }else{
                return null;
            }
        }

    }


}
