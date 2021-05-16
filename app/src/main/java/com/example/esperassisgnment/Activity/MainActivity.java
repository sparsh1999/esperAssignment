package com.example.esperassisgnment.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

import com.example.esperassisgnment.Adapters.OptionAdapter;
import com.example.esperassisgnment.App;
import com.example.esperassisgnment.Database.dao.ExclusionDAO;
import com.example.esperassisgnment.Database.dao.FeatureDAO;
import com.example.esperassisgnment.Database.dao.OptionsDAO;
import com.example.esperassisgnment.Database.dao.SelectionDAO;
import com.example.esperassisgnment.Helpers.SelectionManager;
import com.example.esperassisgnment.Models.Data;
import com.example.esperassisgnment.Models.Entities.Exclusions;
import com.example.esperassisgnment.Models.Entities.Feature;
import com.example.esperassisgnment.Models.Entities.Options;
import com.example.esperassisgnment.Models.Entities.Selection;
import com.example.esperassisgnment.Models.Responses.DataResponse;
import com.example.esperassisgnment.Models.Responses.ExclusionResponse;
import com.example.esperassisgnment.Models.Responses.FeatureResponse;
import com.example.esperassisgnment.R;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    // logging purposes
    String TAG = "MAIN_ACTIVITY";
    // TabsLayout , to show all the features
    TabLayout featureTabs;
    // recyclerview to show all options for a feature
    RecyclerView recyclerView;
    // global list of features , for tabs
    List<Feature> features = new ArrayList<>();
    // network progress or loading view (circle)
    ProgressDialog networkProgress ;
    // helper adapter for recyclerview
    OptionAdapter optionsAdapter;
    // Application class instance to get static data
    App app;

    /**
     * MAIN class to manages all selections and exclusions
     */
    SelectionManager selectionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize !! initialize !!
        app = App.getAppInstance();
        selectionManager = new SelectionManager();
        recyclerView = findViewById(R.id.recyclerView);
        featureTabs = findViewById(R.id.featureTabs);
        networkProgress = new ProgressDialog(this);

        // set the adapter , and a grid layout of span = 2
        optionsAdapter = new OptionAdapter(this);
        recyclerView.setAdapter(optionsAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);

        // if network available , request fresh data or load data from db
        if(isNetworkAvailable()){
            Log.d(TAG, "Network is Available");
            requestDataFromServer();
        }
        else{
            loadFeatures();
        }

        // TabSelectionListener to update options in Recyclerview for a selected Feature
        featureTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d(TAG, "tab clicked at "+tab.getPosition() +" "+tab.getText());
                loadOptions(features.get(tab.getPosition()).getId());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        hideNetworkProgress();
    }

    /**
     * uses Retrofit, fetches the data from server, if success , call saveData() to persist responses to db
     * and finally refresh the feature Tabs
     */
    private void requestDataFromServer(){
        showNetworkProgress();
        try {
            app.api.requestData().enqueue(new Callback<DataResponse>() {
                @Override
                public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                    DataResponse dataResponse = response.body();

                    // TODO there should be some better way to parse exclusionResponse
                    if (dataResponse!=null) {
                        for (JsonElement jsonElement: dataResponse.exclusionData){
                            if (jsonElement instanceof JsonArray){
                                Log.d(TAG, jsonElement.toString()+" "+((JsonArray) jsonElement).size());
                                List<Selection> exclusions = app.gson.fromJson(jsonElement,
                                        new TypeToken<List<Selection>>(){}.getType());
                                dataResponse.exclusionResponses.add(new ExclusionResponse(exclusions));
                            }
                        }
                        // after parsing , persist data to db
                        saveData(dataResponse);
                        // and finally refresh features
                        loadFeatures();
                    }
                    else{
                        Log.d(TAG, "Data Response is Empty or null");
                    }
                }

                @Override
                public void onFailure(Call<DataResponse> call, Throwable t) {
                    Log.d(TAG, "Network Failure"+t.getMessage());
                }
            });
        }
        catch (Exception e){
            Log.d(TAG, e.getMessage());
        }
        hideNetworkProgress();
    }


    /**
     * utility func to persist data to db
     * @param response
     */
    private void saveData(DataResponse response){
        // get all DAO objects
        showNetworkProgress();
        ExclusionDAO exclusionDAO = app.db.getExclusionsDAO();
        SelectionDAO selectionDAO = app.db.getSelectionDAO();
        FeatureDAO featureDAO = app.db.getFeatureDAO();
        OptionsDAO optionsDAO = app.db.getOptionsDAO();

        // delete all data before saving new one
        optionsDAO.deleteAll();
        featureDAO.deleteAll();
        selectionDAO.deleteAll();
        // exclusions should automatically delete due to foreign key constraints !!! TODO CHECK

        // add features and options
        if (response.features!=null)
        for (FeatureResponse featureResponse: response.features){
            // first feature
            Feature feature = new Feature();
            feature.setId(featureResponse.featureId);
            feature.setName(featureResponse.name);
            featureDAO.insert(feature);

            // now options
            if (featureResponse.options!=null){
                for (Options option: featureResponse.options){
                    // set a foreign key to Feature
                    option.setFeatureId(feature.getId());
                    optionsDAO.insert(option);
                }
            }
        }

        // now add exclusions Data
        if (response.exclusionResponses!=null)
        for (ExclusionResponse exclusionResponse: response.exclusionResponses){
            if (exclusionResponse.exclusion!=null && exclusionResponse.exclusion.size()==2){
                // insert both selections which cannot be included together
                // and then populate a temp Table (Exclusion) to store the relationship
                int selectionId1 = (int)selectionDAO.insert(exclusionResponse.exclusion.get(0));
                int selectionId2 = (int)selectionDAO.insert(exclusionResponse.exclusion.get(0));
                Exclusions exclusions = new Exclusions(selectionId1, selectionId2);
                exclusionDAO.insert(exclusions);
            }
            else{
                Log.d(TAG, "exclusion Response is either null or <2");
            }
        }
        hideNetworkProgress();
    }

    /**
     * called by PageChangeListener of Tab, whenever a feature is selected
     * updates the same adapter with the new set of options
     * @param featureId
     */
    private void loadOptions(int featureId){
        OptionsDAO optionsDAO = app.db.getOptionsDAO();
        List<Options> options = optionsDAO.getAllOptionsForFeature(featureId);
        optionsAdapter.updateOptions(options);
    }

    /**
     *  assumes saveData is successful and then loads all the features , calls addTab to populates tabs
     *  with feature Names
     */
    private void loadFeatures(){
        showNetworkProgress();
        FeatureDAO featureDAO = app.db.getFeatureDAO();
        List<Feature> features = featureDAO.getAllFeatures();
        if (features!=null){
            this.features.clear();
            this.features.addAll(features);
        }
        addTabs();
        hideNetworkProgress();
    }

    // removes all tabs and add fresh tabs(features)
    private void addTabs(){
        featureTabs.removeAllTabs();
        for (Feature feature: features){
            featureTabs.addTab(featureTabs.newTab().setText(feature.getName()));
        }
    }

    /**
     * utility function to check Network availability
     * @return true if connected or false
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void showNetworkProgress(){
        if (!networkProgress.isShowing()) networkProgress.show();
    }

    private void hideNetworkProgress(){
        if (networkProgress.isShowing()) networkProgress.hide();
    }
}
