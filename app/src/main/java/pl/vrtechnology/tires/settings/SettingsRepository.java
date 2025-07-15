package pl.vrtechnology.tires.settings;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import pl.vrtechnology.tires.R;

public class SettingsRepository {

    private static final String CONFIG_FILE_NAME = "config.json";

    private final Gson gson = new Gson();
    private final Context context;
    private Configuration configuration;

    @Inject
    SettingsRepository(@ApplicationContext @NonNull Context context) {
        this.context = context;
        loadConfig();
    }

    /**
     * @return Generates configuration from default_config.json file.
     */
    private Configuration getDefaultConfig() {
        try (InputStream inputStream = context.getResources().openRawResource(R.raw.default_config)) {
            return gson.fromJson(new InputStreamReader(inputStream), Configuration.class);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void loadConfig() {
        // create config file if not exists
        if(!Arrays.asList(context.fileList()).contains(CONFIG_FILE_NAME)) {
            try (FileOutputStream fileOutputStream = context.openFileOutput(CONFIG_FILE_NAME, Context.MODE_PRIVATE)) {
                configuration = getDefaultConfig();
                fileOutputStream.write(gson.toJson(configuration).getBytes(StandardCharsets.UTF_8));
                return;
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        }

        // load config
        try (FileInputStream fileOutputStream = context.openFileInput(CONFIG_FILE_NAME)) {
            configuration = gson.fromJson(new InputStreamReader(fileOutputStream), Configuration.class);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        if(configuration == null) {
            configuration = new Configuration();
        }
    }

    private void saveConfig() {
        try (FileOutputStream fileOutputStream = context.openFileOutput(CONFIG_FILE_NAME, Context.MODE_PRIVATE)) {
            fileOutputStream.write(gson.toJson(configuration).getBytes(StandardCharsets.UTF_8));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public String getDeviceAddressIp() {
        return configuration.getDeviceIp();
    }

    void setDeviceAddressIp(@NonNull String ip) {
        configuration.setDeviceIp(ip);
        saveConfig();
        fireSettingsUpdatedEvent();
    }

    public int getDeviceAddressPort() {
        return configuration.getDevicePort();
    }

    void setDeviceAddressPort(int port) {
        configuration.setDevicePort(port);
        saveConfig();
        fireSettingsUpdatedEvent();
    }

    private void fireSettingsUpdatedEvent() {
        EventBus.getDefault().post(new SettingsUpdatedEvent());
    }

    public static class SettingsUpdatedEvent {}
}
