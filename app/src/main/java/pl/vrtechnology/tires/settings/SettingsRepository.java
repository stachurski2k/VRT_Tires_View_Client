package pl.vrtechnology.tires.settings;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

public class SettingsRepository {

    private final SharedPreferences preferences;

    @Inject
    public SettingsRepository(@ApplicationContext @NonNull Context context) {
        this.preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
    }

    public String getDeviceIp() {
        return preferences.getString("device_ip", "172.20.0.226");
    }

    public void setDeviceIp(@NonNull String ip) {
        preferences.edit().putString("device_ip", ip).apply();
    }
}
