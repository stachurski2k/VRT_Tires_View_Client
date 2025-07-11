package pl.vrtechnology.tires.advanced;

import androidx.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import pl.vrtechnology.tires.settings.SettingsRepository;

@Module
@InstallIn(SingletonComponent.class)
public class ParameterRepositoryModule {

    @Provides
    @Singleton
    public ParameterRepository provideParameterRepository(@NonNull SettingsRepository settingsRepository) {
        return new ParameterRepository(settingsRepository);
    }
}
