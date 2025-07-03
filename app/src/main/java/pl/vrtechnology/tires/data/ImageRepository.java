package pl.vrtechnology.tires.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.CompletableFuture;

public class ImageRepository {

    private final MutableLiveData<Bitmap> imageLiveData = new MutableLiveData<>();

    public void setImage(Bitmap bitmap) {
        imageLiveData.postValue(bitmap);
    }

    public LiveData<Bitmap> getImageLiveData() {
        return imageLiveData;
    }

    public CompletableFuture<Void> fetchImage() {
        //return imageBoundedService.downloadImage();
        return null;
    }

    @Nullable
    public Drawable getImageAsDrawable(@NonNull Context context) {
        //return imageBoundedService.getImageAsDrawable(context);
        return null;
    }
}