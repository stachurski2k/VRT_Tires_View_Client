package pl.vrtechnology.tires.advanced;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

class ParameterRepository {

    private final MutableLiveData<TireParameters> tireParametersLiveData = new MutableLiveData<>(new TireParameters(135, 30, 12));

    @Inject
    ParameterRepository() {}

    void setTireParameters(@NonNull TireParameters tireParameters) {
        tireParametersLiveData.postValue(tireParameters);
    }

    @NonNull
    LiveData<TireParameters> getTireParameters() {
        return tireParametersLiveData;
    }
}