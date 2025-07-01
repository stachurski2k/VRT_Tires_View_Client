package pl.vrtechnology.tires;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position){
            case 0:
                return new Odczyt();
            case 1:
                return new Zaawansowane();
            case 2:
                return new Ustawienia();
            default:
                return new Odczyt();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
