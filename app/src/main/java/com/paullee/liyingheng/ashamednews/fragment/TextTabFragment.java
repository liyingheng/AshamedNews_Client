package com.paullee.liyingheng.ashamednews.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paullee.liyingheng.ashamednews.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class TextTabFragment extends BaseTabFragment {


    public TextTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_text_tab, container, false);
    }

}
