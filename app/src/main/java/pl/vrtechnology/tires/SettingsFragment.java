package pl.vrtechnology.tires;

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
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class SettingsFragment extends Fragment {

    EditText ip, port;
    TextView textView_koncowe;
    Button button_zatwierdz;

    private static final String IP_ADDRESS_PATTERN =
            "^((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)\\.){3}"
                    + "(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)$";
    public static String string_ip;
    public static int int_port;
    private static final Pattern pattern = Pattern.compile(IP_ADDRESS_PATTERN);
    public SettingsFragment() {
        super(R.layout.settings_fragment); // <-- layout fragmentu
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.settings_fragment, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ip = view.findViewById(R.id.settings_ip_edit_text);
        port = view.findViewById(R.id.settings_port_edit_text);
        button_zatwierdz = view.findViewById(R.id.buttonZatwierdz);

        button_zatwierdz.setOnClickListener(
                new View.OnClickListener() {
                    private View v;

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(View v) {
                        this.v = v;
                        string_ip = ip.getText().toString();
                        if(isValidIP(string_ip) && tryParsePort(port.getText().toString())){
                            int_port = Integer.parseInt(port.getText().toString());
                            textView_koncowe.setText(string_ip+" : "+int_port);


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
                            textView_koncowe.setText("nie poprawny port lub adres ip");
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