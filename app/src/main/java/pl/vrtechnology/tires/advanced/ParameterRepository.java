package pl.vrtechnology.tires.advanced;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import pl.vrtechnology.tires.settings.Configuration;
import pl.vrtechnology.tires.settings.SettingsRepository;

class ParameterRepository {

    private final Gson gson = new Gson();
    private final SettingsRepository settingsRepository;
    private final MutableLiveData<TireParameters> tireParametersLiveData;
    private final OkHttpClient httpClient;

    @Inject
    ParameterRepository(@NonNull SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
        Configuration config = settingsRepository.getConfiguration();
        this.tireParametersLiveData = new MutableLiveData<>(new TireParameters(
                config.getMinTireWidth(),
                config.getMinTireProfile(),
                config.getMinTireDiameter()
        ));
        httpClient = new OkHttpClient();
    }

    @NonNull
    LiveData<TireParameters> getTireParameters() {
        return tireParametersLiveData;
    }

    @NonNull
    CompletableFuture<Boolean> setTireParameters(@NonNull TireParameters tireParameters) {
        tireParametersLiveData.postValue(tireParameters);
        return sendParameters(tireParameters);
    }

    private CompletableFuture<Boolean> sendParameters(@NonNull TireParameters tireParameters) {
        return CompletableFuture.supplyAsync(() -> {
            Request request = new Request.Builder()
                    .method("POST", RequestBody.create(gson.toJson(tireParameters).getBytes()))
                    .url("http://" + settingsRepository.getDeviceAddressIp() + ":" + settingsRepository.getDeviceAddressPort() + "/tire-settings")
                    .build();
            try (Response response = httpClient.newCall(request).execute()) {
                if(response.code() != 200) {
                    return false;
                }
            } catch (Exception exception) {
                Log.e("ParameterRepository", "sendParameters: ", exception);
                return false;
            }
            return true;
        });
    }
}