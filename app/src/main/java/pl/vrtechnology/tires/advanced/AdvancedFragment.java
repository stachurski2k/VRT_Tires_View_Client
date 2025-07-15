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
import pl.vrtechnology.tires.settings.Configuration;

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
        Configuration config = viewModel.getConfig();

        // tire width
        Slider tireWidthSlider = view.findViewById(R.id.advanced_slider_tire_width);
        TextView minWidthTextView = view.findViewById(R.id.advanced_tire_width_min_text_view);
        TextView maxWidthTextView = view.findViewById(R.id.advanced_tire_width_max_text_view);
        float initialWidth = viewModel.getTireParameters().getValue().getWidth();
        setupSlider(tireWidthSlider, minWidthTextView, maxWidthTextView, config.getMinTireWidth(), config.getMaxTireWidth(), initialWidth, (slider, value, fromUser) -> viewModel.onTireWidthChange(value));

        // tire profile
        Slider tireProfileSlider = view.findViewById(R.id.advanced_slider_tire_profile);
        TextView minProfileTextView = view.findViewById(R.id.advanced_tire_profile_min_text_view);
        TextView maxProfileTextView = view.findViewById(R.id.advanced_tire_profile_max_text_view);
        float initialProfile = viewModel.getTireParameters().getValue().getProfile();
        setupSlider(tireProfileSlider, minProfileTextView, maxProfileTextView, config.getMinTireProfile(), config.getMaxTireProfile(), initialProfile, (slider, value, fromUser) -> viewModel.onTireProfileChange(value));

        // tire diameter
        Slider tireDiameterSlider = view.findViewById(R.id.advanced_slider_tire_diameter);
        TextView minDiameterTextView = view.findViewById(R.id.advanced_tire_diameter_min_text_view);
        TextView maxDiameterTextView = view.findViewById(R.id.advanced_tire_diameter_max_text_view);
        float initialDiameter = viewModel.getTireParameters().getValue().getDiameter();
        setupSlider(tireDiameterSlider, minDiameterTextView, maxDiameterTextView, config.getMinTireDiameter(), config.getMaxTireDiameter(), initialDiameter, (slider, value, fromUser) -> viewModel.onTireDiameterChange(value));

        // Observer for updated tire parameters
        TextView tireWidthTextView = view.findViewById(R.id.tire_width_setting_text_view);
        TextView tireProfileTextView = view.findViewById(R.id.tire_profile_setting_text_view);
        TextView tireDiameterTextView = view.findViewById(R.id.tire_diameter_setting_text_view);

        viewModel.getEditedTireParameters().observe(getViewLifecycleOwner(), tireParameters -> {
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

    // Helper method to set up the sliders
    private void setupSlider(Slider slider, TextView minTextView, TextView maxTextView, int min, int max, float currentValue, Slider.OnChangeListener listener) {
        minTextView.setText(String.valueOf(min));
        maxTextView.setText(String.valueOf(max));
        slider.setValueFrom(min);
        slider.setValueTo(max);
        slider.setValue(currentValue);
        slider.addOnChangeListener(listener);
    }
}
