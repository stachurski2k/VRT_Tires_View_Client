package pl.vrtechnology.tires.settings;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class SettingsRepositoryModule {

    @Provides
    @Singleton
    public SettingsRepository provideSettingsRepository(@ApplicationContext Context context) {
        return new SettingsRepository(context);
    }
}
