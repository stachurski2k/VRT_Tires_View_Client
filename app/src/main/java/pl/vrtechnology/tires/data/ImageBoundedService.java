package pl.vrtechnology.tires.data;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

public class ImageBoundedService extends Service {

    private static final String IMAGE_URL = "http://172.20.0.226:8080/image";
    private static final String UPDATE_URL = "http://172.20.0.226:8080/update";

    private final IBinder binder = new LocalBinder();
    private final UpdateListener updateListener;

    private final OkHttpClient client;
    private final Request request;

    @Inject
    ImageRepository repository;
    
    private byte[] cachedImageData = null;

    public ImageBoundedService() {
        Log.d("ImageBoundedService", "ImageBoundedService: CONSTRUCTOR");
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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public CompletableFuture<Void> downloadImage() {
        Log.d("ImageBoundedService", "SYNC");
        return CompletableFuture.supplyAsync(() -> {
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            Log.d("ImageBoundedService", "ASYNC");

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
                Log.d("ImageBoundedService", "RETURN: " + cachedImageData.length);
                sendImageLoadedBroadcast();
                return null;

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

    @Nullable
    public Drawable getImageAsDrawable(@NonNull Context context) {
        return byteArrayToDrawable(cachedImageData, context);
    }

    @Nullable
    private Drawable byteArrayToDrawable(byte[] byteArray, Context context) {
        if(byteArray == null) {
            return null;
        }
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        if (bitmap != null) {
            return new BitmapDrawable(context.getResources(), bitmap);
        } else {
            return null;
        }
    }

    private void sendImageLoadedBroadcast() {
        @SuppressLint("UnsafeImplicitIntentLaunch") Intent intent = new Intent("com.vrtechnology.IMAGE_LOADED");
        Log.d("ImageBoundedService", "INTENT");
        sendBroadcast(intent);
    }

    public class LocalBinder extends Binder {
        ImageBoundedService getService() {
            return ImageBoundedService.this;
        }
    }
}
