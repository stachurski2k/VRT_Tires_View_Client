package pl.vrtechnology.tires.settings;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.vrtechnology.tires.R;

@HiltViewModel
class SettingsViewModel extends ViewModel {

    private final SettingsRepository settingsRepository;
    private final SettingsValidator validator = new SettingsValidator();

    private final MutableLiveData<String> deviceIpInput;
    private final MutableLiveData<String> deviceIpError = new MutableLiveData<>();

    private final MutableLiveData<Integer> devicePortInput;
    private final MutableLiveData<String> devicePortError = new MutableLiveData<>();

    private final MutableLiveData<Boolean> canSaveSettings = new MutableLiveData<>(false);
    private final MutableLiveData<ShowConfirmDialogEvent> showConfirmDialogEvent = new MutableLiveData<>();

    @Inject
    SettingsViewModel(@NonNull SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
        deviceIpInput = new MutableLiveData<>(settingsRepository.getDeviceAddressIp());
        devicePortInput = new MutableLiveData<>(settingsRepository.getDeviceAddressPort());
    }

    @NonNull
    LiveData<String> getDeviceIpInput() {
        return deviceIpInput;
    }

    void onDeviceIpChanged(@NonNull Context context, @NonNull String ip) {
        var result = validator.validateIp(ip);
        canSaveSettings.postValue(result.isValid());
        if(result.isValid()) {
            deviceIpInput.postValue(ip);
            deviceIpError.postValue(null);
        } else {
            deviceIpError.postValue(context.getResources().getString(result.getErrorMessage()));
        }
    }

    @NonNull
    LiveData<String> getDeviceIpError() {
        return deviceIpError;
    }

    @NonNull
    LiveData<Integer> getDevicePortInput() {
        return devicePortInput;
    }

    void onDevicePortChanged(@NonNull Context context, @NonNull String port) {
        var result = validator.validatePort(port);
        canSaveSettings.postValue(result.isValid());
        if(result.isValid()) {
            devicePortInput.postValue(Integer.parseInt(port));
            devicePortError.postValue(null);
        } else {
            devicePortError.postValue(context.getResources().getString(result.getErrorMessage()));
        }
    }

    @NonNull
    LiveData<String> getDevicePortError() {
        return devicePortError;
    }

    @NonNull
    LiveData<Boolean> getCanSaveSettings() {
        return canSaveSettings;
    }

    void onSaveSettingsClicked() {
        if(Boolean.FALSE.equals(canSaveSettings.getValue())) {
            return;
        }
        showConfirmDialogEvent.postValue(new ShowConfirmDialogEvent(
                R.string.settings_alert_title,
                R.string.settings_alert_message,
                R.string.settings_alert_save,
                R.string.settings_alert_cancel
        ));
    }

    @NonNull
    LiveData<ShowConfirmDialogEvent> getShowConfirmDialogEvent() {
        return showConfirmDialogEvent;
    }

    void onConfirmDialogResponse(boolean confirmed) {
        showConfirmDialogEvent.postValue(null);
        if(!confirmed) {
            return;
        }
        canSaveSettings.postValue(false);
        settingsRepository.setDeviceAddressIp(Objects.requireNonNull(deviceIpInput.getValue()));
        settingsRepository.setDeviceAddressPort(Objects.requireNonNull(devicePortInput.getValue()));
    }

    @Getter
    @AllArgsConstructor
    static class ShowConfirmDialogEvent {
        @StringRes private final int title;
        @StringRes private final int message;
        @StringRes private final int positiveButtonText;
        @StringRes private final int negativeButtonText;
    }
}
