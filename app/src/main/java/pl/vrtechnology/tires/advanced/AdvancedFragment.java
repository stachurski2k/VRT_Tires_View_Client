package pl.vrtechnology.tires.advanced;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.slider.Slider;

import dagger.hilt.android.AndroidEntryPoint;
import pl.vrtechnology.tires.R;
import pl.vrtechnology.tires.alert.Alert;

@AndroidEntryPoint
public class AdvancedFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.advanced_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AdvancedViewModel viewModel = new ViewModelProvider(this).get(AdvancedViewModel.class);

        // tire width
        Slider tireWidthSlider = view.findViewById(R.id.advanced_slider_tire_width);
        tireWidthSlider.setValue(viewModel.getTireParameters().getValue().getWidth());
        tireWidthSlider.addOnChangeListener((slider, value, fromUser) -> viewModel.onTireWidthChange(value));

        // tire profile
        Slider tireDiameterSlider = view.findViewById(R.id.advanced_slider_tire_diameter);
        tireDiameterSlider.setValue(viewModel.getTireParameters().getValue().getDiameter());
        tireDiameterSlider.addOnChangeListener((slider, value, fromUser) -> viewModel.onTireDiameterChange(value));

        // tire diameter
        Slider tireProfileSlider = view.findViewById(R.id.advanced_slider_tire_profile);
        tireProfileSlider.setValue(viewModel.getTireParameters().getValue().getProfile());
        tireProfileSlider.addOnChangeListener((slider, value, fromUser) -> viewModel.onTireProfileChange(value));

        TextView tireWidthTextView = view.findViewById(R.id.tire_width_setting_text_view);
        TextView tireDiameterTextView = view.findViewById(R.id.tire_profile_setting_text_view);
        TextView tireProfileTextView = view.findViewById(R.id.tire_diameter_setting_text_view);

        viewModel.getTireParameters().observe(getViewLifecycleOwner(), tireParameters -> {
            if (tireParameters != null) {
                tireWidthTextView.setText(getString(R.string.advanced_tire_setting_width, tireParameters.getWidth()));
                tireProfileTextView.setText(getString(R.string.advanced_tire_setting_profile, tireParameters.getProfile()));
                tireDiameterTextView.setText(getString(R.string.advanced_tire_setting_diameter, tireParameters.getDiameter()));
            }
        });

        Button submitButton = view.findViewById(R.id.tire_settings_submit);
        submitButton.setOnClickListener(v -> viewModel.onTireParametersSubmit());

        Alert alert = view.findViewById(R.id.advanced_alert);
        viewModel.getShowAlertEvent().observe(getViewLifecycleOwner(), event -> {
            if(event == null) {
                alert.hide();
            } else {
                alert.show(event.getText(requireContext()), event.getType(), event.getDuration());
            }
        });
    }
}
