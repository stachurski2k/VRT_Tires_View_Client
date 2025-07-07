package pl.vrtechnology.tires.advanced;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
class AdvancedViewModel extends ViewModel {

    private final ParameterRepository parameterRepository;

    @Inject
    AdvancedViewModel(@NonNull ParameterRepository parameterRepository) {
        this.parameterRepository = parameterRepository;
    }

    @NonNull
    LiveData<TireParameters> getTireParameters() {
        return parameterRepository.getTireParameters();
    }

    void onTireWidthChange(float width) {
        TireParameters oldParameters = parameterRepository.getTireParameters().getValue();
        TireParameters newParameters = new TireParameters((int) width, oldParameters.getProfile(), oldParameters.getDiameter());
        parameterRepository.setTireParameters(newParameters);
    }

    void onTireProfileChange(float profile) {
        TireParameters oldParameters = parameterRepository.getTireParameters().getValue();
        TireParameters newParameters = new TireParameters(oldParameters.getWidth(), (int) profile, oldParameters.getDiameter());
        parameterRepository.setTireParameters(newParameters);
    }

    void onTireDiameterChange(float diameter) {
        TireParameters oldParameters = parameterRepository.getTireParameters().getValue();
        TireParameters newParameters = new TireParameters(oldParameters.getWidth(), oldParameters.getProfile(), (int) diameter);
        parameterRepository.setTireParameters(newParameters);
    }
}
