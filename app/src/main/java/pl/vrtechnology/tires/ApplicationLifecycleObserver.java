package pl.vrtechnology.tires;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import pl.vrtechnology.tires.data.ImageBoundedService;

public class ApplicationLifecycleObserver implements DefaultLifecycleObserver {

    private final Context context;

    public ApplicationLifecycleObserver(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {

    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {

    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {

    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {

    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        Intent serviceIntent = new Intent(context, ImageBoundedService.class);
        context.startService(serviceIntent);
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        Intent serviceIntent = new Intent(context, ImageBoundedService.class);
        context.stopService(serviceIntent);
    }
}
