package pl.vrtechnology.tires.alert;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import lombok.Getter;

public class ShowAlertEvent {

    @StringRes
    private final int textId;
    @Nullable
    private final String text;

    @Getter private final Alert.Type type;
    @Getter private final long duration;

    public ShowAlertEvent(int textId, @NonNull Alert.Type type, long duration) {
        this.textId = textId;
        this.text = null;
        this.type = type;
        this.duration = duration;
    }

    public ShowAlertEvent(@NonNull String text, @NonNull Alert.Type type, long duration) {
        this.textId = -1;
        this.text = text;
        this.type = type;
        this.duration = duration;
    }

    public String getText(@NonNull Context context, Object... args) {
        if(textId != -1) {
            return context.getString(textId, args);
        }
        return text;
    }
}
