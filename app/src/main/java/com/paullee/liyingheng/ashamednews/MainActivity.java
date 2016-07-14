package com.paullee.liyingheng.ashamednews;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

    //Widget for global
    FloatingActionButton addFAB;


    //Widget for MainActivity
    ListView dataListView;
    FeaturedAdapter featuredAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize global widget
        addFAB=(FloatingActionButton)findViewById(R.id.addNew_featured_FAB);


        //Initialize Adapter
        featuredAdapter=new FeaturedAdapter(this);

        //Initialize MainActivity widget
        dataListView=(ListView)findViewById(R.id.featuredNews_featured_ListView);
        dataListView.setAdapter(featuredAdapter);
        if(featuredAdapter.isEmpty())
        {
            View emptyView=findViewById(R.id.dataEmpty_TextView);
            dataListView.setEmptyView(emptyView);
        }


    }
}
