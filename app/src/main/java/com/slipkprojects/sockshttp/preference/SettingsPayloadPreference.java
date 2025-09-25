package com.slipkprojects.sockshttp.preference;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.preference.CheckBoxPreference;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.slipkprojects.sockshttp.R;
import com.slipkprojects.sockshttp.fragments.ProxyRemoteDialogFragment;
import com.slipkprojects.ultrasshservice.config.Settings;
import com.slipkprojects.ultrasshservice.config.SettingsConstants;
import com.slipkprojects.ultrasshservice.logger.ConnectionStatus;
import com.slipkprojects.ultrasshservice.logger.SkStatus;

public class SettingsPayloadPreference extends PreferenceFragmentCompat implements SettingsConstants, SkStatus.StateListener {

    private Settings mConfig;
    private SharedPreferences mSecurePrefs;
    private SharedPreferences mInsecurePrefs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mConfig = new Settings(getContext());
        mInsecurePrefs = getPreferenceManager().getDefaultSharedPreferences(getContext());
        mSecurePrefs = mConfig.getPrefsPrivate();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.payload_preferences, rootKey);

        // Enable/disable custom payload field depending on checkbox
        CheckBoxPreference useDefault = findPreference(PROXY_USAR_DEFAULT_PAYLOAD);
        EditTextPreference payload = findPreference(CUSTOM_PAYLOAD_KEY);
        Preference proxyConfig = findPreference("openProxyConfig");

        if (useDefault != null && payload != null) {
            // When using default payload (checked = true), disable custom payload field
            payload.setEnabled(!useDefault.isChecked());
            useDefault.setOnPreferenceChangeListener((preference, newValue) -> {
                boolean checked = (boolean) newValue;
                payload.setEnabled(!checked);
                return true;
            });
        }

        if (proxyConfig != null) {
            proxyConfig.setOnPreferenceClickListener(pref -> {
                DialogFragment dialog = new ProxyRemoteDialogFragment();
                dialog.show(getParentFragmentManager(), "ProxyRemoteDialog");
                return true;
            });
        }

        // Keep screen disabled while tunnel active
        getPreferenceScreen().setEnabled(!SkStatus.isTunnelActive());
    }

    @Override
    public void onStart() {
        super.onStart();
        SkStatus.addStateListener(this);

        // Reflect secure stored values into visible prefs
        // tunnel type stored as int in secure prefs
        ListPreference tunnelType = findPreference(TUNNELTYPE_KEY);
        if (tunnelType != null) {
            int type = mSecurePrefs.getInt(TUNNELTYPE_KEY, Settings.bTUNNEL_TYPE_SSH_DIRECT);
            tunnelType.setValue(Integer.toString(type));
        }

        EditTextPreference payload = findPreference(CUSTOM_PAYLOAD_KEY);
        if (payload != null) {
            payload.setText(mSecurePrefs.getString(CUSTOM_PAYLOAD_KEY, SettingsConstants.PAYLOAD_DEFAULT));
        }

        CheckBoxPreference useDefault = findPreference(PROXY_USAR_DEFAULT_PAYLOAD);
        if (useDefault != null) {
            useDefault.setChecked(mSecurePrefs.getBoolean(PROXY_USAR_DEFAULT_PAYLOAD, true));
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        // Migrate visible (insecure) values into secure preferences with correct types
        SharedPreferences.Editor secure = mSecurePrefs.edit();
        SharedPreferences.Editor insecure = mInsecurePrefs.edit();

        ListPreference tunnelType = findPreference(TUNNELTYPE_KEY);
        if (tunnelType != null) {
            String val = tunnelType.getValue();
            try {
                int intVal = Integer.parseInt(val);
                secure.putInt(TUNNELTYPE_KEY, intVal);
            } catch (Exception ignored) {}
            insecure.remove(TUNNELTYPE_KEY);
        }

        EditTextPreference payload = findPreference(CUSTOM_PAYLOAD_KEY);
        if (payload != null) {
            secure.putString(CUSTOM_PAYLOAD_KEY, payload.getText());
            insecure.remove(CUSTOM_PAYLOAD_KEY);
        }

        CheckBoxPreference useDefault = findPreference(PROXY_USAR_DEFAULT_PAYLOAD);
        if (useDefault != null) {
            secure.putBoolean(PROXY_USAR_DEFAULT_PAYLOAD, useDefault.isChecked());
            insecure.remove(PROXY_USAR_DEFAULT_PAYLOAD);
        }

        secure.apply();
        insecure.apply();

        SkStatus.removeStateListener(this);
    }

    @Override
    public void updateState(String state, String logMessage, int localizedResId, ConnectionStatus level, Intent intent) {
        // Disable screen when tunnel active
        if (getPreferenceScreen() != null) {
            getPreferenceScreen().setEnabled(!SkStatus.isTunnelActive());
        }
    }
}
