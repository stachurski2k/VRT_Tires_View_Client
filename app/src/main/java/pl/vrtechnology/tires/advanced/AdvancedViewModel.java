package pl.vrtechnology.tires.advanced;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
class AdvancedViewModel extends ViewModel {

    private final ParameterRepository parameterRepository;
    private TireParameters editedParameters;

    @Inject
    AdvancedViewModel(@NonNull ParameterRepository parameterRepository) {
        this.parameterRepository = parameterRepository;
        this.editedParameters = parameterRepository.getTireParameters().getValue();
    }

    @NonNull
    LiveData<TireParameters> getTireParameters() {
        return parameterRepository.getTireParameters();
    }

    void onTireWidthChange(float width) {
        editedParameters.setWidth((int) width);
    }

    void onTireProfileChange(float profile) {
        editedParameters.setProfile((int) profile);
    }

    void onTireDiameterChange(float diameter) {
        editedParameters.setDiameter((int) diameter);
    }

    void onTireParametersSubmit() {
        parameterRepository.setTireParameters(editedParameters);
    }
}
