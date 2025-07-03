package pl.vrtechnology.tires;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainViewActivity extends AppCompatActivity {

    /*private final ImageServiceConnection imageServiceConnection = new ImageServiceConnection();
    private ImageBoundedService imageService;*/

    public MainViewActivity() {
        //
    }

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewpager2);

        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            switch (position) {
                case 0: tab.setText("Odczyt"); break;
                case 1: tab.setText("Zaawansowane"); break;
                case 2: tab.setText("Ustawienia"); break;
            }
        }).attach();

        /*Intent intent = new Intent(this, ImageBoundedService.class);
        bindService(intent, imageServiceConnection, Context.BIND_AUTO_CREATE);

        IntentFilter filter = new IntentFilter("com.vrtechnology.IMAGE_LOADED");
        var imageLoadedReceiver = new ImageBroadcastReceiver();
        imageLoadedReceiver.setListener((context, intent1) -> {
            //
        });
        registerReceiver(imageLoadedReceiver, filter, Context.RECEIVER_EXPORTED);*/
    }

    @Override
    protected void onStop() {
        super.onStop();
        //unregisterReceiver(imageLoadedReceiver);
    }

    /*public ImageBoundedService getImageService() {
        return imageService;
    }

    private class ImageServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ImageBoundedService.LocalBinder binder = (ImageBoundedService.LocalBinder) service;
            imageService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {}
    }*/
}