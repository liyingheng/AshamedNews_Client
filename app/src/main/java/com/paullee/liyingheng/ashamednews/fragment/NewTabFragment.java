package com.paullee.liyingheng.ashamednews.fragment;

import android.os.Bundle;
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
import com.paullee.liyingheng.ashamednews.http.HttpUtil;

import java.util.List;


public class NewTabFragment extends BaseTabFragment implements HttpHandlerCallback{

    private static final String LOG_TAG=NewTabFragment.class.getSimpleName();

    //Layout for baseTabFragment
    SwipeRefreshLayout baseRefreshLayout;
    ListView dataListView;
    BaseDataAdapter baseDataAdapter;


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
        httpThread = new HttpThread(getActivity(), GlobalApplication.URL_NEW, handler, null);
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
        baseRefreshLayout.setProgressViewOffset(false, 0, 100);
        baseRefreshLayout.setRefreshing(true);
        baseRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                HttpThread t1 = new HttpThread(getActivity(), GlobalApplication.URL_NEW, handler, null);
                t1.start();
            }
        });
        baseRefreshLayout.setColorSchemeColors(R.color.colorPrimary);
        baseRefreshLayout.setColorSchemeResources(R.color.colorAccent);
    }


    @Override
    public void sendbackHandlerData(List<Tweet> parsedData, int NetworkState) {
        if (parsedData != null) {
            if (!parsedData.isEmpty()) {
                parsedList = parsedData;
                baseDataAdapter = new BaseDataAdapter(getActivity(), parsedList);
                dataListView.setAdapter(baseDataAdapter);
                baseRefreshLayout.setRefreshing(false);
            } else {
                baseRefreshLayout.setRefreshing(false);
                Log.d(LOG_TAG, "Data is empty");
                dataListView.setEmptyView(emptyDataView);
            }
        }
        else
        {
            baseRefreshLayout.setRefreshing(false);
            Log.d(LOG_TAG,"Data is null");
        }
    }

}
