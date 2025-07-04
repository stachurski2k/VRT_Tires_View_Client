package pl.vrtechnology.tires.image;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.sse.EventSources;

public class ImageService extends Service {

    private static final String IMAGE_URL = "http://83.168.69.49:2323/image";
    private static final String UPDATE_URL = "http://83.168.69.49:2323/update";

    private final UpdateListener updateListener;

    private final OkHttpClient client;
    private final Request request;

    @Inject
    ImageRepository repository;
    
    private byte[] cachedImageData = null;

    public ImageService() {
        Log.d("ImageBoundedService", "ImageBoundedService: SERVICE CONSTRUCTOR");

        this.updateListener = new UpdateListener(this);
        this.client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(0, TimeUnit.MINUTES)
                .build();

        this.request = new Request.Builder()
                .url(UPDATE_URL)
                .build();

        connectUpdateChannel();
    }

    public void connectUpdateChannel() {
        EventSources.createFactory(client)
                .newEventSource(request, updateListener);
    }

    public CompletableFuture<Void> downloadImage() {
        return CompletableFuture.runAsync(() -> {
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            try {
                URL url = new URL(IMAGE_URL);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.connect();

                inputStream = urlConnection.getInputStream();
                byte[] buffer = new byte[1024];
                int bytesRead;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, bytesRead);
                }

                cachedImageData = byteArrayOutputStream.toByteArray();
                Bitmap bitmap = BitmapFactory.decodeByteArray(cachedImageData, 0, cachedImageData.length);
                sendImageLoadedBroadcast(bitmap);

            } catch (Exception exception) {
                Log.e("ImageBoundedService", "downloadImage: fetching image", exception);
                throw new RuntimeException(exception);
            } finally {
                try {
                    if (inputStream != null) inputStream.close();
                    if (urlConnection != null) urlConnection.disconnect();
                } catch (Exception e) {
                    Log.e("ImageBoundedService", "downloadImage: closing connection", e);
                }
            }
        });
    }

    private void sendImageLoadedBroadcast(Bitmap bitmap) {
        ImageDownloadedEvent event = new ImageDownloadedEvent(bitmap);
        EventBus.getDefault().post(event);

        @SuppressLint("UnsafeImplicitIntentLaunch") Intent intent = new Intent("com.vrtechnology.IMAGE_LOADED");
        Log.d("ImageBoundedService", "INTENT");
        sendBroadcast(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
