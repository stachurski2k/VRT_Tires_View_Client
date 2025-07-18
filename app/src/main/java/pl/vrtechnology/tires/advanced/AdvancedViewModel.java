package pl.vrtechnology.tires.advanced;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import pl.vrtechnology.tires.R;
import pl.vrtechnology.tires.alert.Alert;
import pl.vrtechnology.tires.alert.ShowAlertEvent;
import pl.vrtechnology.tires.settings.Configuration;
import pl.vrtechnology.tires.settings.SettingsRepository;

@HiltViewModel
class AdvancedViewModel extends ViewModel {

    private final SettingsRepository settingsRepository;
    private final ParameterRepository parameterRepository;
    private final MutableLiveData<ShowAlertEvent> showAlertEventLiveData = new MutableLiveData<>();
    private final MutableLiveData<TireParameters> editedParametersLiveData;
    private boolean notSubmittedChanges = false;

    @Inject
    AdvancedViewModel(@NonNull SettingsRepository settingsRepository, @NonNull ParameterRepository parameterRepository) {
        this.settingsRepository = settingsRepository;
        this.parameterRepository = parameterRepository;
        this.editedParametersLiveData = new MutableLiveData<>(Objects.requireNonNull(parameterRepository.getTireParameters().getValue()).clone());
        //EventBus.getDefault().register(this);
    }

    Configuration getConfig() {
        return settingsRepository.getConfiguration();
    }

    @NonNull
    LiveData<ShowAlertEvent> getShowAlertEvent() {
        return showAlertEventLiveData;
    }

    @NonNull
    LiveData<TireParameters> getTireParameters() {
        return parameterRepository.getTireParameters();
    }

    @NonNull
    LiveData<TireParameters> getEditedTireParameters() {
        return editedParametersLiveData;
    }

    void onTireWidthChange(float width) {
        Objects.requireNonNull(editedParametersLiveData.getValue()).setWidth((int) width);
        onParameterChange();
    }

    void onTireProfileChange(float profile) {
        Objects.requireNonNull(editedParametersLiveData.getValue()).setProfile((int) profile);
        onParameterChange();
    }

    void onTireDiameterChange(float diameter) {
        Objects.requireNonNull(editedParametersLiveData.getValue()).setDiameter((int) diameter);
        onParameterChange();
    }

    private void onParameterChange() {
        editedParametersLiveData.postValue(editedParametersLiveData.getValue());
        if (!Objects.requireNonNull(editedParametersLiveData.getValue()).equals(parameterRepository.getTireParameters().getValue())) {
            if (!notSubmittedChanges) {
                showAlertEventLiveData.postValue(new ShowAlertEvent(R.string.advanced_alert_unsaved, Alert.Type.INFO, -1));
            }
            notSubmittedChanges = true;
        } else {
            notSubmittedChanges = false;
            showAlertEventLiveData.postValue(null);
        }
    }

    void onTireParametersSubmit() {
        notSubmittedChanges = false;
        parameterRepository.setTireParameters(Objects.requireNonNull(editedParametersLiveData.getValue()).clone())
                .exceptionally(throwable -> {
                    showAlertEventLiveData.postValue(new ShowAlertEvent(R.string.advanced_alert_save_failed, Alert.Type.ERROR, 2000));
                    return null;
                })
                .thenAccept(success -> {
                    if(success) {
                        showAlertEventLiveData.postValue(new ShowAlertEvent(R.string.advanced_alert_saved, Alert.Type.SUCCESS, 2000));
                    } else {
                        showAlertEventLiveData.postValue(new ShowAlertEvent(R.string.advanced_alert_save_failed, Alert.Type.ERROR, 2000));
                    }
                });
    }
}
