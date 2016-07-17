package com.paullee.liyingheng.ashamednews.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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


public class NewTabFragment extends BaseTabFragment implements HttpHandlerCallback{

    private static final String LOG_TAG=NewTabFragment.class.getSimpleName();

    //Layout for baseTabFragment
    SwipeRefreshLayout baseRefreshLayout;
    ListView dataListView;
    FeaturedAdapter baseAdapter;


    //DataSource
    List<Tweet> parsedList;
    HttpThread httpThread;
    HttpHandler handler;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_base_tab, container, false);

        //Fetch Data From Cloud
        handler=new HttpHandler();
        httpThread=new HttpThread(getActivity(), GlobalApplication.URL_NEW,handler);
        httpThread.start();
        httpThread.handler.setHandlerCallback(this);

        //Initialize View
        initView(view);

        return view;
    }

    private void initView(View view)
    {
        //Initialize Widget
        dataListView=(ListView)view.findViewById(R.id.base_hotNews_ListView);
        //Respond to Vertical Swipe Refresh
        baseRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.base_RefreshLayout);
        baseRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                httpThread.start();
            }
        });
        baseRefreshLayout.setColorSchemeColors(R.color.colorPrimary);
        baseRefreshLayout.setColorSchemeResources(R.color.colorAccent);
    }


    @Override
    public void sendbackHandlerData(List<Tweet> parsedData) {
        if(!parsedData.isEmpty()) {
            parsedList = parsedData;
            baseAdapter=new FeaturedAdapter(getActivity(),parsedList);
            dataListView.setAdapter(baseAdapter);
            baseRefreshLayout.setRefreshing(false);
        }
        else
        {
            Log.d(LOG_TAG,"Data is null");
        }
    }

}
