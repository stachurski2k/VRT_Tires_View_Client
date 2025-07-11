package pl.vrtechnology.tires.result;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import pl.vrtechnology.tires.R;
import pl.vrtechnology.tires.alert.Alert;
import pl.vrtechnology.tires.alert.ShowAlertEvent;
import pl.vrtechnology.tires.image.ConnectionErrorEvent;
import pl.vrtechnology.tires.image.ConnectionSuccessEvent;
import pl.vrtechnology.tires.image.ImageDownloadedEvent;
import pl.vrtechnology.tires.image.ImageRepository;

@HiltViewModel
public class ResultViewModel extends ViewModel {

    private final ImageRepository imageRepository;
    private final MutableLiveData<ShowAlertEvent> showAlertEventLiveData = new MutableLiveData<>();

    @Inject
    public ResultViewModel(@NonNull ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
        EventBus.getDefault().register(this);
    }

    @NonNull
    public LiveData<Bitmap> getImage() {
        return imageRepository.getImage();
    }

    @NonNull
    public LiveData<ShowAlertEvent> getShowAlertEventLiveData() {
        return showAlertEventLiveData;
    }

    /*@Subscribe(threadMode = ThreadMode.MAIN)
    public void onImageDownloaded(@NonNull ImageDownloadedEvent event) {
        showAlertEventLiveData.postValue(new ShowAlertEvent("image downloaded", Alert.Type.SUCCESS, 2000));
    }*/

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onConnectionSuccess(@NonNull ConnectionSuccessEvent event) {
        showAlertEventLiveData.postValue(new ShowAlertEvent(R.string.result_alert_connection_success, Alert.Type.SUCCESS, 2000));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onConnectionError(@NonNull ConnectionErrorEvent event) {
        showAlertEventLiveData.postValue(new ShowAlertEvent(R.string.result_alert_connection_error, Alert.Type.ERROR, -1));
    }
}
