package com.paullee.liyingheng.ashamednews.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paullee.liyingheng.ashamednews.R;
import com.paullee.liyingheng.ashamednews.callback.ILoading;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseTabFragment extends Fragment {

    public ILoading loadingInterface;

    Handler handler=new Handler();

    public BaseTabFragment() {
        // Required empty public constructor
    }

    public void setLoadingInterface(ILoading loadingInterface) {
        this.loadingInterface = loadingInterface;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Delay to execute LoadingSuccess Method
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingInterface.LoadingFailed();
            }
        },2000);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base_tab, container, false);
    }

}
