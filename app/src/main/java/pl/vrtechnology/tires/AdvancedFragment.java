package pl.vrtechnology.tires;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.slider.Slider;

public class AdvancedFragment extends Fragment {
    Slider slider_szerokosc, slider_srednica, slider_profil;
    TextView textView_szerokosc, textView_profil, textView_srednica;

    int szerokosc, srednica, profil;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.advanced_fragment, container, false);

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        slider_szerokosc = view.findViewById(R.id.advanced_slider_tire_width);
        slider_srednica = view.findViewById(R.id.advanced_slider_tire_diameter);
        slider_profil = view.findViewById(R.id.advanced_slider_tire_profile);

        textView_srednica = view.findViewById(R.id.textView_srednica);
        textView_profil = view.findViewById(R.id.textView_profil);
        textView_szerokosc = view.findViewById(R.id.textView_szerokosc);

        slider_srednica.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                srednica = (int) value;
                updateData(textView_srednica, "Średnica: "+srednica);
            }
        });
        slider_szerokosc.addOnChangeListener(new Slider.OnChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                szerokosc = (int) value;
                updateData(textView_szerokosc, "Szerokosc: "+szerokosc);

            }
        });
        slider_profil.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                profil = (int) value;
                updateData(textView_profil, "Profil: "+profil);
            }
        });
    }
    private void updateData(TextView textview, String value){
            textview.setText(value);
    }
}