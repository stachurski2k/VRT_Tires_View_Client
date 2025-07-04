package pl.vrtechnology.tires.result;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import pl.vrtechnology.tires.image.ImageRepository;

@HiltViewModel
public class ResultViewModel extends ViewModel {

    private final ImageRepository imageRepository;

    @Inject
    public ResultViewModel(@NonNull ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public LiveData<Bitmap> getImage() {
        return imageRepository.getImage();
    }
}
