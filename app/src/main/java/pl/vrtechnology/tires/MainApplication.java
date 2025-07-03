package pl.vrtechnology.tires;

import android.app.Application;

import androidx.lifecycle.ProcessLifecycleOwner;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ProcessLifecycleOwner.get().getLifecycle().addObserver(new ApplicationLifecycleObserver());
    }
}
