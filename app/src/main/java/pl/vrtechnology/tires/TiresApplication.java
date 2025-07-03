package pl.vrtechnology.tires;

import android.app.Application;

import androidx.lifecycle.ProcessLifecycleOwner;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class TiresApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ProcessLifecycleOwner.get().getLifecycle().addObserver(new ApplicationLifecycleObserver(this));
    }
}
