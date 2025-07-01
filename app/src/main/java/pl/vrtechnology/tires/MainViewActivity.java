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

public class MainViewActivity extends AppCompatActivity {

    private final ImageServiceConnection imageServiceConnection = new ImageServiceConnection();
    private ImageBoundedService imageService;
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view_activity);
        imageView = findViewById(R.id.imageView);

        if (imageView == null) {
            Log.e("MainViewActivity", "imageView is null!");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, ImageBoundedService.class);
        boolean success = bindService(intent, imageServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private class ImageServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ImageBoundedService.LocalBinder binder = (ImageBoundedService.LocalBinder) service;
            imageService = binder.getService();

            Log.d("MainViewActivity", "Service Connected");
            imageService.downloadImage()
                    .thenAcceptAsync(size -> {
                        if (size == -1) {
                            Log.e("MainViewActivity", "Failed to load image. Size is -1");
                            runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Failed to load image", Toast.LENGTH_SHORT).show());
                        } else {
                            Log.d("MainViewActivity", "Image loaded successfully: " + size + " bytes");
                            runOnUiThread(() -> {
                                Toast.makeText(getApplicationContext(), "Image loaded: " + size + " bytes", Toast.LENGTH_SHORT).show();
                                Drawable drawable = imageService.getImageAsDrawable(MainViewActivity.this);
                                if (drawable != null) {
                                    imageView.setImageDrawable(drawable);
                                } else {
                                    Log.e("MainViewActivity", "Drawable is null.");
                                }
                            });
                        }
                    })
                    .exceptionally(throwable -> {
                        Log.e("MainViewActivity", "Error during image download", throwable);
                        runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Error loading image: " + throwable.getMessage(), Toast.LENGTH_SHORT).show());
                        return null;
                    });

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(getApplicationContext(), "Service Disconnected", Toast.LENGTH_SHORT).show();
        }
    }
}
