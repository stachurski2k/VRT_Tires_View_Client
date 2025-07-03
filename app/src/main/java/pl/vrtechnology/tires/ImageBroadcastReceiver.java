package pl.vrtechnology.tires;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ImageBroadcastReceiver extends BroadcastReceiver {

    @Nullable
    private Listener listener;

    public ImageBroadcastReceiver() {}

    @Override
    public void onReceive(Context context, Intent intent) {
        if (listener != null) {
            listener.onImageDownloaded(context, intent);
        }
    }

    public void setListener(@NonNull Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void onImageDownloaded(Context context, Intent intent);
    }
}
