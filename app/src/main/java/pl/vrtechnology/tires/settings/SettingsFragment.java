package pl.vrtechnology.tires.settings;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

import dagger.hilt.android.AndroidEntryPoint;
import pl.vrtechnology.tires.R;

@AndroidEntryPoint
public class SettingsFragment extends Fragment {

    private EditText measurerIpEditText;
    private EditText measurerPortEditText;

    private static final String IP_ADDRESS_PATTERN =
            "^((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)\\.){3}"
                    + "(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)$";
    public String string_ip;
    public int int_port;
    private static final Pattern pattern = Pattern.compile(IP_ADDRESS_PATTERN);

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

        measurerIpEditText = view.findViewById(R.id.settings_ip_edit_text);
        measurerPortEditText = view.findViewById(R.id.settings_port_edit_text);
        Button submitButton = view.findViewById(R.id.buttonZatwierdz);

        submitButton.setOnClickListener(
                new View.OnClickListener() {
                    private View v;

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(View v) {
                        this.v = v;
                        string_ip = measurerIpEditText.getText().toString();
                        if(isValidIP(string_ip) && tryParsePort(measurerPortEditText.getText().toString())){
                            int_port = Integer.parseInt(measurerPortEditText.getText().toString());
                            //summaryTextView.setText(string_ip+" : "+int_port);


                            /*
                             * Standardowy AlertDialog ustawia przyciski w stałej kolejności:
                             * - Pozytywny przycisk (PositiveButton) jest zawsze po prawej stronie,
                             * - Negatywny przycisk (NegativeButton) jest zawsze po lewej stronie.
                             *
                             * Zamieniłem miejscsami żeby tak było z lewej
                             */
                            AlertDialog alertDialog = new AlertDialog.Builder(requireContext())
                                    .setTitle("Potwierdź wybór")
                                    .setMessage("Czy chcesz kontynuować?")
                                    .setNegativeButton("Tak", (dialog, which) -> {
                                        Toast.makeText(requireContext(), "Kliknięto TAK", Toast.LENGTH_SHORT).show();
                                        // np. wywołanie metody, zmiana stanu, itd.
                                    })
                                    .setPositiveButton("Nie", (dialog, which) -> {
                                        Toast.makeText(requireContext(), "Kliknięto NIE", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    })

                                    .show();

                            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED);
                            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.GREEN);
                        }
                        else{
                            //summaryTextView.setText("nie poprawny port lub adres ip");
                        }
                    }
                }
        );
    }

    public static boolean isValidIP(String ip) {
        if (ip == null) return false;
        return pattern.matcher(ip).matches();
    }
    private Boolean tryParsePort(String value) {
        try {
            int port = Integer.parseInt(value);
            if (port >= 0 && port <= 65535) {
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }

}