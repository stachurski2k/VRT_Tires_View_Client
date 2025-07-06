package pl.vrtechnology.tires.settings;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import dagger.hilt.android.AndroidEntryPoint;
import pl.vrtechnology.tires.R;
import pl.vrtechnology.tires.SimpleTextWatcher;

@AndroidEntryPoint
public class SettingsFragment extends Fragment {

    private EditText deviceIpEditText;
    private EditText devicePortEditText;

    public SettingsFragment() {
        super(R.layout.settings_fragment);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settings_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SettingsViewModel viewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        deviceIpEditText = view.findViewById(R.id.settings_ip_edit_text);
        deviceIpEditText.setText(viewModel.getDeviceIpInput().getValue());
        viewModel.getDeviceIpError().observe(getViewLifecycleOwner(), error -> deviceIpEditText.setError(error));
        deviceIpEditText.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.onDeviceIpChanged(requireContext(), s.toString());
            }
        });

        devicePortEditText = view.findViewById(R.id.settings_port_edit_text);
        devicePortEditText.setText(String.valueOf(viewModel.getDevicePortInput().getValue()));
        viewModel.getDevicePortError().observe(getViewLifecycleOwner(), error -> devicePortEditText.setError(error));
        devicePortEditText.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.onDevicePortChanged(requireContext(), s.toString());
            }
        });

        Button submitButton = view.findViewById(R.id.settings_submit_button);
        submitButton.setOnClickListener(v -> viewModel.onSaveSettingsClicked());
        viewModel.getCanSaveSettings().observe(getViewLifecycleOwner(), canSave -> {
            submitButton.setEnabled(canSave); // TODO disabled button theme
            if (canSave) {
                submitButton.setAlpha(1.0f);
            } else {
                submitButton.setAlpha(0.5f);
            }
        });

        viewModel.getShowConfirmDialogEvent().observe(getViewLifecycleOwner(), event -> {
            if(event == null) {
                return;
            }
            AlertDialog alertDialog = new AlertDialog.Builder(requireContext())
                    .setTitle(event.getTitle())
                    .setMessage(event.getMessage())
                    .setNegativeButton(event.getPositiveButtonText(), (dialog, which) -> viewModel.onConfirmDialogResponse(true))
                    .setPositiveButton(event.getNegativeButtonText(), (dialog, which) -> {
                        viewModel.onConfirmDialogResponse(false);
                        dialog.dismiss();
                    })
                    .show();
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.DKGRAY);
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);
        });
    }
}