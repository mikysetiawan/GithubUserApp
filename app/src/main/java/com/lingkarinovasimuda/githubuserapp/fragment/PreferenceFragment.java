package com.lingkarinovasimuda.githubuserapp.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.CheckBoxPreference;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.SwitchPreference;

import com.lingkarinovasimuda.githubuserapp.R;
import com.lingkarinovasimuda.githubuserapp.services.AlarmReceiver;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.Locale;

public class PreferenceFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    private SwitchPreference reminder;
    private Preference language;
    private String stringReminder, stringLanguage;
    private TextView tvLanguage;
    private AlarmReceiver alarmReceiver;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);

        init();
        setSummaries();

        language.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(mIntent);

                return false;
            }
        });
    }

    private void init(){
        stringReminder = getString(R.string.reminder);
        stringLanguage = getString(R.string.language);

        reminder = findPreference(stringReminder);
        language = findPreference(stringLanguage);

        alarmReceiver = new AlarmReceiver();

//        tvLanguage = view.findViewById(R.id.tv_language);
//
//        String lang = Locale.getDefault().getDisplayLanguage();
//        tvLanguage.setText(lang);
    }

    private void setSummaries() {
        SharedPreferences sh = getPreferenceManager().getSharedPreferences();
        reminder.setChecked(sh.getBoolean(stringReminder, false));
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }
    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(stringReminder)) {
            reminder.setChecked(sharedPreferences.getBoolean(stringReminder, false));

            if(sharedPreferences.getBoolean(stringReminder, false)){
                String repeatMessage = "Let's find popular user on Github!";
                alarmReceiver.setRepeatingAlarm(getContext(), AlarmReceiver.TYPE_REPEATING,
                        "09:00", repeatMessage);
            }else{
                alarmReceiver.cancelAlarm(getContext(), AlarmReceiver.TYPE_REPEATING);
            }
        }
    }
}
