package com.paullee.liyingheng.ashamednews.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.paullee.liyingheng.ashamednews.DetailActivity;
import com.paullee.liyingheng.ashamednews.R;
import com.paullee.liyingheng.ashamednews.Tweet;
import com.paullee.liyingheng.ashamednews.application.GlobalApplication;
import com.paullee.liyingheng.ashamednews.callback.HttpHandlerCallback;
import com.paullee.liyingheng.ashamednews.http.HttpHandler;
import com.paullee.liyingheng.ashamednews.http.HttpThread;
import com.paullee.liyingheng.ashamednews.http.HttpUtil;

import java.util.List;

public class HotTabFragment extends BaseTabFragment implements HttpHandlerCallback{

    private static final String LOG_TAG=HotTabFragment.class.getSimpleName();

    //Layout for HotTabFragment
    SwipeRefreshLayout hotRefreshLayout;
    ListView dataListView;
    BaseDataAdapter dataAdapter;


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
        //Initialize View
        initView(view);

        //Fetch Data From Cloud
        handler=new HttpHandler();
        hotRefreshLayout.setProgressViewOffset(false, 0, 100);
        hotRefreshLayout.setRefreshing(true);
        httpThread = new HttpThread(getActivity(), GlobalApplication.URL_HOT, handler, null);
        httpThread.start();
        handler.setHandlerCallback(this);

        //Initialize Adapter
        dataAdapter = new BaseDataAdapter(getActivity());


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
                Log.d(LOG_TAG, httpThread.isAlive() + "-----------");
                HttpThread t1 = new HttpThread(getActivity(), GlobalApplication.URL_HOT, handler, null);
                t1.start();
            }
        });
        hotRefreshLayout.setColorSchemeColors(R.color.colorPrimary);
        hotRefreshLayout.setColorSchemeResources(R.color.colorAccent);
    }


    @Override
    public void sendbackHandlerData(List<Tweet> parsedData, int NetworkState) {
        Log.d(LOG_TAG, "sendbackReceived! Network State:" + NetworkState);
        if (parsedData != null) {
            if (!parsedData.isEmpty()) {
                parsedList = parsedData;
                dataAdapter.setDataSource(parsedData);
                dataListView.setAdapter(dataAdapter);
                //Respond to ListView item selected
                dataListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Tweet clickTweet = new Tweet();
                        clickTweet = parsedList.get(position);
                        Intent intent = new Intent(getActivity(), DetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(getString(R.string.intent_tabToDetail), clickTweet);
                        startActivity(intent);
                    }
                });
                hotRefreshLayout.setRefreshing(false);
            } else {
                Log.d(LOG_TAG, "Data is null");
            }
        }
        else
        {
            if (NetworkState == HttpUtil.NETWORK_ERROR_GENERAL) {
                hotRefreshLayout.setRefreshing(false);
                Log.d(LOG_TAG, "No Data. Network State:" + NetworkState);
            }
        }
    }
}




