package pl.vrtechnology.tires.advanced;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class ParameterRepositoryModule {

    @Provides
    @Singleton
    public ParameterRepository provideParameterRepository() {
        return new ParameterRepository();
    }
}
