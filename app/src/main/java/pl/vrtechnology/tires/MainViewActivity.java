package pl.vrtechnology.tires;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class MainViewActivity extends AppCompatActivity {

    private final ImageServiceConnection imageServiceConnection = new ImageServiceConnection();
    private ImageBoundedService imageService;
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, ImageBoundedService.class);
        boolean success = bindService(intent, imageServiceConnection, Context.BIND_AUTO_CREATE);
        imageView = findViewById(R.id.imageView);
    }

    private class ImageServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ImageBoundedService.LocalBinder binder = (ImageBoundedService.LocalBinder) service;
            imageService = binder.getService();

            Log.d("MainViewActivity", "Service Connected");
            imageService.downloadImage()
                    .thenAcceptAsync(size -> {
                        Log.d("MainViewActivity", "Downloaded image size: " + size);
                        runOnUiThread(() -> {
                            Log.d("MainViewActivity", Arrays.toString(imageService.getCachedImageData()));
                            Drawable drawable = imageService.getImageAsDrawable(getApplicationContext());
                            if(drawable == null) {
                                Log.e("MainViewActivity", "Drawable is null!");
                                return;
                            }
                            imageView.setImageDrawable(drawable);
                        });
                    })
                    .exceptionally(throwable -> {
                        throw new RuntimeException("FAILED TO LOAD IMAGE: " + throwable.getMessage());
                    });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(getApplicationContext(), "Service Disconnected", Toast.LENGTH_SHORT).show();
        }
    }
}
