package com.example.esperassisgnment.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.example.esperassisgnment.Adapters.OptionAdapter;
import com.example.esperassisgnment.Helpers.SelectionManager;
import com.example.esperassisgnment.Models.Data;
import com.example.esperassisgnment.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TabLayout featureTabs;
    List<Data> dataList = new ArrayList<>();
    ProgressDialog networkProgress ;
    OptionAdapter optionsAdapter;
    SelectionManager selectionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectionManager = new SelectionManager();
        recyclerView = findViewById(R.id.recyclerView);
        featureTabs = findViewById(R.id.featureTabs);
        networkProgress = new ProgressDialog(this);

        optionsAdapter = new OptionAdapter(this);
        recyclerView.setAdapter(optionsAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);

        if(isNetworkAvailable()){
            requestDataFromServer();
        }
        else{
            loadData();
        }

        featureTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



    }

    private void requestDataFromServer(){

    }

    private void loadData(){

    }

    private void addTabs(){
        networkProgress.show();
        featureTabs.removeAllTabs();
        for (Data data: dataList){
            featureTabs.addTab(featureTabs.newTab().setText(data.feature.getName()));
        }
        networkProgress.hide();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
