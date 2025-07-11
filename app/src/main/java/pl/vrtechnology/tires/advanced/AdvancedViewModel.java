package pl.vrtechnology.tires.advanced;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import pl.vrtechnology.tires.R;
import pl.vrtechnology.tires.alert.Alert;
import pl.vrtechnology.tires.alert.ShowAlertEvent;

@HiltViewModel
class AdvancedViewModel extends ViewModel {

    private final ParameterRepository parameterRepository;
    private final MutableLiveData<ShowAlertEvent> showAlertEventLiveData = new MutableLiveData<>();
    private final TireParameters editedParameters;
    private boolean notSubmittedChanges = false;

    @Inject
    AdvancedViewModel(@NonNull ParameterRepository parameterRepository) {
        this.parameterRepository = parameterRepository;
        this.editedParameters = Objects.requireNonNull(parameterRepository.getTireParameters().getValue()).clone();
    }

    @NonNull
    LiveData<ShowAlertEvent> getShowAlertEvent() {
        return showAlertEventLiveData;
    }

    @NonNull
    LiveData<TireParameters> getTireParameters() {
        return parameterRepository.getTireParameters();
    }

    void onTireWidthChange(float width) {
        editedParameters.setWidth((int) width);
        onParameterChange();
    }

    void onTireProfileChange(float profile) {
        editedParameters.setProfile((int) profile);
        onParameterChange();
    }

    void onTireDiameterChange(float diameter) {
        editedParameters.setDiameter((int) diameter);
        onParameterChange();
    }

    private void onParameterChange() {
        if(!editedParameters.equals(parameterRepository.getTireParameters().getValue())) {
            if(!notSubmittedChanges) {
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
        parameterRepository.setTireParameters(editedParameters.clone());
        showAlertEventLiveData.postValue(new ShowAlertEvent(R.string.advanced_alert_saved, Alert.Type.SUCCESS, 2000));
    }
}
