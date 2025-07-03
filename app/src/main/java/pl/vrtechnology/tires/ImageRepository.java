package pl.vrtechnology.tires;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.concurrent.CompletableFuture;

public class ImageRepository {

    private final ImageBoundedService imageBoundedService;

    public ImageRepository(ImageBoundedService imageBoundedService) {
        this.imageBoundedService = imageBoundedService;
    }

    public CompletableFuture<Void> fetchImage() {
        return imageBoundedService.downloadImage();
    }

    @Nullable
    public Drawable getImageAsDrawable(@NonNull Context context) {
        return imageBoundedService.getImageAsDrawable(context);
    }
}