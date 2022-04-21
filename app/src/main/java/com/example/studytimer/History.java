package com.example.studytimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {
    private TimeAdp adapter;
    private List<TimerModel> timelist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView recyclerView;
        setContentView(R.layout.activity_history);
        recyclerView = findViewById(R.id.recyclerview);

        timelist = savehistory.readListFromPref(this);

        if (timelist == null)
            timelist = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new TimeAdp(getApplicationContext(), timelist);
        recyclerView.setAdapter(adapter);

    }

    public void historyreset(View view) {
        SharedPreferences todaytime, totaltime, countday;
        todaytime = getSharedPreferences("todaytime", MODE_PRIVATE);
        totaltime = getSharedPreferences("totaltime", MODE_PRIVATE);
        countday = getSharedPreferences("countday", MODE_PRIVATE);

        int countdayy = 1;
        SharedPreferences.Editor countdayeditor = countday.edit();
        countdayeditor.putInt("countday", countdayy);
        countdayeditor.commit();

        int totaltimenum = 0;
        SharedPreferences.Editor totaltimeeditor = totaltime.edit();
        totaltimeeditor.putInt("totaltime", totaltimenum);
        totaltimeeditor.commit();

        int todaytimett = 0;
        SharedPreferences.Editor todayeditor = todaytime.edit();
        todayeditor.putInt("todaytime", todaytimett);
        todayeditor.commit();

        timelist = new ArrayList<>();
        savehistory.writeListInPref(getApplicationContext(), timelist);
        //Collections.reverse(timelist);
        adapter.setTaskModelList(timelist);
    }
}
