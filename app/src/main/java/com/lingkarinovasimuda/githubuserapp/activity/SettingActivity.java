package com.lingkarinovasimuda.githubuserapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.lingkarinovasimuda.githubuserapp.R;
import com.lingkarinovasimuda.githubuserapp.fragment.PreferenceFragment;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        getSupportFragmentManager().beginTransaction().add(R.id.setting_holder, new PreferenceFragment()).commit();
    }
}