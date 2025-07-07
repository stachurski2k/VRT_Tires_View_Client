package pl.vrtechnology.tires.settings;

import androidx.annotation.NonNull;

import java.util.regex.Pattern;

import pl.vrtechnology.tires.R;
import pl.vrtechnology.tires.util.ValidationResult;

class SettingsValidator {

    private static final String IP_ADDRESS_PATTERN = "^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$";

    @NonNull
    ValidationResult validateIp(@NonNull String ip) {
        Pattern pattern = Pattern.compile(IP_ADDRESS_PATTERN);
        if(pattern.matcher(ip.trim()).matches()) {
            return ValidationResult.valid();
        } else {
            return ValidationResult.invalid(R.string.validation_error_incorrect_ip);
        }
    }

    @NonNull
    ValidationResult validatePort(@NonNull String port) {
        try {
            int portNumber = Integer.parseInt(port);
            if (portNumber < 0 || portNumber > 65535) {
                return ValidationResult.invalid(R.string.validation_error_incorrect_port);
            }
            return ValidationResult.valid();
        } catch (NumberFormatException e) {
            return ValidationResult.invalid(R.string.validation_error_incorrect_port);
        }
    }
}
