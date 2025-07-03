package pl.vrtechnology.tires.data;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import okhttp3.Response;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;

class UpdateListener extends EventSourceListener {

    private final ImageBoundedService service;
    private final ScheduledExecutorService scheduler;

    public UpdateListener(ImageBoundedService service) {
        super();
        this.service = service;
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void onClosed(@NonNull EventSource eventSource) {
        scheduler.schedule(() -> {
            service.connectUpdateChannel();
            Log.d("UpdateListener", "onClosed");
        }, 5, TimeUnit.SECONDS);
    }

    @Override
    public void onEvent(@NonNull EventSource eventSource, @Nullable String id, @Nullable String type, @NonNull String data) {
        service.downloadImage();
        Log.d("UpdateListener", "DOWNLOAD");
    }

    @Override
    public void onFailure(@NonNull EventSource eventSource, @Nullable Throwable t, @Nullable Response response) {
        scheduler.schedule(() -> {
            service.connectUpdateChannel();
            Log.d("UpdateListener", "onFailure");
        }, 5, TimeUnit.SECONDS);
    }

    @Override
    public void onOpen(@NonNull EventSource eventSource, @NonNull Response response) {
        Log.d("UpdateListener", "onOpen");
    }
}
