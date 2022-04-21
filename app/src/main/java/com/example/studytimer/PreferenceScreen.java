package com.example.studytimer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.studytimer.ui.preferencescreen.PreferenceScreenFragment;

public class PreferenceScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preference_screen_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new PreferenceScreenFragment())
                    .commitNow();
        }
    }
}
