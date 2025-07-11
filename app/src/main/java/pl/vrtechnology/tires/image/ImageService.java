package pl.vrtechnology.tires.image;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSources;
import pl.vrtechnology.tires.settings.SettingsRepository;

@AndroidEntryPoint
public class ImageService extends Service {

    // image
    private final OkHttpClient imageClient = new OkHttpClient();
    private Request imageRequest;

    // update
    private UpdateListener updateListener;
    private OkHttpClient sseUpdateClient;
    private Request sseUpdateRequest;
    private EventSource sseUpdateSource;

    // repositories
    @Inject
    ImageRepository imageRepository;
    @Inject
    SettingsRepository settingsRepository;
    
    private byte[] cachedImageData = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        EventBus.getDefault().register(this);
        imageRequest = new Request.Builder()
                .url(createUrl("image"))
                .build();
        updateListener = new UpdateListener(this);
        initializeChannels();
        connectUpdateChannel();
        downloadImage();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        if(sseUpdateSource != null) {
            sseUpdateSource.cancel();
        }
    }

    private void initializeChannels() {
        Log.d("ImageService", "initializeChannels");
        imageRequest = new Request.Builder()
                .url(createUrl("image"))
                .build();
        sseUpdateClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(0, TimeUnit.MINUTES)
                .build();
        sseUpdateRequest = new Request.Builder()
                .url(createUrl("update"))
                .build();
    }

    @Subscribe
    public void onSettingsUpdatedEvent(SettingsRepository.SettingsUpdatedEvent event) {
        initializeChannels();
        connectUpdateChannel();
    }

    public void downloadImage() {
        CompletableFuture.runAsync(() -> {
            try (Response response = imageClient.newCall(imageRequest).execute()) {
                if (response.body() == null) {
                    Log.e("ImageService", "downloadImage: Response body is null!");
                    return;
                }
                cachedImageData = response.body().bytes();
                Bitmap bitmap = BitmapFactory.decodeByteArray(cachedImageData, 0, cachedImageData.length);
                sendImageLoadedBroadcast(bitmap);
            } catch (Exception exception) {
                Log.e("ImageService", "downloadImage: Failed to download image!", exception);
                throw new RuntimeException(exception);
            }
        });
    }

    private void sendImageLoadedBroadcast(Bitmap bitmap) {
        ImageDownloadedEvent event = new ImageDownloadedEvent(bitmap);
        EventBus.getDefault().post(event);
    }

    synchronized void connectUpdateChannel() {
        Log.d("ImageService", "connectUpdateChannel");
        if(sseUpdateSource != null) {
            sseUpdateSource.cancel();
        }
        sseUpdateSource = EventSources.createFactory(sseUpdateClient)
                .newEventSource(sseUpdateRequest, updateListener);
    }

    @NonNull
    private String createUrl(@NonNull String endpoint) {
        return String.format(Locale.US, "http://%s:%d/%s", settingsRepository.getDeviceAddressIp(), settingsRepository.getDeviceAddressPort(), endpoint);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
