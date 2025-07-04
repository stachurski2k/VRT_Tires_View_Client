package pl.vrtechnology.tires.image;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ImageRepository {

    private final MutableLiveData<Bitmap> imageLiveData = new MutableLiveData<>();

    ImageRepository() {
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onImageDownloaded(@NonNull ImageDownloadedEvent event) {
        imageLiveData.postValue(event.getBitmap());
    }

    public LiveData<Bitmap> getImage() {
        return imageLiveData;
    }
}