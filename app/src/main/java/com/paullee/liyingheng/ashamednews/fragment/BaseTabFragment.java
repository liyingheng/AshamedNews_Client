package com.paullee.liyingheng.ashamednews.fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.paullee.liyingheng.ashamednews.R;
import com.paullee.liyingheng.ashamednews.Tweet;
import com.paullee.liyingheng.ashamednews.application.GlobalApplication;
import com.paullee.liyingheng.ashamednews.callback.HttpHandlerCallback;
import com.paullee.liyingheng.ashamednews.callback.ILoading;
import com.paullee.liyingheng.ashamednews.http.HttpThread;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseTabFragment extends Fragment{

    private static final String LOG_TAG=BaseTabFragment.class.getSimpleName();

    public ILoading loadingInterface;

    Handler handler=new Handler();

    public BaseTabFragment(){}

    public void setLoadingInterface(ILoading loadingInterface) {
        this.loadingInterface = loadingInterface;
    }

    //Variable
    int mFragmentType;


    //Layout for HotTabFragment
    SwipeRefreshLayout baseRefreshLayout;
    ListView dataListView;
    TextView emptyDataView;
    FeaturedAdapter HotAdapter;


    //DataSource
    List<Tweet> parsedList;
    HttpThread httpThread;

    //Constant
    public static final int TYPE_HOT=0;
    public static final int TYPE_NEW=1;
    public static final int TYPE_TEXT=2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_base_tab, container, false);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Delay to execute LoadingSuccess Method
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingInterface.LoadingFailed();
            }
        },2000);
    }

}
