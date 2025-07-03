package pl.vrtechnology.tires.result;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import pl.vrtechnology.tires.data.ImageRepository;

@HiltViewModel
public class ResultViewModel extends ViewModel {

    private final ImageRepository imageRepository;
    private final MutableLiveData<Drawable> imageLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    @Inject
    public ResultViewModel(@NonNull ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public LiveData<Drawable> getImage() {
        return imageLiveData;
    }

    public LiveData<Boolean> getLoadingStatus() {
        return isLoading;
    }

    public void fetchImage(@NonNull Context context) {
        isLoading.setValue(true);
        imageRepository.fetchImage()
                .thenRun(() -> {
                    Drawable drawable = imageRepository.getImageAsDrawable(context);
                    imageLiveData.postValue(drawable);
                    isLoading.postValue(false);
                })
                .exceptionally(ex -> {
                    isLoading.postValue(false);
                    return null;
                });
    }
}
