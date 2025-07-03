package pl.vrtechnology.tires;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ResultViewModel extends ViewModel {

    private final ImageRepository imageRepository;
    private final MutableLiveData<Drawable> imageLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

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

    public static class Factory implements ViewModelProvider.Factory {
        private final ImageRepository imageRepository;

        public Factory(ImageRepository imageRepository) {
            this.imageRepository = imageRepository;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(ResultViewModel.class)) {
                return (T) new ResultViewModel(imageRepository);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
