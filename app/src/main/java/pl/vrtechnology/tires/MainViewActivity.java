package pl.vrtechnology.tires;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import pl.vrtechnology.tires.R;
import pl.vrtechnology.tires.ViewPagerAdapter;

public class MainViewActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view_activity); // Upewnij się że to jest TWÓJ layout
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
    }
}