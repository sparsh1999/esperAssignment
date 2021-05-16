package com.example.esperassisgnment.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.example.esperassisgnment.Adapters.OptionAdapter;
import com.example.esperassisgnment.App;
import com.example.esperassisgnment.Database.dao.ExclusionDAO;
import com.example.esperassisgnment.Database.dao.FeatureDAO;
import com.example.esperassisgnment.Database.dao.OptionsDAO;
import com.example.esperassisgnment.Database.dao.SelectionDAO;
import com.example.esperassisgnment.Helpers.Constants;
import com.example.esperassisgnment.Helpers.Listeners.SelectionChangeListener;
import com.example.esperassisgnment.Helpers.SelectionManager;
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
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SelectionChangeListener {

    // logging purposes
    String TAG = "MAIN_ACTIVITY";
    // TabsLayout , to show all the features
    TabLayout featureTabs;
    // recyclerview to show all options for a feature
    RecyclerView recyclerView;
    // save button , which becomes available only when a option from each feature is selected
    Button saveButton;
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
        selectionManager = new SelectionManager(this);
        recyclerView = findViewById(R.id.recyclerView);
        featureTabs = findViewById(R.id.featureTabs);
        saveButton = findViewById(R.id.saveButton);
        networkProgress = new ProgressDialog(this);

        // set the adapter , and a grid layout of span = 2
        optionsAdapter = new OptionAdapter(this, selectionManager);
        recyclerView.setAdapter(optionsAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);

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


        saveButton.setOnClickListener((v)->{
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra(Constants.RESULT_DATA, app.gson.toJson(selectionManager.getSelections()));
            startActivity(intent);
        });

        // if network available , request fresh data or load data from db
        if(isNetworkAvailable()){
            Log.d(TAG, "Network is Available");
            requestDataFromServer();
        }
        else{
            loadFeatures();
        }
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
                        showNetworkProgress();
                        for (JsonElement jsonElement: dataResponse.exclusionData){
                            if (jsonElement instanceof JsonArray){
                                Log.d(TAG, jsonElement.toString());
                                List<Selection> exclusions = app.gson.fromJson(jsonElement,
                                        new TypeToken<List<Selection>>(){}.getType());
                                dataResponse.exclusionResponses.add(new ExclusionResponse(exclusions));
                                Log.d(TAG, exclusions.toString());
                            }
                        }
                        // after parsing , persist data to db
                        saveData(dataResponse);
                        // and finally refresh features
                        loadFeatures();
                        hideNetworkProgress();
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
        Selection selection1;
        Selection selection2;
        int selectionId1;
        int selectionId2;

        if (response.exclusionResponses!=null)
        for (ExclusionResponse exclusionResponse: response.exclusionResponses){
            if (exclusionResponse.exclusion!=null && exclusionResponse.exclusion.size()==2){
                // insert both selections which cannot be included together
                // and then populate a temp Table (Exclusion) to store the relationship
                Selection selectionTemp ;
                selection1 = exclusionResponse.exclusion.get(0);
                selection2 = exclusionResponse.exclusion.get(1);

                selectionTemp = selectionDAO.getSelection(selection1.featureId, selection1.optionId);
                if (selectionTemp!=null){
                    selectionId1 = selectionTemp.getId();
                }
                else{
                    selectionId1 = (int)selectionDAO.insert(selection1);
                }
                selectionTemp = selectionDAO.getSelection(selection2.featureId, selection2.optionId);
                if (selectionTemp!=null){
                    selectionId2 = selectionTemp.getId();
                }
                else{
                    selectionId2 = (int)selectionDAO.insert(selection2);
                }

                Exclusions exclusions = new Exclusions(selectionId1, selectionId2);
                exclusionDAO.insert(exclusions);
            }
            else{
                Log.d(TAG, "exclusion Response is either null or <2");
            }
        }
    }

    /**
     * called by PageChangeListener of Tab, whenever a feature is selected
     * updates the same adapter with the new set of options
     * @param featureId
     */
    private void loadOptions(int featureId){
        OptionsDAO optionsDAO = app.db.getOptionsDAO();
        List<Options> options = optionsDAO.getAllOptionsForFeature(featureId);
        optionsAdapter.updateOptions(options, featureId);
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
        Log.d(TAG, networkProgress.isShowing()+" ");
        if (!networkProgress.isShowing()) networkProgress.show();
    }

    private void hideNetworkProgress(){
        if (networkProgress.isShowing()) networkProgress.dismiss();
    }

    @Override
    public void selectionChanged(Set<Selection> selections) {
        Log.d(TAG, "onChanged is called");
        if (selections.size()!=0 && selections.size()==features.size()){
            saveButton.setVisibility(View.VISIBLE);
        }
        else{
            saveButton.setVisibility(View.GONE);
        }
    }
}
