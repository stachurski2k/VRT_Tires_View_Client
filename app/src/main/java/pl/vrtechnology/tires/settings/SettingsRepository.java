package pl.vrtechnology.tires.settings;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

public class SettingsRepository {

    private final SharedPreferences preferences;

    @Inject
    SettingsRepository(@ApplicationContext @NonNull Context context) {
        this.preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
    }

    public String getDeviceAddressIp() {
        return preferences.getString("device_address_ip", "172.20.0.226");
    }

    void setDeviceAddressIp(@NonNull String ip) {
        preferences.edit().putString("device_address_ip", ip).apply();
        fireSettingsUpdatedEvent();
    }

    public int getDeviceAddressPort() {
        return preferences.getInt("device_address_port", 8080);
    }

    void setDeviceAddressPort(int port) {
        preferences.edit().putInt("device_address_port", port).apply();
        fireSettingsUpdatedEvent();
    }

    private void fireSettingsUpdatedEvent() {
        EventBus.getDefault().post(new SettingsUpdatedEvent());
    }

    public static class SettingsUpdatedEvent {}
}
