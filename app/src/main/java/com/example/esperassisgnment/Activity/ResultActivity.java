package com.example.esperassisgnment.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.esperassisgnment.App;
import com.example.esperassisgnment.Helpers.Constants;
import com.example.esperassisgnment.Models.Entities.Selection;
import com.example.esperassisgnment.R;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    Intent intent;
    ListView listView;
    App app;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        app = App.getAppInstance();

        listView = findViewById(R.id.resultListView);

        intent = getIntent();
        List<Selection> selections = app.gson.fromJson(intent.getStringExtra(Constants.RESULT_DATA), new TypeToken<List<Selection>>(){}.getType());

        List<String> result = new ArrayList<>();
        if (selections!=null){
            for(Selection selection: selections){
                result.add("featureId "+selection.getFeatureId()+" optionId "+selection.getOptionId());
            }
        }

        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, result));

    }
}
