package com.example.studytimer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private TimeAdp adapter;
    private TextView showtime;
    private TextView perday;
    private TextView aveageday;
    private TextView mtitle;
    private Button mstart;
    private Timer mtimer;
    private TimerTask mtimerTask;
    private Double mtime = 0.0;
    private boolean isstart = false;
    private List<TimerModel> timelist;
    public String slotName;
    private SharedPreferences todaytime, totaltime, currentday, countday;
    private Context context;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_main);
        showtime = findViewById(R.id.uoaclock);
        perday = findViewById(R.id.perday);
        aveageday = findViewById(R.id.aveageday);
        mstart = findViewById(R.id.uoastart);
        mtitle = findViewById(R.id.uoatitle);
        mtimer = new Timer();

        todaytime = getSharedPreferences("todaytime", MODE_PRIVATE);
        totaltime = getSharedPreferences("totaltime", MODE_PRIVATE);
        currentday = getSharedPreferences("currentday", MODE_PRIVATE);
        countday = getSharedPreferences("countday", MODE_PRIVATE);

        double currentdaytime = (double) todaytime.getInt("todaytime", 0);
        perday.setText(getshowtime(currentdaytime));

        double titletime = (double) totaltime.getInt("totaltime", 0);
        int numday = countday.getInt("countday", 1);
        aveageday.setText(getshowtime(titletime / numday));


        timelist = savehistory.readListFromPref(this);
        if (timelist == null)
            timelist = new ArrayList<>();


        adapter = new TimeAdp(getApplicationContext(), timelist);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent;
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            intent = new Intent(MainActivity.this, PreferenceScreen.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_history) {
            intent = new Intent(MainActivity.this, History.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onResume() {
        super.onResume();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String value = preferences.getString("prefer_name", " Student");
        mtitle.setText("Hi " + value + ", you have studied: ");
        timelist = savehistory.readListFromPref(this);

        double currentdaytime = (double) todaytime.getInt("todaytime", 0);
        perday.setText(getshowtime(currentdaytime));

        double titletime = (double) totaltime.getInt("totaltime", 0);
        int numday = countday.getInt("countday", 1);
        aveageday.setText(getshowtime(titletime / numday));

        String datebefore = currentday.getString("currentday", "Noday");

        if (!datebefore.equals("Noday")) {
            String todaydada = getDate();
            if (!datebefore.equals(todaydada)) {
                String thisday = getDate();
                SharedPreferences.Editor todateeditor = currentday.edit();
                todateeditor.putString("currentday", thisday);
                todateeditor.commit();
                Log.e("haha", datebefore);
                Log.e("haha", getDate());

                int countdayy = countday.getInt("countday", 1);
                countdayy += 1;
                SharedPreferences.Editor countdayeditor = countday.edit();
                countdayeditor.putInt("countday", countdayy);
                countdayeditor.commit();

                int outputtime = 0;
                SharedPreferences.Editor todaytimeeditor = todaytime.edit();
                todaytimeeditor.putInt("todaytime", outputtime);
                todaytimeeditor.commit();
            }
        } else {
            String thisday = getDate();
            SharedPreferences.Editor todateeditor = currentday.edit();
            todateeditor.putString("currentday", thisday);
            todateeditor.commit();
        }
    }

    public void onPause() {
        super.onPause();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (preferences.getBoolean("prefer_focus", false) && isstart) {
            isstart = false;
            mstart.setText(context.getString(R.string.resume));
            mstart.setTextColor(ContextCompat.getColor(this, R.color.green));
            mtimerTask.cancel();
        }
    }

    public void studyreset(View view) {
        AlertDialog.Builder resetAlert = new AlertDialog.Builder(this);
        resetAlert.setTitle("Reset Timer");
        resetAlert.setMessage("Are you sure you want to reset the timer?");
        resetAlert.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (mtimerTask != null) {
                    mtimerTask.cancel();
                    mstart.setText(context.getString(R.string.start));
                    mstart.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.green));
                    mtime = 0.0;
                    isstart = false;
                    showtime.setText(context.getString(R.string.timer_zero));
                }
            }
        });

        resetAlert.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        resetAlert.show();
    }

    public void setnewday(View view) {
        AlertDialog.Builder newdayAlert = new AlertDialog.Builder(this);
        final AlertDialog dialog = newdayAlert.create();
        View dialogView = View.inflate(this, R.layout.save_slot_dialog, null);
        dialog.setView(dialogView);
        dialog.show();
        final EditText etName = dialogView.findViewById(R.id.et_name);
        Button btnLogin = dialogView.findViewById(R.id.btn_save);
        Button btnCancel = dialogView.findViewById(R.id.btn_cancel);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slotName = etName.getText().toString();
                if (TextUtils.isEmpty(slotName)) {
                    Toast.makeText(MainActivity.this, "Please enter your slot name", Toast.LENGTH_SHORT).show();
                } else if (mtimerTask == null) {
                    Toast.makeText(MainActivity.this, "No time slot to save", Toast.LENGTH_SHORT).show();
                } else {
                    addhistory();
                    double currentdaytime = (double) todaytime.getInt("todaytime", 0);
                    currentdaytime += mtime;
                    perday.setText(getshowtime(currentdaytime));
                    int outputtime = (int) currentdaytime;
                    SharedPreferences.Editor todaytimeeditor = todaytime.edit();
                    todaytimeeditor.putInt("todaytime", outputtime);
                    todaytimeeditor.commit();

                    double currenttotaltime = (double) totaltime.getInt("totaltime", 0);
                    currenttotaltime += mtime;
                    int numday = countday.getInt("countday", 1);
                    int resultday = (int) currenttotaltime / numday;
                    aveageday.setText(getshowtime(resultday));
                    int outputtotaltime = (int) currenttotaltime;
                    SharedPreferences.Editor totaltimeeditor = totaltime.edit();
                    totaltimeeditor.putInt("totaltime", outputtotaltime);
                    totaltimeeditor.commit();

                    mtimerTask.cancel();
                    mstart.setText(context.getString(R.string.start));
                    mstart.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.green));
                    mtime = 0.0;
                    isstart = false;
                    showtime.setText(context.getString(R.string.timer_zero));
                    dialog.dismiss();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void addhistory() {
        Toast.makeText(MainActivity.this, getshowtime(mtime) + " has been added to history", Toast.LENGTH_SHORT).show();

        TimerModel timerModel = new TimerModel(getshowtime(mtime), getDate(), mtime, slotName);
        if (timelist == null)
            timelist = new ArrayList<>();
        timelist.add(timerModel);
        savehistory.writeListInPref(getApplicationContext(), timelist);
        Collections.reverse(timelist);
        adapter.setTaskModelList(timelist);
    }

    public void studystart(View view) {
        if (isstart) {
            isstart = false;
            mstart.setText(context.getString(R.string.resume));
            mstart.setTextColor(ContextCompat.getColor(this, R.color.green));
            mtimerTask.cancel();
        } else {
            isstart = true;
            mstart.setText(context.getString(R.string.pause));
            mstart.setTextColor(ContextCompat.getColor(this, R.color.red));
            startTimer();
        }
    }

    private void startTimer() {
        mtimerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mtime++;
                        showtime.setText(getshowtime(mtime));
                    }
                });
            }
        };
        mtimer.scheduleAtFixedRate(mtimerTask, 0, 1000);
    }

    private String getshowtime(double time) {
        int rounded = (int) Math.round(time);
        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
        int hours = ((rounded % 86400) / 3600);
        return String.format("%02d", hours) + " : " + String.format("%02d", minutes) + " : " + String.format("%02d", seconds);
    }

    private String getDate() {
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        DateFormat out = new SimpleDateFormat("dd/MM/yyyy");
        return out.format(date);
    }

}