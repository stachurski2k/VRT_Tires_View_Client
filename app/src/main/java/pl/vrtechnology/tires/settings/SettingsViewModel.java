package pl.vrtechnology.tires.settings;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SettingsViewModel extends ViewModel {

    private final SettingsRepository settingsRepository;

    @Inject
    public SettingsViewModel(@NonNull SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }
}
