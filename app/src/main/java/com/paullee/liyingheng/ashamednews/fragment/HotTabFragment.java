package com.paullee.liyingheng.ashamednews.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.paullee.liyingheng.ashamednews.R;
import com.paullee.liyingheng.ashamednews.Tweet;
import com.paullee.liyingheng.ashamednews.application.GlobalApplication;
import com.paullee.liyingheng.ashamednews.callback.HttpHandlerCallback;
import com.paullee.liyingheng.ashamednews.http.HttpHandler;
import com.paullee.liyingheng.ashamednews.http.HttpThread;

import java.util.List;

public class HotTabFragment extends BaseTabFragment implements HttpHandlerCallback{

    private static final String LOG_TAG=HotTabFragment.class.getSimpleName();

    //Layout for HotTabFragment
    SwipeRefreshLayout hotRefreshLayout;
    ListView dataListView;
    FeaturedAdapter HotAdapter;


    //DataSource
    List<Tweet> parsedList;
    HttpThread httpThread;
    HttpHandler handler;

    public HotTabFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_hot_tab, container, false);

        //Fetch Data From Cloud
        handler=new HttpHandler();
        httpThread=new HttpThread(getActivity(), GlobalApplication.URL_HOT,handler);
        httpThread.start();
        //httpThread.setHandlerCallback(this);
        handler.setHandlerCallback(this);

        //Initialize View
        initView(view);

        return view;
    }

    private void initView(View view)
    {
        //Initialize Widget
        dataListView=(ListView)view.findViewById(R.id.hot_hotNews_ListView);
        //Respond to Vertical Swipe Refresh
        hotRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.hot_RefreshLayout);
        hotRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                httpThread.start();
            }
        });
        hotRefreshLayout.setColorSchemeColors(R.color.colorPrimary);
        hotRefreshLayout.setColorSchemeResources(R.color.colorAccent);
    }


    @Override
    public void sendbackHandlerData(List<Tweet> parsedData) {
        if(!parsedData.isEmpty()) {
            parsedList = parsedData;
            HotAdapter=new FeaturedAdapter(getActivity(),parsedList);
            dataListView.setAdapter(HotAdapter);
            hotRefreshLayout.setRefreshing(false);
        }
        else
        {
            Log.d(LOG_TAG,"Data is null");
        }
    }
}




