package com.example.studytimer.ui.preferencescreen;

import androidx.lifecycle.ViewModelProviders;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.studytimer.MainActivity;
import com.example.studytimer.R;

import org.w3c.dom.Text;

public class PreferenceScreenFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    TextView tv;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences,rootKey);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("prefer_name")){
            Preference connectionPref = findPreference(key);
            connectionPref.setSummary(sharedPreferences.getString(key,"Set up welcome name"));
        }
    }

    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}
