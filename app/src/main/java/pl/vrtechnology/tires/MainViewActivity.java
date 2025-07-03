package pl.vrtechnology.tires;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainViewActivity extends AppCompatActivity implements ImageBroadcastReceiver.Listener {

    private final ImageServiceConnection imageServiceConnection = new ImageServiceConnection();
    private ImageBroadcastReceiver imageLoadedReceiver;
    private ImageBoundedService imageService;
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view_activity);
        imageView = findViewById(R.id.imageView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, ImageBoundedService.class);
        bindService(intent, imageServiceConnection, Context.BIND_AUTO_CREATE);

        IntentFilter filter = new IntentFilter("com.vrtechnology.IMAGE_LOADED");
        imageLoadedReceiver = new ImageBroadcastReceiver();
        imageLoadedReceiver.setListener(this);
        registerReceiver(imageLoadedReceiver, filter, Context.RECEIVER_EXPORTED);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(imageLoadedReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshImage();
    }

    @Override
    public void onImageDownloaded(Context context, Intent intent) {
        refreshImage();
    }

    private void refreshImage() {
        if(imageService != null) {
            Drawable drawable = imageService.getImageAsDrawable(getApplicationContext());
            if(drawable == null) {
                Log.d("MainViewActivity", "BRAK ZDJĘCIA, MOŻE JAKIŚ PLACEHOLDER?");
            } else {
                imageView.setImageDrawable(drawable);
            }
        }
    }

    private class ImageServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ImageBoundedService.LocalBinder binder = (ImageBoundedService.LocalBinder) service;
            imageService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {}
    }
}
