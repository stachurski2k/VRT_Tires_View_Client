package pl.vrtechnology.tires.util;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import lombok.Getter;

@Getter
public class ValidationResult {

    private static final ValidationResult VALID = new ValidationResult(true, -1);

    private final boolean isValid;
    @StringRes private final int errorMessage;

    public ValidationResult(boolean isValid, @StringRes int errorMessage) {
        this.isValid = isValid;
        this.errorMessage = errorMessage;
    }

    @NonNull
    public static ValidationResult valid() {
        return VALID;
    }

    @NonNull
    public static ValidationResult invalid(@StringRes int errorMessage) {
        return new ValidationResult(false, errorMessage);
    }
}
