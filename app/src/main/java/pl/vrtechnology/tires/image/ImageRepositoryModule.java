package pl.vrtechnology.tires.image;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class ImageRepositoryModule {

    @Provides
    @Singleton
    public ImageRepository provideImageRepository() {
        return new ImageRepository();
    }
}
