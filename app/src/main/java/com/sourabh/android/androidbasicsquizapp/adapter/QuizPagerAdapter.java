package com.sourabh.android.androidbasicsquizapp.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sourabh.android.androidbasicsquizapp.R;

import java.util.List;

public class QuizPagerAdapter extends PagerAdapter {
    List<View> pagerList;
    public QuizPagerAdapter(List<View> pagerList){
        this.pagerList = pagerList;
    }

    public List<View> getPagerList() {
        return pagerList;
    }

    @Override
    public int getCount() {
        return pagerList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        container.addView(pagerList.get(position));
        return pagerList.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
        container.removeView(pagerList.get(position));
    }
}
