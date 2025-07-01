package pl.vrtechnology.tires;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class Ustawienia extends Fragment {
    EditText ip, port;
    TextView textView_koncowe;
    private static final String IP_ADDRESS_PATTERN =
            "^((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)\\.){3}"
                    + "(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)$";
    public static String string_ip;
    public static int int_port;
    private static final Pattern pattern = Pattern.compile(IP_ADDRESS_PATTERN);
    public Ustawienia() {
        super(R.layout.fragment_ustawienia); // <-- layout fragmentu
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_ustawienia, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ip = view.findViewById(R.id.editText_ip);
        port = view.findViewById(R.id.editText_port);
        textView_koncowe = view.findViewById(R.id.koncowe);
        string_ip = ip.getText().toString();
        //bez parseinta
    }

    public static boolean isValidIP(String ip) {
        if (ip == null) return false;
        return pattern.matcher(ip).matches();
    }
    public static boolean isValidPORT(int port){
        if(port >= 0 && port <= 65535){
            return true;
        }
        return false;
    }
}